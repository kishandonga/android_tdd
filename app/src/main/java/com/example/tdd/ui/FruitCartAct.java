package com.example.tdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdd.R;
import com.example.tdd.adapter.FruitCartAdapter;
import com.example.tdd.db.AppDatabase;
import com.example.tdd.model.FruitCart;
import com.example.tdd.utils.DataIntent;
import com.example.tdd.utils.RxDisposable;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FruitCartAct extends AppCompatActivity {

    private AppCompatTextView tvCartTotal;
    private RecyclerView rvFruit;
    private FruitCartAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.act_fruit_cart);
        initView();

        mAdapter = new FruitCartAdapter();
        mAdapter.setOnPriceChangeListener(amount -> tvCartTotal.setText(String.format(Locale.ENGLISH, "$%.2f", amount)));
        rvFruit.setAdapter(mAdapter);
        setData();

        mAdapter.setOnItemClickListener(new FruitCartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FruitCart fruit) {
                RxDisposable.addDisposable(AppDatabase.getDatabase(FruitCartAct.this).fruitDao().getFruit(fruit.getFruitId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(fruit1 -> {
                            DataIntent.getInstance().setFruit(fruit1);
                            Intent intent = new Intent(FruitCartAct.this, FruitDetailAct.class);
                            intent.putExtra("_ShowOptionMenu", false);
                            startActivity(intent);
                        }, Timber::e));
            }

            @Override
            public void onItemLongClick(FruitCart fruit) {
            }

            @Override
            public void refreshAdapter() {
                setData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        RxDisposable.addDisposable(AppDatabase.getDatabase(this)
                .cartDao().getFruitCart(DataIntent.getInstance().getUser().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAdapter::setItems, Timber::e));
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        rvFruit = findViewById(R.id.rvFruitCartList);
        rvFruit.setLayoutManager(new GridLayoutManager(this, 2));
        setTitle(getString(R.string.lbl_fruit_cart));
        tvCartTotal = findViewById(R.id.tvCartTotal);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposable.clear();
    }
}
