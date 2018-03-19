package com.sample.assignment;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sample.assignment.destination.DestinationActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DestinationsInstrumentedTest {

    @Rule
    public ActivityTestRule<DestinationActivity> destinationActivityTestRule =
            new ActivityTestRule<>(DestinationActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.sample.assignment.mock", appContext.getPackageName());
    }

    @Test
    public void withNetworkConnectivity() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        if (TestUtils.isConnectingToInternet(appContext)) {
            // Data should be loaded and list view will be visible on launch
            onView(withId(R.id.list_view)).check(matches(isDisplayed()));

        } else {
            // Error message should be displayed if there is no network
            onView(withId(R.id.error_text)).check(matches(isDisplayed()));
            onView(withText(R.string.error_generic_content)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void toolbar_persistsRotation() {

        // Check that the toolbar is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        // Rotate activity
        TestUtils.rotateOrientation(destinationActivityTestRule.getActivity());

        // check that the toolbar title is persisted
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }
}
