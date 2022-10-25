package com.example.tdd.test_tdd;

import com.example.tdd.utils.Utils;
import com.example.tdd.utils.helper.Pair;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthUnitTest {

    String email = "k@d.com";

    String pwd1 = "Geeks@portal20";
    String pwd2 = "Geeksforgeeks";
    String pwd3 = "Geeks@ portal9";
    String pwd4 = "1234";
    String pwd5 = "Gfg@20";
    String pwd6 = "geeks@portal20";

    Pair<String, Boolean> out;
    Pair<String, Boolean> p1;
    Pair<String, Boolean> p2;

    @Test
    public void test001_validateNullEmail() {

        out = Utils.validateEmail(null);
        assertFalse(out.second);
    }

    @Test
    public void test002_validateEmptyEmail() {

        out = Utils.validateEmail("");
        assertFalse(out.second);
    }

    @Test
    public void test003_inValidateEmail() {
        out = Utils.validateEmail("kd.com");
        assertFalse(out.second);
    }

    @Test
    public void test004_validateEmail() {

        out = Utils.validateEmail(email);
        assertTrue(out.second);
    }

    @Test
    public void test005_validateNullPassword() {

        out = Utils.validatePassword(null);
        assertFalse(out.second);
    }

    @Test
    public void test006_validateEmptyPassword() {

        out = Utils.validatePassword("");
        assertFalse(out.second);
    }

    @Test
    public void test007_validatePassword() {

        out = Utils.validatePassword(pwd1);
        assertTrue(out.second);
    }

    @Test
    public void test008_validatePw2Password() {

        out = Utils.validatePassword(pwd2);
        assertFalse(out.second);
    }

    @Test
    public void test009_validatePw3Password() {

        out = Utils.validatePassword(pwd3);
        assertFalse(out.second);
    }

    @Test
    public void test010_validatePw4Password() {

        out = Utils.validatePassword(pwd4);
        assertFalse(out.second);
    }

    @Test
    public void test011_validatePw5Password() {

        out = Utils.validatePassword(pwd5);
        assertFalse(out.second);
    }

    @Test
    public void test012_validatePw6Password() {

        out = Utils.validatePassword(pwd6);
        assertFalse(out.second);
    }

    @Test
    public void test013_compareNullPasswords() {

        p1 = Utils.validatePassword(null);
        p2 = Utils.validatePassword(null);

        out = Utils.comparePassword(p1, p2);
        assertFalse(out.second);
    }

    @Test
    public void test014_compareSecondNullPassword() {

        p1 = Utils.validatePassword(pwd1);
        p2 = Utils.validatePassword(null);

        out = Utils.comparePassword(p1, p2);
        assertFalse(out.second);
    }

    @Test
    public void test015_compareFirstNullPassword() {

        p1 = Utils.validatePassword(null);
        p2 = Utils.validatePassword(pwd1);

        out = Utils.comparePassword(p1, p2);
        assertFalse(out.second);
    }

    @Test
    public void test016_comparePasswords() {

        p1 = Utils.validatePassword(pwd1);
        p2 = Utils.validatePassword(pwd1);

        out = Utils.comparePassword(p1, p2);
        assertTrue(out.second);
    }
}