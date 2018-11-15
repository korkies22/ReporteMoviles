/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;
import de.cisha.chess.model.Game;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ Game val$game;
    final /* synthetic */ GameEndInformation val$gameEndInfo;

    RemoteGameAdapter(Game game, GameEndInformation gameEndInformation) {
        this.val$game = game;
        this.val$gameEndInfo = gameEndInformation;
    }

    @Override
    public void run() {
        RemoteGameAdapter.this._gameHolder.setNewGame(this.val$game);
        RemoteGameAdapter.this._game = this.val$game;
        RemoteGameAdapter.this._gameHolder.setGameActive(false);
        RemoteGameAdapter.this._delegate.onGameSessionOver(this.val$game, this.val$gameEndInfo);
        RemoteGameAdapter.this._delegate.onOpponentDeclinedRematch();
        if (RemoteGameAdapter.this._connection != null) {
            RemoteGameAdapter.this._connection.disconnect();
        }
    }
}
