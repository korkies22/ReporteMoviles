/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.SocketIOTournamentListService;
import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.SocketIO;
import java.util.List;

class SocketIOTournamentListService
implements IOAcknowledge {
    final /* synthetic */ SocketIO val$broadcastNodeSocket;

    SocketIOTournamentListService(SocketIO socketIO) {
        this.val$broadcastNodeSocket = socketIO;
    }

    @Override
    public /* varargs */ void ack(Object ... arrobject) {
        Logger.getInstance().debug("BroadcastSocketIO:", "connect answered:");
        for (int i = 0; i < arrobject.length; ++i) {
            Logger logger = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("args[");
            stringBuilder.append(i);
            stringBuilder.append("]: ");
            stringBuilder.append(arrobject[i]);
            logger.debug("BroadcastSocketIO:", stringBuilder.toString());
        }
        long l = arrobject.length > 0 && arrobject[0] instanceof Long ? (Long)arrobject[0] - System.currentTimeMillis() : 0L;
        1.this.this$0.getTournaments(1.this.val$callback, 1.this.val$offset, 1.this.val$limit, this.val$broadcastNodeSocket, l);
    }
}
