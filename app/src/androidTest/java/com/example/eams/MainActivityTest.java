package com.example.eams;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String USER_TYPE_SELECT = "Select User Type:";
    private static final String USER_TYPE_ATTENDEE = "Attendee";
    private static final String USER_TYPE_ORGANIZER = "Organizer";
    private static final String USER_TYPE_ADMINISTRATOR = "Administrator";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void sameActivityInView() {
        onView(withId(R.id.btn_main_login)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));

        onView(withId(R.id.spinner_main_user_select)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(USER_TYPE_SELECT))).perform(click());
        onView(withId(R.id.btn_main_login)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));

        onView(withId(R.id.spinner_main_user_select)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(USER_TYPE_ATTENDEE))).perform(click());
        onView(withId(R.id.btn_main_login)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));

        onView(withId(R.id.spinner_main_user_select)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(USER_TYPE_ORGANIZER))).perform(click());
        onView(withId(R.id.btn_main_login)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));

        onView(withId(R.id.spinner_main_user_select)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(USER_TYPE_ADMINISTRATOR))).perform(click());
        onView(withId(R.id.btn_main_login)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));
    }

    @Test
    public void changeActivityAttendeeWelcome() {
        onView(withId(R.id.spinner_main_user_select)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(USER_TYPE_ATTENDEE))).perform(click());

        onView(withId(R.id.et_main_email)).perform(typeText("attendee@eams.com"));
        onView(withId(R.id.et_main_password)).perform(typeText("Attendee123!"));
        onView(withId(R.id.btn_main_login)).perform(click());

        onView(withId(R.id.cl_activity_welcome_attendee)).check(matches(isDisplayed()));
    }

    @Test
    public void changeActivityOrganizerWelcome() {
        onView(withId(R.id.spinner_main_user_select)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(USER_TYPE_ORGANIZER))).perform(click());

        onView(withId(R.id.et_main_email)).perform(typeText("organizer@eams.com"));
        onView(withId(R.id.et_main_password)).perform(typeText("Organizer123!"));
        onView(withId(R.id.btn_main_login)).perform(click());

        onView(withId(R.id.cl_activity_welcome_organizer)).check(matches(isDisplayed()));
    }

    @Test
    public void changeActivityAdminWelcome() {
        onView(withId(R.id.spinner_main_user_select)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(USER_TYPE_ADMINISTRATOR))).perform(click());

        onView(withId(R.id.et_main_email)).perform(typeText("admin@eams.com"));
        onView(withId(R.id.et_main_password)).perform(typeText("Admin123!"));
        onView(withId(R.id.btn_main_login)).perform(click());

        onView(withId(R.id.cl_activity_welcome_admin)).check(matches(isDisplayed()));
    }

}
