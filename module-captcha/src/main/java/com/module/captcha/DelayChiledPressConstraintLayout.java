package com.module.captcha;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DelayChiledPressConstraintLayout extends FrameLayout {


    private boolean delay = false;

    public DelayChiledPressConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public DelayChiledPressConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DelayChiledPressConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return delay;
    }
}
