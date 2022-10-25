package com.example.tdd.automation;

import android.content.Context;
import android.widget.TextView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.tdd.R;
import com.example.tdd.ui.StartActivity;
import com.example.tdd.utils.Const;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.example.tdd.automation.Helper.clickWithId;
import static com.example.tdd.automation.Helper.hasTextInputLayoutErrorText;
import static com.example.tdd.automation.Helper.hasTextInputLayoutHintText;
import static com.example.tdd.automation.Helper.pressBack;
import static com.example.tdd.automation.Helper.waitFor;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FruitInstrumentedTest {

    @Rule
    public ActivityScenarioRule<StartActivity> activityRule = new ActivityScenarioRule<>(StartActivity.class);

    private Context context;

    @Before
    public void setUp() {
        System.out.println("JUnit");
        // Context of the app under test.
        context = getInstrumentation().getTargetContext();
    }

    @Test
    public void test001_waitForSplashScreen() {
        onView(withId(R.id.tvFruitBasket)).check(matches(isDisplayed()));
        waitFor(4);
        test002_doSignUp();
    }

    public void test002_doSignUp() {
        Espresso.closeSoftKeyboard();
        waitFor(2);
        onView(withId(R.id.btnDoSignUp)).perform(click());

        waitFor(1);
        onView(withId(R.id.edEmailLayout)).check(matches(hasTextInputLayoutHintText(context.getString(R.string.lbl_enter_email))));
        onView(withId(R.id.edPwdLayout)).check(matches(hasTextInputLayoutHintText(context.getString(R.string.lbl_password))));
        onView(withId(R.id.edConfirmPwdLayout)).check(matches(hasTextInputLayoutHintText(context.getString(R.string.lbl_confirm_password))));

        Espresso.closeSoftKeyboard();
        waitFor(2);
        onView(withId(R.id.btnSignUp)).perform(click());

        waitFor(1);
        onView(withId(R.id.edEmailLayout)).check(matches(hasTextInputLayoutErrorText(Const.EMAIL_NULL_EMPTY)));
        onView(withId(R.id.edPwdLayout)).check(matches(hasTextInputLayoutErrorText(Const.PWD_NULL_EMPTY)));
        onView(withId(R.id.edConfirmPwdLayout)).check(matches(hasTextInputLayoutErrorText(Const.PWD_NULL_EMPTY)));

        waitFor(1);
        onView(withId(R.id.edEmail)).perform(scrollTo(), typeText("@kd.com"));
        onView(withId(R.id.edPwd)).perform(scrollTo(), typeText("abc@abc"));
        onView(withId(R.id.edConfirmPwd)).perform(scrollTo(), typeText("abc@abc"));

        pressBack();
        waitFor(1);
        onView(withId(R.id.btnSignUp)).perform(scrollTo(), click());

        waitFor(1);
        onView(withId(R.id.edEmailLayout)).check(matches(hasTextInputLayoutErrorText(Const.EMAIL_VALID)));
        onView(withId(R.id.edPwdLayout)).check(matches(hasTextInputLayoutErrorText(Const.PWD_VALID)));
        onView(withId(R.id.edConfirmPwdLayout)).check(matches(hasTextInputLayoutErrorText(Const.PWD_VALID)));

        waitFor(1);
        onView(withId(R.id.edEmail)).perform(scrollTo(), clearText());
        onView(withId(R.id.edPwd)).perform(scrollTo(), clearText());
        onView(withId(R.id.edConfirmPwd)).perform(scrollTo(), clearText());

        onView(withId(R.id.edEmail)).perform(scrollTo(), typeText("k@d.com"));
        onView(withId(R.id.edPwd)).perform(scrollTo(), typeText("Geeks@portal20"));
        onView(withId(R.id.edConfirmPwd)).perform(scrollTo(), typeText("Geeks$portal20"));

        pressBack();
        waitFor(1);
        onView(withId(R.id.btnSignUp)).perform(click());

        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(isDisplayed()));
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(Const.PWD_AND_CONFIRM_PWD_NOT_VALID)));

        waitFor(4);
        onView(withId(R.id.edPwd)).perform(scrollTo(), clearText());
        onView(withId(R.id.edConfirmPwd)).perform(scrollTo(), clearText());

        onView(withId(R.id.edPwd)).perform(scrollTo(), typeText("Geeks@portal20"));
        onView(withId(R.id.edConfirmPwd)).perform(scrollTo(), typeText("Geeks@portal20"));

        pressBack();
        waitFor(1);
        onView(withId(R.id.btnSignUp)).perform(scrollTo(), click());

        waitFor(2);
        pressBack();
        test003_doSignIn();
    }

    public void test003_doSignIn() {
        onView(withId(R.id.edEmail)).perform(scrollTo(), clearText());
        onView(withId(R.id.edPwd)).perform(scrollTo(), clearText());

        waitFor(1);
        onView(withId(R.id.edEmail)).perform(scrollTo(), typeText("k@dd.com"));
        onView(withId(R.id.edPwd)).perform(scrollTo(), typeText("Geeks@portal20"));

        pressBack();
        waitFor(1);
        onView(withId(R.id.btnLogin)).perform(scrollTo(), click());

        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(isDisplayed()));
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(Const.USER_NOT_EXIST)));

        waitFor(4);
        onView(withId(R.id.edEmail)).perform(scrollTo(), clearText());
        onView(withId(R.id.edPwd)).perform(scrollTo(), clearText());

        onView(withId(R.id.edEmail)).perform(scrollTo(), typeText("k@d.com"));
        onView(withId(R.id.edPwd)).perform(scrollTo(), typeText("Geeks@portal20"));

        pressBack();
        waitFor(1);

        onView(withId(R.id.btnLogin)).perform(scrollTo(), click());

        test004_dashboardValidation();
    }

    public void test004_dashboardValidation() {
        waitFor(2);

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.cart_badge)).check(matches(not(isDisplayed())));
        onView(withId(R.id.rvSelectedFruitList)).check(matches(isDisplayed()));

        // scroll list item to...
        onView(withId(R.id.rvSelectedFruitList)).perform(RecyclerViewActions.scrollToPosition(9));

        waitFor(2);

        // click list item to...
        onView(withId(R.id.rvSelectedFruitList)).perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));

        waitFor(2);
        test005_fruitDetailsValidation();
    }

    public void test005_fruitDetailsValidation() {

        onView(allOf(instanceOf(TextView.class),
                withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(matches(withText("Lemon")));

        onView(withId(R.id.cart_badge)).check(matches(not(isDisplayed())));

        onView(withId(R.id.tvFruitPrice)).check(matches(withText("Price of 12 Pic - $1.55")));
        onView(withId(R.id.tvFruitPricePerItem)).check(matches(withText("Price of 0 Pic - $0.00")));

        onView(withId(R.id.subtract_btn)).perform(click());
        onView(withId(R.id.number_counter)).check(matches(withText("0")));

        onView(withId(R.id.add_btn)).perform(click());
        onView(withId(R.id.add_btn)).perform(click());
        onView(withId(R.id.number_counter)).check(matches(withText("2")));
        onView(withId(R.id.tvFruitPricePerItem)).check(matches(withText("Price of 2 Pic - $0.26")));

        waitFor(2);
        onView(withId(R.id.cart_badge)).check(matches(isDisplayed()));
        onView(withId(R.id.cart_badge)).check(matches(withText("1")));

        pressBack();
        waitFor(2);
        test006_dashboardCartValidation();
    }

    public void test006_dashboardCartValidation() {
        onView(withId(R.id.cart_badge)).check(matches(isDisplayed()));
        onView(withId(R.id.cart_badge)).check(matches(withText("1")));

        onView(withId(R.id.action_open_cart)).perform(click());
        waitFor(2);
        test007_cartValidation();
    }

    public void test007_cartValidation() {

        onView(allOf(instanceOf(TextView.class),
                withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(matches(withText("Fruit Cart")));

        onView(withId(R.id.tvCartTotal)).check(matches(withText("$0.26")));

        onView(withId(R.id.rvFruitCartList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        waitFor(2);
        test008_removeItemFromCart();
    }

    public void test008_removeItemFromCart() {
        onView(allOf(instanceOf(TextView.class),
                withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(matches(withText("Lemon")));

        onView(withId(R.id.tvFruitPrice)).check(matches(withText("Price of 12 Pic - $1.55")));
        onView(withId(R.id.tvFruitPricePerItem)).check(matches(withText("Price of 2 Pic - $0.26")));

        onView(withId(R.id.subtract_btn)).perform(click());
        onView(withId(R.id.number_counter)).check(matches(withText("1")));

        onView(withId(R.id.tvFruitPricePerItem)).check(matches(withText("Price of 1 Pic - $0.13")));

        pressBack();
        waitFor(2);
        test009_validateCart();
    }

    public void test009_validateCart() {
        onView(allOf(instanceOf(TextView.class),
                withParent(ViewMatchers.withResourceName("action_bar"))))
                .check(matches(withText("Fruit Cart")));

        onView(withId(R.id.tvCartTotal)).check(matches(withText("$0.13")));

        onView(withId(R.id.rvFruitCartList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, clickWithId(R.id.ivDelete)));
        waitFor(1);
        onView(withId(android.R.id.button2)).perform(click());

        onView(withId(R.id.rvFruitCartList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, clickWithId(R.id.ivDelete)));
        waitFor(1);
        onView(withId(android.R.id.button1)).perform(click());

        waitFor(1);
        onView(withId(R.id.tvCartTotal)).check(matches(withText("$0.00")));

        pressBack();
        waitFor(2);
    }
}
