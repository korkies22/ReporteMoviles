/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.chess.model.GameStatus;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ GameStatus val$gameStatus;

    RemoteGameAdapter(GameStatus gameStatus) {
        this.val$gameStatus = gameStatus;
    }

    @Override
    public void run() {
        RemoteGameAdapter.this._gameHolder.setGameActive(false);
        RemoteGameAdapter.this._delegate.onGameSessionEnded(this.val$gameStatus);
    }
}
