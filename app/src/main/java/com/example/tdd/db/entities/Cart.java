package com.example.tdd.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tbl_Cart", foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Fruit.class,
                parentColumns = "fruitId",
                childColumns = "fruitId",
                onDelete = ForeignKey.CASCADE)
}, indices = {
        @Index(unique = true, value = "fruitId"),
        @Index(value = "userId")
})
public class Cart implements Comparable<Cart> {

    @PrimaryKey(autoGenerate = true)
    private int cartId;

    @ColumnInfo(name = "fruitId")
    private int fruitId;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "numbersOfItem")
    private int numbersOfItem = 0;

    @ColumnInfo(name = "amount")
    private float amount = 0f;

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

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int compareTo(Cart cart) {
        if (String.valueOf(amount).equals(String.valueOf(cart.getAmount()))
                && String.valueOf(numbersOfItem).equals(String.valueOf(cart.getNumbersOfItem()))
                && String.valueOf(userId).equals(String.valueOf(cart.getUserId()))
                && String.valueOf(fruitId).equals(String.valueOf(cart.getFruitId())))
            return 0;
        else return 1;
    }
}
