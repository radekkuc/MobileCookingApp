package com.example.cookingapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class FirstFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testFirstFragmentDisplayRecyclerView(){
        onView(withId(R.id.editTextInput)).perform(replaceText("potato"), closeSoftKeyboard());

        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.recyclerViewDishes)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerViewItemCount() throws InterruptedException {
        onView(withId(R.id.submitButton)).perform(click());

        // Wait for data to load
        Thread.sleep(3000);

        // Get the RecyclerView instance and print item count
        onView(withId(R.id.recyclerViewDishes))
                .check((view, noViewFoundException) -> {
                    if (noViewFoundException != null) {
                        throw noViewFoundException;
                    }
                    RecyclerView recyclerView = (RecyclerView) view;
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    Log.d("EspressoTest", "RecyclerView item count: " + itemCount);
                });
    }

    @Test
    public void testRecyclerViewWaitsForItems() {
        onView(withId(R.id.submitButton)).perform(click());

        // Register RecyclerView as an Idling Resource
        ActivityScenario<MainActivity> recyclerView = activityRule.getScenario().onActivity(activity ->
                activity.findViewById(R.id.recyclerViewDishes));
        IdlingResource idlingResource = new RecyclerViewIdlingResource(recyclerView);
        IdlingRegistry.getInstance().register(idlingResource);

        // Now perform test (this waits until RecyclerView has items)
        onView(withId(R.id.recyclerViewDishes))
                .perform(scrollToPosition(0));

        onView(withId(R.id.recyclerViewDishes))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.dishTitle)).check(matches(isDisplayed()));

        // Unregister Idling Resource after test
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
