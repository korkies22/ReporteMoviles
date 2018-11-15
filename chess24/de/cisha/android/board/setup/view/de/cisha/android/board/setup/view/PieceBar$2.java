/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board.setup.view;

import android.view.View;
import android.view.animation.Animation;

class PieceBar
implements Animation.AnimationListener {
    final /* synthetic */ boolean val$show;

    PieceBar(boolean bl) {
        this.val$show = bl;
    }

    public void onAnimationEnd(Animation animation) {
        PieceBar.this.post(new Runnable(){

            @Override
            public void run() {
                if (!2.this.val$show) {
                    PieceBar.this._overlayDelete.setVisibility(4);
                    PieceBar.this._overlayDelete.clearAnimation();
                }
            }
        });
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }

}
