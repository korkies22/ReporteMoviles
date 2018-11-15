/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.Paint
 *  android.view.View
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Paint;
import android.support.transition.Fade;
import android.support.transition.ViewUtils;
import android.support.v4.view.ViewCompat;
import android.view.View;

private static class Fade.FadeAnimatorListener
extends AnimatorListenerAdapter {
    private boolean mLayerTypeChanged = false;
    private final View mView;

    Fade.FadeAnimatorListener(View view) {
        this.mView = view;
    }

    public void onAnimationEnd(Animator animator) {
        ViewUtils.setTransitionAlpha(this.mView, 1.0f);
        if (this.mLayerTypeChanged) {
            this.mView.setLayerType(0, null);
        }
    }

    public void onAnimationStart(Animator animator) {
        if (ViewCompat.hasOverlappingRendering(this.mView) && this.mView.getLayerType() == 0) {
            this.mLayerTypeChanged = true;
            this.mView.setLayerType(2, null);
        }
    }
}
