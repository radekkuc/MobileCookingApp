package com.example.cookingapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    // Special rule that must be applied before and after each test
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    // Code that is testes
    @Test
    public void testSearchInputAndButtonClick(){
        onView(withId(R.id.editTextInput)).perform(replaceText("potato"), closeSoftKeyboard());

        onView(withId(R.id.editTextInput)).check(matches(withText("potato")));

        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.recyclerViewDishes)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyInputDoesNotProceed() {
        // Click the search button without entering any text
        onView(withId(R.id.submitButton)).perform(click());

        // Check if the search input is still displayed (meaning it didn't navigate)
        onView(withId(R.id.editTextInput)).check(matches(isDisplayed()));
    }

}
