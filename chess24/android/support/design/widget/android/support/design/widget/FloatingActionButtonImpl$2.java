/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.VisibilityAwareImageButton;

class FloatingActionButtonImpl
extends AnimatorListenerAdapter {
    final /* synthetic */ boolean val$fromUser;
    final /* synthetic */ FloatingActionButtonImpl.InternalVisibilityChangedListener val$listener;

    FloatingActionButtonImpl(boolean bl, FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener) {
        this.val$fromUser = bl;
        this.val$listener = internalVisibilityChangedListener;
    }

    public void onAnimationEnd(Animator animator) {
        FloatingActionButtonImpl.this.mAnimState = 0;
        if (this.val$listener != null) {
            this.val$listener.onShown();
        }
    }

    public void onAnimationStart(Animator animator) {
        FloatingActionButtonImpl.this.mView.internalSetVisibility(0, this.val$fromUser);
    }
}
