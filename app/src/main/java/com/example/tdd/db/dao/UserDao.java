package com.example.tdd.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tdd.db.entities.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM TBL_USERS WHERE email=:mail")
    Single<User> getUsers(String mail);

    @Query("SELECT * FROM TBL_USERS")
    Single<List<User>> getAllUsers();

    @Insert()
    Completable insert(User user);

    @Query("SELECT COUNT(*) FROM Tbl_Users WHERE email=:mail AND password=:pwd")
    Single<Integer> isAvail(String mail, String pwd);
}