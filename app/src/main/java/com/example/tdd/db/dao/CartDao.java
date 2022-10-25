package com.example.tdd.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tdd.db.entities.Cart;
import com.example.tdd.model.FruitCart;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public abstract class CartDao {

    @Query("SELECT TBL_FRUITS.fruitId,TBL_FRUITS.fruit, TBL_FRUITS.photoId, TBL_FRUITS.description, TBL_FRUITS.is_favorite, TBL_CART.userId, TBL_CART.numbersOfItem, TBL_CART.amount FROM TBL_CART INNER JOIN TBL_FRUITS ON TBL_CART.fruitId = TBL_FRUITS.fruitId WHERE userId=:userId")
    public abstract Single<List<FruitCart>> getFruitCart(int userId);

    @Delete
    public abstract Completable removeFromCart(Cart cart);

    @Query("SELECT * FROM TBL_CART WHERE userId=:userId AND fruitId=:fruitId")
    public abstract Single<Cart> getCart(int fruitId, int userId);

    @Query("UPDATE TBL_CART SET numbersOfItem=:numOfItem, amount=:amount WHERE userId=:userId AND fruitId=:fruitId")
    public abstract Completable update(int fruitId, int userId, int numOfItem, float amount);

    @Insert
    public abstract Completable insert(Cart cart);

    @Query("SELECT COUNT(*) FROM TBL_CART WHERE userId=:userId AND fruitId=:fruitId")
    public abstract Single<Integer> isAvail(int fruitId, int userId);

    @Query("SELECT COUNT(*) FROM TBL_CART WHERE userId=:userId")
    public abstract Single<Integer> getCartCount(int userId);

    public Completable insertOrUpdate(Cart cart) {
        return isAvail(cart.getFruitId(), cart.getUserId()).flatMapCompletable(map -> {
            if (map == 1) {
                return update(cart.getFruitId(), cart.getUserId(), cart.getNumbersOfItem(), cart.getAmount());
            } else {
                return insert(cart);
            }
        });
    }
}
