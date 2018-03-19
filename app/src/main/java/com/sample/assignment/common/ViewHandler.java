package com.sample.assignment.common;

import android.support.annotation.IdRes;
import android.view.View;

public interface ViewHandler {

    @SuppressWarnings("unchecked")
    default <T extends View> T findById(View parent, @IdRes int id) {
        return (T) parent.findViewById(id);
    }
}
