package com.example.tdd.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tdd.db.dao.CartDao;
import com.example.tdd.db.dao.FruitDao;
import com.example.tdd.db.dao.UserDao;
import com.example.tdd.db.entities.Cart;
import com.example.tdd.db.entities.Fruit;
import com.example.tdd.db.entities.User;

@Database(entities = {User.class, Fruit.class, Cart.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "tdd_test.db";
    private static AppDatabase database;

    /**
     * @param context for the defining Room instance or builder
     * @return AppDatabase or we can say room database context
     */
    public static AppDatabase getDatabase(final Context context) {
        if (database == null) {
            synchronized (AppDatabase.class) {
                if (database == null) {
                    database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }
                            })
                            .build();
                }
            }
        }
        return database;
    }

    /**
     * @param context for the defining Room instance or builder
     * @return AppDatabase or we can say in memory room database context
     */
    public static AppDatabase getInMemoryDatabase(final Context context) {
        if (database == null) {
            synchronized (AppDatabase.class) {
                if (database == null) {
                    database = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }
                            })
                            .build();
                }
            }
        }
        return database;
    }

    public abstract UserDao userDao();

    public abstract FruitDao fruitDao();

    public abstract CartDao cartDao();
}
