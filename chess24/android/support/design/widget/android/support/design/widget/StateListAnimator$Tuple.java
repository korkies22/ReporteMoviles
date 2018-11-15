/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 */
package android.support.design.widget;

import android.animation.ValueAnimator;
import android.support.design.widget.StateListAnimator;

static class StateListAnimator.Tuple {
    final ValueAnimator mAnimator;
    final int[] mSpecs;

    StateListAnimator.Tuple(int[] arrn, ValueAnimator valueAnimator) {
        this.mSpecs = arrn;
        this.mAnimator = valueAnimator;
    }
}
