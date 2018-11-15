/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.widget.TextView
 */
package android.support.design.internal;

import android.animation.ValueAnimator;
import android.widget.TextView;

class TextScale
implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ TextView val$view;

    TextScale(TextView textView) {
        this.val$view = textView;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        this.val$view.setScaleX(f);
        this.val$view.setScaleY(f);
    }
}
