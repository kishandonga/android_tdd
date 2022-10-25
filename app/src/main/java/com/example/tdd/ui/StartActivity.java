package com.example.tdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tdd.R;
import com.example.tdd.db.AppDatabase;
import com.example.tdd.utils.DataProvider;
import com.example.tdd.utils.RxDisposable;

import io.easyprefs.Prefs;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!Prefs.read().content("is_data_avail", false)) {
            RxDisposable.addDisposable(Observable.fromIterable(DataProvider.getData())
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(fruitModel -> RxDisposable.addDisposable(AppDatabase.getDatabase(this).fruitDao().insert(fruitModel.getFruitModel())
                            .subscribeOn(Schedulers.single())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe()), Timber::e));
            Prefs.write().content("is_data_avail", true).commit();
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent mainIntent = new Intent(StartActivity.this, LoginAct.class);
            startActivity(mainIntent);
            finish();
        }, 3000);
    }

}
