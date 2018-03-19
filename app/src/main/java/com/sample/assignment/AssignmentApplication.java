package com.sample.assignment;


import android.app.Application;

import com.sample.assignment.common.Common;

public class AssignmentApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Common.init(getApplicationContext(), this);
    }
}
