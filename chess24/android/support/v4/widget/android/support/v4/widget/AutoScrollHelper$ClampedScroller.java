/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.AnimationUtils
 */
package android.support.v4.widget;

import android.support.v4.widget.AutoScrollHelper;
import android.view.animation.AnimationUtils;

private static class AutoScrollHelper.ClampedScroller {
    private long mDeltaTime = 0L;
    private int mDeltaX = 0;
    private int mDeltaY = 0;
    private int mEffectiveRampDown;
    private int mRampDownDuration;
    private int mRampUpDuration;
    private long mStartTime = Long.MIN_VALUE;
    private long mStopTime = -1L;
    private float mStopValue;
    private float mTargetVelocityX;
    private float mTargetVelocityY;

    AutoScrollHelper.ClampedScroller() {
    }

    private float getValueAt(long l) {
        if (l < this.mStartTime) {
            return 0.0f;
        }
        if (this.mStopTime >= 0L && l >= this.mStopTime) {
            long l2 = this.mStopTime;
            return 1.0f - this.mStopValue + this.mStopValue * AutoScrollHelper.constrain((float)(l - l2) / (float)this.mEffectiveRampDown, 0.0f, 1.0f);
        }
        return 0.5f * AutoScrollHelper.constrain((float)(l - this.mStartTime) / (float)this.mRampUpDuration, 0.0f, 1.0f);
    }

    private float interpolateValue(float f) {
        return -4.0f * f * f + 4.0f * f;
    }

    public void computeScrollDelta() {
        if (this.mDeltaTime == 0L) {
            throw new RuntimeException("Cannot compute scroll delta before calling start()");
        }
        long l = AnimationUtils.currentAnimationTimeMillis();
        float f = this.interpolateValue(this.getValueAt(l));
        long l2 = this.mDeltaTime;
        this.mDeltaTime = l;
        f = (float)(l - l2) * f;
        this.mDeltaX = (int)(this.mTargetVelocityX * f);
        this.mDeltaY = (int)(f * this.mTargetVelocityY);
    }

    public int getDeltaX() {
        return this.mDeltaX;
    }

    public int getDeltaY() {
        return this.mDeltaY;
    }

    public int getHorizontalDirection() {
        return (int)(this.mTargetVelocityX / Math.abs(this.mTargetVelocityX));
    }

    public int getVerticalDirection() {
        return (int)(this.mTargetVelocityY / Math.abs(this.mTargetVelocityY));
    }

    public boolean isFinished() {
        if (this.mStopTime > 0L && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + (long)this.mEffectiveRampDown) {
            return true;
        }
        return false;
    }

    public void requestStop() {
        long l = AnimationUtils.currentAnimationTimeMillis();
        this.mEffectiveRampDown = AutoScrollHelper.constrain((int)(l - this.mStartTime), 0, this.mRampDownDuration);
        this.mStopValue = this.getValueAt(l);
        this.mStopTime = l;
    }

    public void setRampDownDuration(int n) {
        this.mRampDownDuration = n;
    }

    public void setRampUpDuration(int n) {
        this.mRampUpDuration = n;
    }

    public void setTargetVelocity(float f, float f2) {
        this.mTargetVelocityX = f;
        this.mTargetVelocityY = f2;
    }

    public void start() {
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStopTime = -1L;
        this.mDeltaTime = this.mStartTime;
        this.mStopValue = 0.5f;
        this.mDeltaX = 0;
        this.mDeltaY = 0;
    }
}
