package com.sample.assignment.utils;


import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.sample.assignment.common.Common;

public class Util {

    public static Resources getResources() {
        return Common.context().getResources();
    }

    public static String getString(@StringRes int stringRes, Object... formatArgs) {
        return getResources().getString(stringRes, formatArgs);
    }

    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(Common.context(), colorId);
    }
}

