/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.Paint
 *  android.view.View
 */
package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Paint;
import android.support.v4.app.FragmentManagerImpl;
import android.view.View;

private static class FragmentManagerImpl.AnimatorOnHWLayerIfNeededListener
extends AnimatorListenerAdapter {
    View mView;

    FragmentManagerImpl.AnimatorOnHWLayerIfNeededListener(View view) {
        this.mView = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.mView.setLayerType(0, null);
        animator.removeListener((Animator.AnimatorListener)this);
    }

    public void onAnimationStart(Animator animator) {
        this.mView.setLayerType(2, null);
    }
}
