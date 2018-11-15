/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.widget;

import android.support.v4.view.ViewCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.view.View;

private class AutoScrollHelper.ScrollAnimationRunnable
implements Runnable {
    AutoScrollHelper.ScrollAnimationRunnable() {
    }

    @Override
    public void run() {
        AutoScrollHelper.ClampedScroller clampedScroller;
        if (!AutoScrollHelper.this.mAnimating) {
            return;
        }
        if (AutoScrollHelper.this.mNeedsReset) {
            AutoScrollHelper.this.mNeedsReset = false;
            AutoScrollHelper.this.mScroller.start();
        }
        if (!(clampedScroller = AutoScrollHelper.this.mScroller).isFinished() && AutoScrollHelper.this.shouldAnimate()) {
            if (AutoScrollHelper.this.mNeedsCancel) {
                AutoScrollHelper.this.mNeedsCancel = false;
                AutoScrollHelper.this.cancelTargetTouch();
            }
            clampedScroller.computeScrollDelta();
            int n = clampedScroller.getDeltaX();
            int n2 = clampedScroller.getDeltaY();
            AutoScrollHelper.this.scrollTargetBy(n, n2);
            ViewCompat.postOnAnimation(AutoScrollHelper.this.mTarget, this);
            return;
        }
        AutoScrollHelper.this.mAnimating = false;
    }
}
