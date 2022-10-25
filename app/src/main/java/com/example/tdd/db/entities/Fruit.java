package com.example.tdd.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tbl_Fruits")
public class Fruit {

    @PrimaryKey(autoGenerate = true)
    private int fruitId;

    @ColumnInfo(name = "fruit")
    private String fruit = "";

    @ColumnInfo(name = "photoId")
    private String photoId = "";

    @ColumnInfo(name = "description")
    private String description = "";

    @ColumnInfo(name = "amount")
    private float amount = 0f;

    @ColumnInfo(name = "is_favorite")
    private int isFavorite = 0;

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
