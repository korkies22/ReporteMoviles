/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.SocketIOTournamentListService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIOException;
import java.util.List;
import org.json.JSONObject;

class SocketIOTournamentListService
implements IOCallback {
    SocketIOTournamentListService() {
    }

    @Override
    public /* varargs */ void on(String object, IOAcknowledge object2, Object ... arrobject) {
        object2 = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("on:");
        stringBuilder.append((String)object);
        object2.debug("BroadcastSocketIO:", stringBuilder.toString());
        for (int i = 0; i < arrobject.length; ++i) {
            object = Logger.getInstance();
            object2 = new StringBuilder();
            object2.append("args[");
            object2.append(i);
            object2.append("]: ");
            object2.append(arrobject[i]);
            object.debug("BroadcastSocketIO:", object2.toString());
        }
    }

    @Override
    public void onConnect() {
        Logger.getInstance().debug("BroadcastSocketIO:", "onConnect:");
    }

    @Override
    public void onDisconnect() {
        Logger.getInstance().debug("BroadcastSocketIO:", "onDisconnect:");
        1.this.val$callback.loadingFailed(APIStatusCode.INTERNAL_UNKNOWN, "connection disconnected", null, null);
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        Logger.getInstance().debug("BroadcastSocketIO:", "onError:", socketIOException);
    }

    @Override
    public void onMessage(String string, IOAcknowledge object) {
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onMessage:");
        stringBuilder.append(string);
        object.debug("BroadcastSocketIO:", stringBuilder.toString());
    }

    @Override
    public void onMessage(JSONObject jSONObject, IOAcknowledge object) {
        object = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onMessage:");
        stringBuilder.append((Object)jSONObject);
        object.debug("BroadcastSocketIO:", stringBuilder.toString());
    }
}
