/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package de.cisha.android.board.playzone;

import android.view.View;
import android.view.ViewGroup;

class AbstractPlayzoneFragment
implements Runnable {
    AbstractPlayzoneFragment() {
    }

    @Override
    public void run() {
        if (AbstractPlayzoneFragment.this._afterGameView == null) {
            AbstractPlayzoneFragment.this._afterGameView = AbstractPlayzoneFragment.this.getAfterGameView();
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            AbstractPlayzoneFragment.this._fragmentView.addView(AbstractPlayzoneFragment.this._afterGameView, layoutParams);
        }
        AbstractPlayzoneFragment.this._afterGameView.bringToFront();
    }
}
