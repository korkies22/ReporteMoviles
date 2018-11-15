/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package android.support.design.widget;

import android.animation.ValueAnimator;
import android.support.design.widget.CollapsingTextHelper;

class TextInputLayout
implements ValueAnimator.AnimatorUpdateListener {
    TextInputLayout() {
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(((Float)valueAnimator.getAnimatedValue()).floatValue());
    }
}
