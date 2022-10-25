package com.example.tdd.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tbl_Users", indices = {
        @Index(value = "email", unique = true),
        @Index(value = "password", unique = false),
})
public class User implements Comparable<User> {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    @ColumnInfo(name = "email")
    private String email = "";

    @ColumnInfo(name = "password")
    private String password = "";

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(User user) {
        return 0;
    }
}
