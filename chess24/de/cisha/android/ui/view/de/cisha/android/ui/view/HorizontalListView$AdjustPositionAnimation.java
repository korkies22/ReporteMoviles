/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.Animation
 *  android.view.animation.Interpolator
 *  android.view.animation.Transformation
 */
package de.cisha.android.ui.view;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import de.cisha.android.ui.view.HorizontalListView;

private class HorizontalListView.AdjustPositionAnimation
extends Animation {
    private int mAlreadyScrolled = 0;
    private boolean mCancelled = false;
    private int mPixelsToScrollLeft;

    public HorizontalListView.AdjustPositionAnimation(int n) {
        this.mPixelsToScrollLeft = n;
        this.setInterpolator((Interpolator)new AccelerateDecelerateInterpolator());
        this.setDuration(200L);
    }

    protected void applyTransformation(float f, Transformation object) {
        if (!this.mCancelled) {
            int n = (int)(f * (float)this.mPixelsToScrollLeft) - this.mAlreadyScrolled;
            this.mAlreadyScrolled += n;
            object = HorizontalListView.this;
            object.mNextX += n;
            HorizontalListView.this.adjustSubviewPositions();
        }
    }

    public void stop() {
        this.mCancelled = true;
    }
}
