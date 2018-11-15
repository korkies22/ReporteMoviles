/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.SocketIOTournamentListService;
import de.cisha.chess.util.Logger;
import io.socket.ConnectionListener;

class SocketIOTournamentListService
implements ConnectionListener {
    SocketIOTournamentListService() {
    }

    @Override
    public void onConnectionStateChanged(boolean bl) {
        Logger logger = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onConnectionstateChanged:");
        stringBuilder.append(bl);
        logger.debug("BroadcastSocketIO:", stringBuilder.toString());
    }
}
