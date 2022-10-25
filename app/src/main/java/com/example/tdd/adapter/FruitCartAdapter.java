package com.example.tdd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdd.R;
import com.example.tdd.db.AppDatabase;
import com.example.tdd.db.entities.Cart;
import com.example.tdd.model.FruitCart;
import com.example.tdd.utils.RxDisposable;
import com.example.tdd.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Kishan Donga on 3/10/19
 */

public class FruitCartAdapter extends RecyclerView.Adapter<FruitCartAdapter.ViewHolder> {

    private List<FruitCart> list = new ArrayList<>();
    private OnItemClickListener listener;
    private OnPriceChangeListener priceChangeListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnPriceChangeListener(OnPriceChangeListener listener) {
        this.priceChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_fruit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<FruitCart> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

        if (priceChangeListener != null) {
            float amountTotal = 0.0f;
            for (FruitCart model : list) {
                amountTotal += model.getAmount();
            }
            priceChangeListener.onPriceChange(amountTotal);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(FruitCart fruit);

        void onItemLongClick(FruitCart fruit);

        void refreshAdapter();
    }

    public interface OnPriceChangeListener {
        void onPriceChange(float amount);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView ivFruit;
        ImageView ivDelete;
        TextView tvFruitName;
        TextView tvFruitPrice;

        ViewHolder(View v) {
            super(v);
            view = v;
            initView(v);
        }

        private void initView(View rootView) {
            ivFruit = rootView.findViewById(R.id.ivFruit);
            ivDelete = rootView.findViewById(R.id.ivDelete);
            tvFruitName = rootView.findViewById(R.id.tvFruitName);
            tvFruitPrice = rootView.findViewById(R.id.tvFruitPrice);
        }

        private void bind(final FruitCart fruit, final OnItemClickListener listener) {
            ivFruit.setImageDrawable(Utils.getDrawable(view.getContext(), fruit.getPhotoId()));
            tvFruitName.setText(fruit.getFruit());
            tvFruitPrice.setText(String.format(Locale.ENGLISH, "Price of " + fruit.getNumbersOfItem() + " Pic - $%.2f", fruit.getAmount()));

            view.setOnClickListener(v -> listener.onItemClick(fruit));
            view.setOnLongClickListener(v -> {
                listener.onItemLongClick(fruit);
                return true;
            });

            ivDelete.setOnClickListener(view -> {
                RxDisposable.addDisposable(AppDatabase.getDatabase(view.getContext())
                        .cartDao().getCart(fruit.getFruitId(), fruit.getUserId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(cart -> deleteConformationDialog(view.getContext(), cart, listener), Timber::e));
            });
        }

        private void deleteConformationDialog(Context context, Cart cart, final OnItemClickListener listener) {
            new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("Delete")
                    .setMessage("Do you want to remove from the cart?")
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                        dialogInterface.cancel();
                        RxDisposable.addDisposable(AppDatabase.getDatabase(context)
                                .cartDao().removeFromCart(cart).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(listener::refreshAdapter, Timber::e));
                    })
                    .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
        }
    }
}
