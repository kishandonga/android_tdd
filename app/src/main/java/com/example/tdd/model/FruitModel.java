package com.example.tdd.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tdd.db.entities.Fruit;

public class FruitModel implements Parcelable {

    private String fruit;
    private String photoId;
    private String description;
    private float amount;

    public FruitModel(String fruit, float amount, String photoId, String description) {
        this.fruit = fruit;
        this.photoId = photoId;
        this.description = description;
        this.amount = amount;
    }

    private FruitModel(Parcel in) {
        fruit = in.readString();
        photoId = in.readString();
        description = in.readString();
        amount = in.readFloat();
    }

    public Fruit getFruitModel(){
        Fruit f = new Fruit();
        f.setAmount(amount);
        f.setDescription(description);
        f.setFruit(fruit);
        f.setPhotoId(photoId);
        return f;
    }

    public static final Creator<FruitModel> CREATOR = new Creator<FruitModel>() {
        @Override
        public FruitModel createFromParcel(Parcel in) {
            return new FruitModel(in);
        }

        @Override
        public FruitModel[] newArray(int size) {
            return new FruitModel[size];
        }
    };

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fruit);
        parcel.writeString(photoId);
        parcel.writeString(description);
        parcel.writeFloat(amount);
    }
}
