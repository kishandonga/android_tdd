package com.example.tdd.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tdd.R;
import com.example.tdd.db.entities.Fruit;
import com.example.tdd.utils.Utils;

import java.util.Locale;

public class FruitViewHolder extends RecyclerView.ViewHolder {

    View view;
    ImageView ivFruit;
    TextView tvFruitName;
    TextView tvFruitPrice;

    public FruitViewHolder(View v) {
        super(v);
        view = v;
        initView(v);
    }

    private void initView(View rootView) {
        ivFruit = rootView.findViewById(R.id.ivFruit);
        tvFruitName = rootView.findViewById(R.id.tvFruitName);
        tvFruitPrice = rootView.findViewById(R.id.tvFruitPrice);
    }

    public void bind(final Fruit fruit, final FruitAdapter.OnItemClickListener listener) {
        ivFruit.setImageDrawable(Utils.getDrawable(view.getContext(), fruit.getPhotoId()));
        tvFruitName.setText(fruit.getFruit());
        tvFruitPrice.setText(String.format(Locale.ENGLISH, "$%.2f", fruit.getAmount()));

        view.setOnClickListener(v -> listener.onItemClick(fruit));
        view.setOnLongClickListener(v -> {
            listener.onItemLongClick(fruit);
            return true;
        });
    }
}