/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.chess.model.Game;

class TournamentGameViewFragment
implements Runnable {
    final /* synthetic */ Game val$game;

    TournamentGameViewFragment(Game game) {
        this.val$game = game;
    }

    @Override
    public void run() {
        TournamentGameViewFragment.this.setGame(this.val$game);
        TournamentGameViewFragment.this.hideDialogWaiting();
        TournamentGameViewFragment.this._flagReloadGame = false;
        TournamentGameViewFragment.this.liveModeChanged(true);
        TournamentGameViewFragment.this.updateViewsWithGame();
        TournamentGameViewFragment.this.initChessClock();
    }
}
