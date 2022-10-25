package com.example.tdd.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tdd.db.entities.Fruit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface FruitDao {

    @Query("SELECT * FROM TBL_FRUITS WHERE fruitId=:fruitId")
    Single<Fruit> getFruit(int fruitId);


    @Query("SELECT * FROM TBL_FRUITS")
    Single<List<Fruit>> getAllFruits();

    @Insert
    Completable insert(Fruit fruit);

    @Query("SELECT COUNT(*) FROM TBL_FRUITS")
    Single<Integer> getSize();
}
