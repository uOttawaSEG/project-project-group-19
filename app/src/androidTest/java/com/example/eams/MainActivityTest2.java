package com.example.eams;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static java.util.regex.Pattern.matches;

import android.view.View;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;
import org.junit.Test;

public class MainActivityTest2 {
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new
            ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void emailIsInvalid() {
        onView(withId(R.id.firstName)).perform(typeText("user"), closeSoftKeyboard());
        onView(withId(R.id.lastName)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("email@"), closeSoftKeyboard());
        onView(withId(R.id.btn_main_login)).perform(click());
        onView(withText("Invalid Email")).check(matches(isDisplayed()));
    }

    private ViewAssertion matches(Matcher<View> displayed) {
        return null;
    }
}
