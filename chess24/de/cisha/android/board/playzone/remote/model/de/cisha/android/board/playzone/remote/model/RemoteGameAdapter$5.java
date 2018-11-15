/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ int val$abortTimeout;
    final /* synthetic */ Game val$game;
    final /* synthetic */ int val$winTimeout;

    RemoteGameAdapter(Game game, int n, int n2) {
        this.val$game = game;
        this.val$abortTimeout = n;
        this.val$winTimeout = n2;
    }

    @Override
    public void run() {
        de.cisha.android.board.playzone.remote.model.RemoteGameAdapter.super.onGameStart(this.val$game, this.val$abortTimeout, this.val$winTimeout);
        this.val$game.setType(GameType.ONLINEPLAYZONE);
        this.val$game.setSite("chess24.com");
        this.val$game.setTitle("Online Playzone Game");
        RemoteGameAdapter.this._winTimeout = this.val$winTimeout;
        RemoteGameAdapter.this._abortTimeout = this.val$abortTimeout;
        if (RemoteGameAdapter.this._lastSentMove != null) {
            RemoteGameAdapter.this._gameHolder.doMoveInCurrentPosition(RemoteGameAdapter.this._lastSentMove.getSEP());
        }
    }
}
