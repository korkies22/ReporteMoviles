/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.position.Position;
import java.util.Timer;
import java.util.TimerTask;

private class RemoteGameAdapter.TimeoutTask
extends TimerTask {
    private RemoteGameAdapter.TimeoutTask() {
    }

    @Override
    public void run() {
        if (!RemoteGameAdapter.this._reconnectStateWaitingForResponse) {
            RemoteGameAdapter.this._gameHolder.setGameActive(false);
            Object object = RemoteGameAdapter.this._playerIsWhite ? GameResult.BLACK_WINS : GameResult.WHITE_WINS;
            object = new GameStatus((GameResult)((Object)object), GameEndReason.DISCONNECT_TIMEOUT);
            RemoteGameAdapter.this._delegate.onGameEnd(new GameEndInformation((GameStatus)object, RemoteGameAdapter.this.getPosition().getActiveColor()));
            return;
        }
        RemoteGameAdapter.this._timer.schedule((TimerTask)new RemoteGameAdapter.TimeoutTask(), 500L);
    }
}
