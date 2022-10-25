package com.example.tdd.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.tdd.utils.helper.Pair;
import com.google.android.material.snackbar.Snackbar;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class Utils {

    public static Pair<String, Boolean> comparePassword(Pair<String, Boolean> pwd1, Pair<String, Boolean> pwd2) {
        if (pwd1.second && pwd2.second) {
            if (!pwd1.first.equals(pwd2.first)) {
                return Pair.create(Const.PWD_AND_CONFIRM_PWD_NOT_VALID, false);
            } else {
                return Pair.create("", true);
            }
        }

        return Pair.create(Const.PWD_AND_CONFIRM_PWD_NOT_VALID, false);
    }

    public static Pair<String, Boolean> validateEmail(String email) {
        if (email == null) {
            return Pair.create(Const.EMAIL_NULL_EMPTY, false);
        }

        if (email.isEmpty()) {
            return Pair.create(Const.EMAIL_NULL_EMPTY, false);
        }

        if (!isValidateEmail(email)) {
            return Pair.create(Const.EMAIL_VALID, false);
        }

        return Pair.create(email, true);
    }

    private static boolean isValidateEmail(String email) {
        return Pattern.matches("[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*", email);
    }

    private static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        return Pattern.matches(regex, password);
    }

    public static Pair<String, Boolean> validatePassword(String pwd) {
        if (pwd == null) {
            return Pair.create(Const.PWD_NULL_EMPTY, false);
        }

        if (pwd.isEmpty()) {
            return Pair.create(Const.PWD_NULL_EMPTY, false);
        }

        if (!isValidPassword(pwd)) {
            return Pair.create(Const.PWD_VALID, false);
        }

        return Pair.create(pwd, true);
    }

    @Nullable
    public static Drawable getDrawable(Context context, String name) {
        try {
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(name, "drawable", context.getPackageName());
            return ResourcesCompat.getDrawable(resources, resourceId, null);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    public static void showSnack(Activity activity, String msg) {
        Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static float getItemPrice(float totalPrice, int item) {
        if (item == 0) {
            return 0.0f;
        }
        float itemPrice = totalPrice / 12f;
        return item * itemPrice;
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            if (address != null) {
                return !address.toString().equals("");
            }
            return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }
}
