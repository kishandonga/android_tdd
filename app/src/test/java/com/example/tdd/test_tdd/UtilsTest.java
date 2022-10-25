package com.example.tdd.test_tdd;

import com.example.tdd.utils.Utils;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UtilsTest {

    @Test
    public void test001_getItemPrice1() {
        float price = Utils.getItemPrice(0, 0);
        Assert.assertEquals(String.valueOf(0.0), String.valueOf(price));
    }

    @Test
    public void test002_getItemPrice2() {
        float price = Utils.getItemPrice(2.45f, 0);
        Assert.assertEquals(String.valueOf(0.0), String.valueOf(price));
    }

    @Test
    public void test003_getItemPrice3() {
        float price = Utils.getItemPrice(0, 5);
        Assert.assertEquals(String.valueOf(0.0), String.valueOf(price));
    }

    @Test
    public void test004_getItemPrice4() {
        float price = Utils.getItemPrice(2.45f, 5);
        Assert.assertEquals(String.valueOf(1.0208334), String.valueOf(price));
    }
}
