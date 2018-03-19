package com.sample.assignment;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.sample.assignment.common.Common;
import com.sample.assignment.destination.DestinationsViewModel;
import com.sample.assignment.models.Destination;
import com.sample.assignment.models.Rows;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DestinationsUnitTest {

    // Executes each task synchronously using Architecture Components.
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private DestinationsViewModel destinationsViewModel;

    private Destination destination;

    @Before
    public void setupDestinationViewModel() {
        // Get a reference to the class under test
        destinationsViewModel = new DestinationsViewModel(Common.application());

        // We initialise the destination object
        destination = new Destination();
        destination.setTitle("Title1");
        destination.setRows(Lists.newArrayList(new Rows("Row Title1", "Row Description1", "http://imageUrl")));

        destinationsViewModel.getDestination().removeObservers(TestUtils.TEST_OBSERVER);
    }

    @Test
    public void observer_checkIfReturnRightValue() throws Exception {
        Observer<Destination> observer = destination -> {};

        destinationsViewModel.getDestination().observe(TestUtils.TEST_OBSERVER, observer);
        destinationsViewModel.destination.setValue(destination);

        assertEquals(destinationsViewModel.destination.getValue(), destination);
    }
}