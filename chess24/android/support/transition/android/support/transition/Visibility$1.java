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
import android.support.transition.ViewGroupOverlayImpl;
import android.view.View;

class Visibility
extends AnimatorListenerAdapter {
    final /* synthetic */ View val$finalOverlayView;
    final /* synthetic */ ViewGroupOverlayImpl val$overlay;

    Visibility(ViewGroupOverlayImpl viewGroupOverlayImpl, View view) {
        this.val$overlay = viewGroupOverlayImpl;
        this.val$finalOverlayView = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.val$overlay.remove(this.val$finalOverlayView);
    }
}
