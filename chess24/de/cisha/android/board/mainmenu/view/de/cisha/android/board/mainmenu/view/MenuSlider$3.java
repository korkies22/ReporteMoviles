/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board.mainmenu.view;

import android.view.animation.Animation;

class MenuSlider
implements Animation.AnimationListener {
    final /* synthetic */ boolean val$menuWillBeOpened;

    MenuSlider(boolean bl) {
        this.val$menuWillBeOpened = bl;
    }

    public void onAnimationEnd(Animation animation) {
        MenuSlider.this._menuOpened = this.val$menuWillBeOpened;
        if (MenuSlider.this._menuOpened) {
            MenuSlider.this.notifyObserversOpened();
        } else {
            MenuSlider.this.notifyObserversClosed();
        }
        MenuSlider.this._animationRunning = false;
        MenuSlider.this.clearAnimation();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
