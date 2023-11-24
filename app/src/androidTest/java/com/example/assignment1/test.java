package com.example.assignment1;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
public class test {



        @Rule
        public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testValidLogin() {
        // Replace with the correct view IDs
        onView(withId(R.id.email)).perform(typeText("ramsha@gmail.com"));
        onView(withId(R.id.pass)).perform(typeText("ram123"));
        closeSoftKeyboard();
        onView(withId(R.id.btn)).perform(click());


        onView(withId(R.id.dash)).check(matches(isDisplayed()));
    }


    @Test
        public void testInvalidLogin() {
            onView(withId(R.id.email)).perform(typeText("invalid_email"));
            onView(withId(R.id.pass)).perform(typeText("invalid_password"));
            onView(withId(R.id.btn)).perform(click());
            // Add assertion to check if the toast for sign-in failure is displayed
            onView(withId(R.id.login)).check(matches(isDisplayed()));
        }

        @Test
        public void testSignUpNavigation() {
            onView(withId(R.id.textsign)).perform(click());
            // Add assertion to check if the sign-up activity is displayed
            onView(withId(R.id.signup)).check(matches(isDisplayed()));
        }

}





