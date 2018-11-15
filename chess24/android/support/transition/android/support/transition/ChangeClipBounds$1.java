/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.view.View
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.View;

class ChangeClipBounds
extends AnimatorListenerAdapter {
    final /* synthetic */ View val$endView;

    ChangeClipBounds(View view) {
        this.val$endView = view;
    }

    public void onAnimationEnd(Animator animator) {
        ViewCompat.setClipBounds(this.val$endView, null);
    }
}
