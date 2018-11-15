/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.playzone.remote.model.RemoteGameDelegator;
import de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.chess.model.CishaUUID;
import java.util.TimerTask;

private class RemoteGameAdapter.ReconnectTask
extends TimerTask {
    private RemoteGameAdapter.ReconnectTask() {
    }

    @Override
    public void run() {
        if (RemoteGameAdapter.this._connection != null) {
            RemoteGameAdapter.this._connection.disconnect();
        }
        RemoteGameAdapter.this._connection = new SocketIORemoteGameConnection(RemoteGameAdapter.this._playing, RemoteGameAdapter.this._pairing, RemoteGameAdapter.this._authToken);
        RemoteGameAdapter.this._connection.initGameSession(RemoteGameAdapter.this._gameSessionToken, RemoteGameAdapter.this._playerIsWhite, RemoteGameAdapter.this);
    }
}
