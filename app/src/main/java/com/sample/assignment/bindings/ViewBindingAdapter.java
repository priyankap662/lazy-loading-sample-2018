package com.sample.assignment.bindings;

import android.databinding.BindingAdapter;
import android.view.View;

public class ViewBindingAdapter {

    @BindingAdapter("visibility")
    public static void setVisibility(final View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}