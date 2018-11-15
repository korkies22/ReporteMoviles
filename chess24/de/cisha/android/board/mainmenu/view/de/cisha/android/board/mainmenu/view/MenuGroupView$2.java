/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.ImageView
 */
package de.cisha.android.board.mainmenu.view;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

class MenuGroupView
implements Animation.AnimationListener {
    MenuGroupView() {
    }

    public void onAnimationEnd(Animation animation) {
        MenuGroupView.this._animating = false;
        MenuGroupView.this._open = true;
        MenuGroupView.this._itemContainer.clearAnimation();
        MenuGroupView.this._arrowImageView.clearAnimation();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
