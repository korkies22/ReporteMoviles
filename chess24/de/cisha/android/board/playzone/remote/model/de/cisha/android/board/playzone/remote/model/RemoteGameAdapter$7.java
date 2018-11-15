/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;
import de.cisha.chess.model.Move;
import java.util.Timer;
import java.util.TimerTask;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ GameEndInformation val$gameEndInfo;

    RemoteGameAdapter(GameEndInformation gameEndInformation) {
        this.val$gameEndInfo = gameEndInformation;
    }

    @Override
    public void run() {
        RemoteGameAdapter.this._delegate.onGameEnd(this.val$gameEndInfo);
        RemoteGameAdapter.this._disconnectTask = new TimerTask(){

            @Override
            public void run() {
                if (RemoteGameAdapter.this._connection != null) {
                    RemoteGameAdapter.this._connection.disconnect();
                    RemoteGameAdapter.this._delegate.onOpponentDeclinedRematch();
                }
            }
        };
        RemoteGameAdapter.this._timer.schedule(RemoteGameAdapter.this._disconnectTask, 30000L);
        RemoteGameAdapter.this._lastSentMove = null;
    }

}
