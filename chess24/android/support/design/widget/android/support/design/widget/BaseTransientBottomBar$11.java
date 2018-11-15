/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package android.support.design.widget;

import android.animation.ValueAnimator;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.view.ViewCompat;
import android.view.View;

class BaseTransientBottomBar
implements ValueAnimator.AnimatorUpdateListener {
    private int mPreviousAnimatedIntValue = 0;

    BaseTransientBottomBar() {
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int n = (Integer)valueAnimator.getAnimatedValue();
        if (USE_OFFSET_API) {
            ViewCompat.offsetTopAndBottom((View)BaseTransientBottomBar.this.mView, n - this.mPreviousAnimatedIntValue);
        } else {
            BaseTransientBottomBar.this.mView.setTranslationY((float)n);
        }
        this.mPreviousAnimatedIntValue = n;
    }
}
