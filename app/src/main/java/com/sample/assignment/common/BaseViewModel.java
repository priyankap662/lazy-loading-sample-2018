package com.sample.assignment.common;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sample.assignment.data.source.remote.Factory;

import retrofit2.Call;

public class BaseViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    MutableLiveData<Throwable> errorData = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public <T> void enqueue(final Call<T> call, final Callable<T> success, final Callable<Throwable> error, boolean interactive) {
        isLoading.setValue(interactive);
        Factory.enqueue(call,
                success,
                Callable.empty(),
                throwable -> {
                    errorData.setValue(throwable);
                    error.call(throwable);
                },
                isSuccess -> isLoading.setValue(false));
    }
}
