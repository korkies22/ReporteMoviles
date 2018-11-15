/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.board.tactics;

import android.view.animation.Animation;
import de.cisha.android.board.view.BoardView;

class TacticsStopFragment
implements Animation.AnimationListener {
    TacticsStopFragment() {
    }

    public void onAnimationEnd(Animation animation) {
        if (TacticsStopFragment.this._boardView != null) {
            TacticsStopFragment.this._boardView.setIsZooming(false);
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
        if (TacticsStopFragment.this._boardView != null) {
            TacticsStopFragment.this._boardView.setIsZooming(true);
        }
    }
}
