package com.example.tdd.db;

import android.content.Context;

import com.example.tdd.db.entities.Cart;
import com.example.tdd.db.entities.User;
import com.example.tdd.utils.Const;
import com.example.tdd.utils.RxDisposable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class Repo {

    public static void addUser(Context context, User user, OnCompleted<Void> completed, OnError<Throwable> error) {
        RxDisposable.addDisposable(AppDatabase.getDatabase(context).userDao().insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    completed.completed(null);
                }, error::error));
    }

    public static void isExists(Context context, User user, OnCompleted<User> completed, OnError<String> error) {
        RxDisposable.addDisposable(AppDatabase.getDatabase(context).userDao().isAvail(user.getEmail(), user.getPassword())
                .flatMap(map -> {
                    if (map == 1) {
                        return AppDatabase.getDatabase(context).userDao().getUsers(user.getEmail());
                    } else {
                        return Single.error(new Throwable(Const.USER_NOT_EXIST));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completed::completed, throwable -> error.error(throwable.getMessage())));
    }

    public static void addToCart(Context context, Cart cart,  OnCompleted<Void> completed) {
        RxDisposable.addDisposable(AppDatabase.getDatabase(context).cartDao().insertOrUpdate(cart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> completed.completed(null), Timber::e));
    }

    public static void removeToCart(Context context, Cart cart, OnCompleted<Void> completed) {
        RxDisposable.addDisposable(AppDatabase.getDatabase(context).cartDao().getCart(cart.getFruitId(), cart.getUserId())
                .flatMapCompletable(map -> AppDatabase.getDatabase(context).cartDao().removeFromCart(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> completed.completed(null), Timber::e));
    }

    public interface OnCompleted<T> {
        void completed(T typeOfT);
    }

    public interface OnError<T> {
        void error(T typeOfT);
    }
}
