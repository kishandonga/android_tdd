package com.example.tdd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.tdd.R;
import com.example.tdd.db.AppDatabase;
import com.example.tdd.db.Repo;
import com.example.tdd.db.entities.Cart;
import com.example.tdd.db.entities.Fruit;
import com.example.tdd.utils.DataIntent;
import com.example.tdd.utils.RxDisposable;
import com.example.tdd.utils.Utils;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class FruitDetailAct extends AppCompatActivity {

    private TextView tvFruitPrice;
    private TextView tvFruitPricePerItem;
    private ImageView ivFruit;
    private TextView tvFruitDescription;
    private Fruit fruit;
    private ElegantNumberButton elegantNumberButton;
    private TextView tvCartItemCount;
    private boolean _showOptionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.act_fruit_detail);
        initView();
        addToCartDialog();

        _showOptionMenu = getIntent().getBooleanExtra("_ShowOptionMenu", false);

        fruit = DataIntent.getInstance().getFruit();
        setTitle(fruit.getFruit());
        ivFruit.setImageDrawable(Utils.getDrawable(this, fruit.getPhotoId()));
        tvFruitDescription.setText(fruit.getDescription());
        tvFruitPrice.setText(String.format(Locale.ENGLISH, "Price of 12 Pic - $%.2f", fruit.getAmount()));
        RxDisposable.addDisposable(AppDatabase.getDatabase(this)
                .cartDao().getCart(fruit.getFruitId(), DataIntent.getInstance().getUser().getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cart -> {
                    elegantNumberButton.setNumber(String.valueOf(cart.getNumbersOfItem()), false);
                    tvFruitPricePerItem.setText(String.format(Locale.ENGLISH, "Price of " + cart.getNumbersOfItem() + " Pic - $%.2f", cart.getAmount()));
                }, throwable -> {
                    Timber.e(throwable);
                    tvFruitPricePerItem.setText(String.format(Locale.ENGLISH, "Price of 0 Pic - $%.2f", 0.0f));
                }));
    }

    private void initView() {
        ivFruit = findViewById(R.id.ivFruit);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvFruitDescription = findViewById(R.id.tvFruitDescription);
        tvFruitPrice = findViewById(R.id.tvFruitPrice);
        tvFruitPricePerItem = findViewById(R.id.tvFruitPricePerItem);
        elegantNumberButton = findViewById(R.id.elegantNumberButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (_showOptionMenu) {
            getMenuInflater().inflate(R.menu.save_as_recent_fruit, menu);

            MenuItem menuItem = menu.findItem(R.id.action_open_cart);
            View actionView = menuItem.getActionView();
            tvCartItemCount = actionView.findViewById(R.id.cart_badge);
            setupBadge();
            actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        }
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
                    .subscribe((count) -> {
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_open_cart:
                startActivity(new Intent(FruitDetailAct.this, FruitCartAct.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToCartDialog() {
        elegantNumberButton.setOnValueChangeListener((view, oldValue, newValue) -> {
            float amount = Utils.getItemPrice(fruit.getAmount(), newValue);
            tvFruitPricePerItem.setText(String.format(Locale.ENGLISH, "Price of " + newValue + " Pic - $%.2f", amount));
            Cart cart = new Cart();
            cart.setUserId(DataIntent.getInstance().getUser().getUserId());
            cart.setFruitId(fruit.getFruitId());
            cart.setNumbersOfItem(newValue);
            cart.setAmount(amount);
            if (newValue != 0) {
                Repo.addToCart(this, cart, (Void) -> setupBadge());
            } else {
                Repo.removeToCart(this, cart, (Void) -> setupBadge());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxDisposable.clear();
    }
}
