/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.transition.ViewUtils;
import android.view.View;
import android.view.ViewGroup;

class ChangeBounds
extends AnimatorListenerAdapter {
    final /* synthetic */ BitmapDrawable val$drawable;
    final /* synthetic */ ViewGroup val$sceneRoot;
    final /* synthetic */ float val$transitionAlpha;
    final /* synthetic */ View val$view;

    ChangeBounds(ViewGroup viewGroup, BitmapDrawable bitmapDrawable, View view, float f) {
        this.val$sceneRoot = viewGroup;
        this.val$drawable = bitmapDrawable;
        this.val$view = view;
        this.val$transitionAlpha = f;
    }

    public void onAnimationEnd(Animator animator) {
        ViewUtils.getOverlay((View)this.val$sceneRoot).remove((Drawable)this.val$drawable);
        ViewUtils.setTransitionAlpha(this.val$view, this.val$transitionAlpha);
    }
}
