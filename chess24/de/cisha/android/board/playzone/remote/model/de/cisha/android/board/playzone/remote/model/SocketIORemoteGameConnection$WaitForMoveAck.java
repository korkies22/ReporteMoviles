/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection;
import de.cisha.chess.model.Move;
import io.socket.SocketIO;
import java.util.TimerTask;

private class SocketIORemoteGameConnection.WaitForMoveAck
extends TimerTask {
    private Move _move;

    public SocketIORemoteGameConnection.WaitForMoveAck(Move move) {
        this._move = move;
    }

    @Override
    public void run() {
        if (this._move == SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck) {
            SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck = null;
            if (SocketIORemoteGameConnection.this._socketPlay != null) {
                SocketIORemoteGameConnection.this.doMove(this._move);
            }
        }
    }
}
