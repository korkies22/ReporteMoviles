/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.broadcast;

import android.view.View;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ boolean val$isInLiveMode;

    TournamentGameViewFragment(boolean bl) {
        this.val$isInLiveMode = bl;
    }

    @Override
    public void run() {
        if (TournamentGameViewFragment.this._liveText != null) {
            boolean bl = this.val$isInLiveMode;
            int n = 0;
            int n2 = !bl && TournamentGameViewFragment.this.isGameRunning() ? 1 : 0;
            View view = TournamentGameViewFragment.this._liveText;
            n2 = n2 != 0 ? n : 8;
            view.setVisibility(n2);
        }
    }
}
