package com.example.tdd.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdd.R;
import com.example.tdd.db.entities.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kishan Donga on 3/10/19
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitViewHolder> {

    private List<Fruit> list = new ArrayList<>();
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FruitViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fruit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FruitViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<Fruit> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Fruit fruit);

        void onItemLongClick(Fruit fruit);
    }
}
