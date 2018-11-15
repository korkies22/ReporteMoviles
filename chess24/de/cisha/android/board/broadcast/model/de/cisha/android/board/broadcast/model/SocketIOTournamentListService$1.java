/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import io.socket.ConnectionListener;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import java.net.MalformedURLException;
import java.util.List;
import org.json.JSONObject;

class SocketIOTournamentListService
extends LoadCommandCallbackWithTimeout<NodeServerAddress> {
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ int val$limit;
    final /* synthetic */ int val$offset;

    SocketIOTournamentListService(int n, LoadCommandCallback loadCommandCallback, int n2, int n3) {
        this.val$callback = loadCommandCallback;
        this.val$offset = n2;
        this.val$limit = n3;
        super(n);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.loadingFailed(aPIStatusCode, string, null, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void succeded(NodeServerAddress object) {
        SocketIO socketIO;
        IOAcknowledge iOAcknowledge;
        try {
            socketIO = new SocketIO(object.getURLString());
            socketIO.connect(new IOCallback(){

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
            }, new ConnectionListener(){

                @Override
                public void onConnectionStateChanged(boolean bl) {
                    Logger logger = Logger.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onConnectionstateChanged:");
                    stringBuilder.append(bl);
                    logger.debug("BroadcastSocketIO:", stringBuilder.toString());
                }
            });
            object = ServiceProvider.getInstance().getLoginService().getAuthToken();
            iOAcknowledge = new IOAcknowledge(){

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
                    SocketIOTournamentListService.this.getTournaments(1.this.val$callback, 1.this.val$offset, 1.this.val$limit, socketIO, l);
                }
            };
            object = object != null ? object.getUuid() : "dummyToken";
        }
        catch (MalformedURLException malformedURLException) {
            Logger.getInstance().debug(de.cisha.android.board.broadcast.model.SocketIOTournamentListService.class.getName(), MalformedURLException.class.getName(), malformedURLException);
            return;
        }
        socketIO.emit("connect", iOAcknowledge, object);
    }

}
