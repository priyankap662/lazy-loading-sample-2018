package com.sample.assignment.destination;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sample.assignment.common.BaseViewModel;
import com.sample.assignment.common.Callable;
import com.sample.assignment.data.source.remote.Factory;
import com.sample.assignment.models.Destination;

public class DestinationsViewModel extends BaseViewModel {

    public MutableLiveData<Destination> destination = new MutableLiveData<>();

    public DestinationsViewModel(@NonNull Application application) {
        super(application);
    }

    void fetchDestinationData(boolean... refresh) {
        enqueue(Factory.dataService().fetchDestination(refresh), this::onData, Callable.empty(), true);
    }

    private void onData(Destination destination) {
        this.destination.setValue(destination);
    }

    public LiveData<Destination> getDestination() {
        return destination;
    }
}
