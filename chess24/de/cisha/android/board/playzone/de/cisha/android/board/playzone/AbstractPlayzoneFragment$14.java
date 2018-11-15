/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
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
        if (AbstractPlayzoneFragment.this._afterGameView != null) {
            AbstractPlayzoneFragment.this._fragmentView.removeView(AbstractPlayzoneFragment.this._afterGameView);
        }
        AbstractPlayzoneFragment.this._afterGameView = null;
    }
}
