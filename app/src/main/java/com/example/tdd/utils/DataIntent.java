package com.example.tdd.utils;

import com.example.tdd.db.entities.Fruit;
import com.example.tdd.db.entities.User;
import com.example.tdd.model.FruitModel;

import java.util.ArrayList;
import java.util.List;

public class DataIntent {
    private static volatile DataIntent intent = new DataIntent();
    private List<FruitModel> recentList = new ArrayList<>();
    private Fruit fruit;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public static DataIntent getInstance() {
        return intent;
    }

    public void addItemInCart(FruitModel item) {
        recentList.add(item);
    }

    public void removeItemCart(FruitModel item) {
        recentList.remove(item);
    }

    public List<FruitModel> getCartList() {
        return recentList;
    }
}
