package com.example.tdd.test_tdd;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.tdd.utils.Utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UtilsTest {

    private Context appContext;

    @Before
    public void setUp() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void test001_getDrawableFromExtName() {
        String name = "apple.jpg";
        Drawable drawable = Utils.getDrawable(appContext, name);
        Assert.assertNull(drawable);
    }

    @Test
    public void test002_getDrawableFromName() {
        String name = "apple";
        Drawable drawable = Utils.getDrawable(appContext, name);
        Assert.assertNotNull(drawable);
    }
}
