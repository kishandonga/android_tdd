package com.example.tdd.test_tdd.db;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.tdd.db.AppDatabase;
import com.example.tdd.db.entities.Cart;
import com.example.tdd.db.entities.Fruit;
import com.example.tdd.db.entities.User;
import com.example.tdd.model.FruitCart;
import com.example.tdd.model.FruitModel;
import com.example.tdd.utils.DataProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {

    private AppDatabase database;
    private final String mail = "kishandonga.92@gmail.com";
    private final String pwd = "Geeks@portal20";

    @Before
    public void setUp() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        database = AppDatabase.getInMemoryDatabase(appContext.getApplicationContext());
    }

    @Test
    public void test001_insertData() {
        for (FruitModel model : DataProvider.getData()) {
            database.fruitDao().insert(model.getFruitModel()).blockingAwait();
        }

        int size = database.fruitDao().getSize().blockingGet();
        Assert.assertEquals(size, DataProvider.getData().size());
    }

    @Test
    public void test002_checkUserTblEmptyOrNot() {
        Assert.assertEquals(0, (int) database.userDao().isAvail(mail, pwd).blockingGet());
    }

    @Test
    public void test003_insertUser() {
        User user = new User();
        user.setEmail(mail);
        user.setPassword(pwd);
        database.userDao().insert(user).blockingAwait();

        Assert.assertEquals(1, (int) database.userDao().isAvail(mail, pwd).blockingGet());
    }

    @Test
    public void test004_checkSizeOfUserTbl() {
        Assert.assertEquals(1, database.userDao().getAllUsers().blockingGet().size());
    }

    @Test
    public void test005_insertMultipleUser() {

        try {
            User user = new User();
            user.setEmail(mail);
            user.setPassword(pwd);
            database.userDao().insert(user).blockingAwait();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Assert.assertEquals(1, database.userDao().getAllUsers().blockingGet().size());
    }

    @Test
    public void test006_insertInCart() {

        List<Fruit> fruits = database.fruitDao().getAllFruits().blockingGet();
        User user = database.userDao().getAllUsers().blockingGet().get(0);

        Cart cart = new Cart();
        cart.setUserId(user.getUserId());
        cart.setFruitId(fruits.get(0).getFruitId());
        cart.setAmount(2.45f);
        cart.setNumbersOfItem(5);

        database.cartDao().insertOrUpdate(cart).blockingAwait();

        cart = new Cart();
        cart.setUserId(user.getUserId());
        cart.setFruitId(fruits.get(1).getFruitId());
        cart.setAmount(1.45f);
        cart.setNumbersOfItem(3);

        database.cartDao().insertOrUpdate(cart).blockingAwait();

        cart = new Cart();
        cart.setUserId(user.getUserId());
        cart.setFruitId(fruits.get(2).getFruitId());
        cart.setAmount(3.45f);
        cart.setNumbersOfItem(7);

        database.cartDao().insertOrUpdate(cart).blockingAwait();

        Cart c = database.cartDao().getCart(fruits.get(2).getFruitId(), user.getUserId()).blockingGet();
        Assert.assertEquals(0, cart.compareTo(c));
    }

    @Test
    public void test007_UpdateInCart() {
        Fruit fruit = database.fruitDao().getAllFruits().blockingGet().get(0);
        User user = database.userDao().getAllUsers().blockingGet().get(0);

        Cart cart = new Cart();
        cart.setUserId(user.getUserId());
        cart.setFruitId(fruit.getFruitId());
        cart.setAmount(1.45f);
        cart.setNumbersOfItem(3);

        database.cartDao().insertOrUpdate(cart).blockingAwait();

        Cart c = database.cartDao().getCart(fruit.getFruitId(), user.getUserId()).blockingGet();
        Assert.assertEquals(0, cart.compareTo(c));
    }

    @Test
    public void test008_getCartCount() {
        User user = database.userDao().getAllUsers().blockingGet().get(0);
        int count = database.cartDao().getCartCount(user.getUserId()).blockingGet();
        Assert.assertEquals(3, count);
    }

    @Test
    public void test009_getUserFromEmail() {
        User user = database.userDao().getUsers(mail).blockingGet();
        Assert.assertEquals(mail, user.getEmail());
    }

    @Test
    public void test010_getCart() {
        User user = database.userDao().getAllUsers().blockingGet().get(0);
        List<FruitCart> carts = database.cartDao().getFruitCart(user.getUserId()).blockingGet();
        carts.forEach(element -> System.out.println(element.toString()));

        Assert.assertEquals(3, carts.get(0).getNumbersOfItem());
    }

    @Test
    public void test011_removeFromCart() {
        Fruit fruit = database.fruitDao().getAllFruits().blockingGet().get(0);
        User user = database.userDao().getAllUsers().blockingGet().get(0);

        Cart c = database.cartDao().getCart(fruit.getFruitId(), user.getUserId()).blockingGet();
        database.cartDao().removeFromCart(c).blockingAwait();

        int count = database.cartDao().getCartCount(user.getUserId()).blockingGet();
        Assert.assertEquals(2, count);
    }

    @Test
    public void test012_getFruitFromFruitId() {
        Fruit f = database.fruitDao().getAllFruits().blockingGet().get(0);
        Fruit fruit = database.fruitDao().getFruit(f.getFruitId()).blockingGet();
        Assert.assertEquals(f.getFruitId(), fruit.getFruitId());
    }
}
