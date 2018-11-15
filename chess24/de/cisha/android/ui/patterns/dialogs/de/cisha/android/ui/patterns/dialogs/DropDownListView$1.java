/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.animation.Animation;

class DropDownListView
implements Animation.AnimationListener {
    final /* synthetic */ boolean val$openOrClose;

    DropDownListView(boolean bl) {
        this.val$openOrClose = bl;
    }

    public void onAnimationEnd(Animation animation) {
        DropDownListView.this.clearAnimation();
        DropDownListView.this._opened = this.val$openOrClose;
        DropDownListView.this._animationIsRunning = false;
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
