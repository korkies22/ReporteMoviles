/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.broadcast.video;

import android.view.View;

class TournamentVideoFragment
implements Runnable {
    TournamentVideoFragment() {
    }

    @Override
    public void run() {
        TournamentVideoFragment.this._videoContainerView.setVisibility(0);
    }
}
