/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board.mainmenu.view;

import android.view.animation.Animation;

class MenuGroupView
implements Animation.AnimationListener {
    MenuGroupView() {
    }

    public void onAnimationEnd(Animation animation) {
        MenuGroupView.this._animating = false;
        MenuGroupView.this._open = false;
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
