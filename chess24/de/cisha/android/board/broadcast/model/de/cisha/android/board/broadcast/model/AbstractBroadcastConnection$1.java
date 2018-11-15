/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.AbstractTimeoutIOAcknowledge;
import de.cisha.android.board.broadcast.model.SocketIOTournamentConnection;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import io.socket.ConnectionListener;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

class AbstractBroadcastConnection
extends LoadCommandCallbackWithTimeout<NodeServerAddress> {
    AbstractBroadcastConnection(int n) {
        super(n);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        AbstractBroadcastConnection.this._isConnected = false;
        AbstractBroadcastConnection.this.notifyListenerConnectionFailed();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void succeded(NodeServerAddress object) {
        AbstractTimeoutIOAcknowledge abstractTimeoutIOAcknowledge;
        SocketIO socketIO;
        try {
            object = new URL(object.getURLString());
            if (AbstractBroadcastConnection.this._tournamentsSocket != null && AbstractBroadcastConnection.this._tournamentsSocket.isConnected()) {
                AbstractBroadcastConnection.this._tournamentsSocket.disconnect();
            }
            AbstractBroadcastConnection.this._isConnected = true;
            AbstractBroadcastConnection.this._tournamentsSocket = new SocketIO((URL)object, (IOCallback)AbstractBroadcastConnection.this, new ConnectionListener(){

                @Override
                public void onConnectionStateChanged(boolean bl) {
                }
            });
            object = ServiceProvider.getInstance().getLoginService().getAuthToken();
            socketIO = AbstractBroadcastConnection.this._tournamentsSocket;
            abstractTimeoutIOAcknowledge = new AbstractTimeoutIOAcknowledge(){

                @Override
                /* varargs */ void onAck(Object ... object) {
                    if (((Object[])object).length > 0 && object[0] instanceof Long) {
                        long l = (Long)object[0];
                        AbstractBroadcastConnection.this._timeOffset = l - System.currentTimeMillis();
                    }
                    AbstractBroadcastConnection.this.connectingSuccessful();
                    object = AbstractBroadcastConnection.this._listeners.iterator();
                    while (object.hasNext()) {
                        ((IConnection.IConnectionListener)object.next()).connectionEstablished(AbstractBroadcastConnection.this);
                    }
                }

                @Override
                void onTimeout() {
                    AbstractBroadcastConnection.this._tournamentsSocket.disconnect();
                }
            };
            object = object != null ? object.getUuid() : "meinAuthTokenIstOk";
        }
        catch (MalformedURLException malformedURLException) {
            Logger.getInstance().error(SocketIOTournamentConnection.class.getName(), MalformedURLException.class.getName(), malformedURLException);
            AbstractBroadcastConnection.this.notifyListenerDisconnect();
            return;
        }
        socketIO.emit("connect", abstractTimeoutIOAcknowledge, object);
    }

}
