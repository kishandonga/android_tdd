package com.example.tdd.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

public class FruitCart {

    @PrimaryKey(autoGenerate = true)
    private int fruitId;

    @ColumnInfo(name = "fruit")
    private String fruit = "";

    @ColumnInfo(name = "photoId")
    private String photoId = "";

    @ColumnInfo(name = "description")
    private String description = "";

    @ColumnInfo(name = "is_favorite")
    private int isFavorite = 0;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "numbersOfItem")
    private int numbersOfItem = 0;

    @ColumnInfo(name = "amount")
    private float amount = 0f;

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

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumbersOfItem() {
        return numbersOfItem;
    }

    public void setNumbersOfItem(int numbersOfItem) {
        this.numbersOfItem = numbersOfItem;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @NotNull
    @Override
    public String toString() {
        return "FruitCart{" +
                "fruitId=" + fruitId +
                ", fruit='" + fruit + '\'' +
                ", photoId='" + photoId + '\'' +
                ", description='" + description + '\'' +
                ", isFavorite=" + isFavorite +
                ", userId=" + userId +
                ", numbersOfItem=" + numbersOfItem +
                ", amount=" + amount +
                '}';
    }
}
