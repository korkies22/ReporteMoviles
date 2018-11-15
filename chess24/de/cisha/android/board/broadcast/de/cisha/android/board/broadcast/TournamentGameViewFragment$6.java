/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.model.BroadcastGameHolder;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ int val$moveId;

    TournamentGameViewFragment(int n) {
        this.val$moveId = n;
    }

    @Override
    public void run() {
        TournamentGameViewFragment.this._broadcastGameHolder.deleteMoveInMainlineWithId(this.val$moveId);
        TournamentGameViewFragment.this.initiateClockWithMoveTimes();
    }
}
