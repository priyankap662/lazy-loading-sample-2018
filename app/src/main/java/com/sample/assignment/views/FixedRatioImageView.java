package com.sample.assignment.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.sample.assignment.R;

public class FixedRatioImageView extends AppCompatImageView {

    public static final int MEASUREMENT_WIDTH = 0;
    public static final int MEASUREMENT_HEIGHT = 1;

    private static final int DEFAULT_ASPECT_RATIO = 0;
    private static final boolean DEFAULT_ASPECT_RATIO_ENABLED = false;
    private static final int DEFAULT_DOMINANT_MEASUREMENT = MEASUREMENT_WIDTH;

    private float aspectRatio;
    private boolean aspectRatioEnabled;
    private int dominantMeasurement;

    public FixedRatioImageView(Context context) {
        this(context, null);
    }

    public FixedRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FixedRatioImageView);
        aspectRatio = (float) typedArray.getInt(R.styleable.FixedRatioImageView_heightValue, DEFAULT_ASPECT_RATIO)
                / (float) typedArray.getInt(R.styleable.FixedRatioImageView_widthValue, DEFAULT_ASPECT_RATIO);

        aspectRatioEnabled = typedArray.getBoolean(R.styleable.FixedRatioImageView_aspectRatioEnabled,
                DEFAULT_ASPECT_RATIO_ENABLED);
        dominantMeasurement = typedArray.getInt(R.styleable.FixedRatioImageView_dominantMeasurement,
                DEFAULT_DOMINANT_MEASUREMENT);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (aspectRatioEnabled) {
            int newWidth;
            int newHeight;
            switch (dominantMeasurement) {
                case MEASUREMENT_WIDTH:
                    newWidth = getMeasuredWidth();
                    newHeight = (int) (newWidth * aspectRatio);
                    break;

                case MEASUREMENT_HEIGHT:
                    newHeight = getMeasuredHeight();
                    newWidth = (int) (newHeight / aspectRatio);
                    break;

                default:
                    throw new IllegalStateException("Unknown measurement with ID ");
            }
            setMeasuredDimension(newWidth, newHeight);
        }
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        if (aspectRatioEnabled) {
            requestLayout();
        }
    }

    public boolean getAspectRatioEnabled() {
        return aspectRatioEnabled;
    }

    public void setAspectRatioEnabled(boolean aspectRatioEnabled) {
        this.aspectRatioEnabled = aspectRatioEnabled;
        requestLayout();
    }

    public int getDominantMeasurement() {
        return dominantMeasurement;
    }

    public void setDominantMeasurement(int dominantMeasurement) {
        if (dominantMeasurement != MEASUREMENT_HEIGHT && dominantMeasurement != MEASUREMENT_WIDTH) {
            throw new IllegalArgumentException("Invalid measurement type.");
        }
        this.dominantMeasurement = dominantMeasurement;
        requestLayout();
    }
}
