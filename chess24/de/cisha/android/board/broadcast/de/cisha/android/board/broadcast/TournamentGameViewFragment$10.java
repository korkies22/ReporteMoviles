/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.playzone.view.ChessClockView;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ boolean val$running;

    TournamentGameViewFragment(boolean bl) {
        this.val$running = bl;
    }

    @Override
    public void run() {
        TournamentGameViewFragment.this._chessClockView.setRunning(this.val$running);
    }
}
