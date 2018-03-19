package com.sample.assignment.utils;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.sample.assignment.R;

import java.util.Random;


public class RandomColor {
    private int lastIndex = -1;

    private final int[] res = {R.color.lazy_loading_1_color, R.color.lazy_loading_2_color, R.color.lazy_loading_3_color, R.color.lazy_loading_4_color, R.color.lazy_loading_5_color, R.color.lazy_loading_6_color};

    @SuppressWarnings("StatementWithEmptyBody")
    public Drawable getRandomLazyLoadingColor() {
        int index;
        Random random = new Random();
        while (lastIndex == (index = random.nextInt(res.length))) {
        }
        lastIndex = index;
        return new ColorDrawable(Util.getColor(res[lastIndex]));
    }
}
