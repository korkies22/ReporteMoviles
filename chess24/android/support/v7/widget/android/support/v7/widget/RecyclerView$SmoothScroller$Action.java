/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.Interpolator;

public static class RecyclerView.SmoothScroller.Action {
    public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
    private boolean mChanged = false;
    private int mConsecutiveUpdates = 0;
    private int mDuration;
    private int mDx;
    private int mDy;
    private Interpolator mInterpolator;
    private int mJumpToPosition = -1;

    public RecyclerView.SmoothScroller.Action(int n, int n2) {
        this(n, n2, Integer.MIN_VALUE, null);
    }

    public RecyclerView.SmoothScroller.Action(int n, int n2, int n3) {
        this(n, n2, n3, null);
    }

    public RecyclerView.SmoothScroller.Action(int n, int n2, int n3, Interpolator interpolator) {
        this.mDx = n;
        this.mDy = n2;
        this.mDuration = n3;
        this.mInterpolator = interpolator;
    }

    private void validate() {
        if (this.mInterpolator != null && this.mDuration < 1) {
            throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
        }
        if (this.mDuration < 1) {
            throw new IllegalStateException("Scroll duration must be a positive number");
        }
    }

    public int getDuration() {
        return this.mDuration;
    }

    public int getDx() {
        return this.mDx;
    }

    public int getDy() {
        return this.mDy;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    boolean hasJumpTarget() {
        if (this.mJumpToPosition >= 0) {
            return true;
        }
        return false;
    }

    public void jumpTo(int n) {
        this.mJumpToPosition = n;
    }

    void runIfNecessary(RecyclerView recyclerView) {
        if (this.mJumpToPosition >= 0) {
            int n = this.mJumpToPosition;
            this.mJumpToPosition = -1;
            recyclerView.jumpToPositionForSmoothScroller(n);
            this.mChanged = false;
            return;
        }
        if (this.mChanged) {
            this.validate();
            if (this.mInterpolator == null) {
                if (this.mDuration == Integer.MIN_VALUE) {
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                } else {
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                }
            } else {
                recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
            }
            ++this.mConsecutiveUpdates;
            if (this.mConsecutiveUpdates > 10) {
                Log.e((String)RecyclerView.TAG, (String)"Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
            }
            this.mChanged = false;
            return;
        }
        this.mConsecutiveUpdates = 0;
    }

    public void setDuration(int n) {
        this.mChanged = true;
        this.mDuration = n;
    }

    public void setDx(int n) {
        this.mChanged = true;
        this.mDx = n;
    }

    public void setDy(int n) {
        this.mChanged = true;
        this.mDy = n;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mChanged = true;
        this.mInterpolator = interpolator;
    }

    public void update(int n, int n2, int n3, Interpolator interpolator) {
        this.mDx = n;
        this.mDy = n2;
        this.mDuration = n3;
        this.mInterpolator = interpolator;
        this.mChanged = true;
    }
}
