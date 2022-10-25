package com.example.tdd.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tdd.R;
import com.example.tdd.adapter.FruitAdapter;
import com.example.tdd.db.AppDatabase;
import com.example.tdd.db.entities.Fruit;
import com.example.tdd.model.FruitModel;
import com.example.tdd.utils.DataIntent;
import com.example.tdd.utils.DataProvider;
import com.example.tdd.utils.RxDisposable;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SelectFruitAct extends AppCompatActivity {

    private TextView tvCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_fruit);
        setTitle(getString(R.string.lbl_select_fruit));

        RecyclerView rvFruit = findViewById(R.id.rvSelectedFruitList);
        rvFruit.setLayoutManager(new GridLayoutManager(this, 2));
        FruitAdapter mAdapter = new FruitAdapter();
        rvFruit.setAdapter(mAdapter);

        RxDisposable.addDisposable(AppDatabase.getDatabase(this).fruitDao().getAllFruits().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mAdapter::setItems, Timber::e));

        mAdapter.setOnItemClickListener(new FruitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Fruit fruit) {
                DataIntent.getInstance().setFruit(fruit);
                Intent intent = new Intent(SelectFruitAct.this, FruitDetailAct.class);
                intent.putExtra("_ShowOptionMenu", true);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(Fruit fruit) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_as_recent_fruit, menu);

        MenuItem menuItem = menu.findItem(R.id.action_open_cart);
        View actionView = menuItem.getActionView();
        tvCartItemCount = actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBadge();
    }

    private void setupBadge() {
        if (tvCartItemCount != null) {
            int userId = DataIntent.getInstance().getUser().getUserId();
            RxDisposable.addDisposable(AppDatabase.getDatabase(this).cartDao().getCartCount(userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((count)->{
                        if (count == 0) {
                            if (tvCartItemCount.getVisibility() != View.GONE) {
                                tvCartItemCount.setVisibility(View.GONE);
                            }
                        } else {
                            tvCartItemCount.setText(String.format(Locale.ENGLISH, "%d", count));
                            if (tvCartItemCount.getVisibility() != View.VISIBLE) {
                                tvCartItemCount.setVisibility(View.VISIBLE);
                            }
                        }
                    }, Timber::e));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_open_cart) {
            startActivity(new Intent(SelectFruitAct.this, FruitCartAct.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposable.clear();
    }
}
