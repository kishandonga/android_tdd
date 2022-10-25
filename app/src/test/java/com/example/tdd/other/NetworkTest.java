package com.example.tdd.other;

import com.example.tdd.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NetworkTest {

    @Test
    public void test1_checkInternetAvailable() {
        boolean status = Utils.isInternetAvailable();
        assertTrue(status);
    }
}
