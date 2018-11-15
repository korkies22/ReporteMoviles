/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.playzone.view.ChessClockView;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ boolean val$colorWhite;
    final /* synthetic */ long val$timeMillis;

    TournamentGameViewFragment(boolean bl, long l) {
        this.val$colorWhite = bl;
        this.val$timeMillis = l;
    }

    @Override
    public void run() {
        TournamentGameViewFragment.this._chessClockView.setTime(this.val$colorWhite, this.val$timeMillis);
    }
}
