/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.AbstractTimeoutIOAcknowledge;
import de.cisha.android.board.broadcast.model.IBroadcastService;
import de.cisha.android.board.broadcast.model.SocketIOTournamentConnection;
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
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import org.json.JSONObject;

public abstract class AbstractBroadcastConnection
implements IConnection,
IOCallback {
    private boolean _isConnected;
    private Set<IConnection.IConnectionListener> _listeners = Collections.newSetFromMap(new WeakHashMap());
    private long _timeOffset;
    private SocketIO _tournamentsSocket;

    public AbstractBroadcastConnection(IConnection.IConnectionListener iConnectionListener) {
        this._listeners.add(iConnectionListener);
    }

    private void notifyListenerConnectionFailed() {
        synchronized (this) {
            if (this._listeners != null) {
                Iterator<IConnection.IConnectionListener> iterator = this._listeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().connectionFailed(this);
                }
            }
            this._isConnected = false;
            return;
        }
    }

    private void notifyListenerDisconnect() {
        synchronized (this) {
            if (this._isConnected && this._listeners != null) {
                Iterator<IConnection.IConnectionListener> iterator = this._listeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().connectionClosed(this);
                }
            }
            this._isConnected = false;
            return;
        }
    }

    @Override
    public void addConnectionListener(IConnection.IConnectionListener iConnectionListener) {
        this._listeners.add(iConnectionListener);
    }

    @Override
    public void close() {
        if (this._tournamentsSocket != null) {
            this._tournamentsSocket.disconnect();
        }
    }

    @Override
    public void connect() {
        ServiceProvider.getInstance().getBroadCastService().getBroadcastServerAdress((LoadCommandCallback<NodeServerAddress>)new LoadCommandCallbackWithTimeout<NodeServerAddress>(20000){

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

        });
    }

    protected abstract void connectingSuccessful();

    protected long getServerTimeOffset() {
        return this._timeOffset;
    }

    protected SocketIO getSocket() {
        return this._tournamentsSocket;
    }

    @Override
    public boolean isConnected() {
        if (this._tournamentsSocket != null && this._tournamentsSocket.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public /* varargs */ void on(String object3, IOAcknowledge object2, Object ... arrobject) {
        object2 = Logger.getInstance();
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.getClass().getName());
        charSequence.append(" Event");
        object2.debug(charSequence.toString(), (String)object3);
        for (Object object3 : arrobject) {
            object2 = Logger.getInstance();
            charSequence = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(object3);
            object2.debug((String)charSequence, stringBuilder.toString());
        }
    }

    @Override
    public void onConnect() {
    }

    @Override
    public void onDisconnect() {
        this.notifyListenerDisconnect();
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        this.notifyListenerDisconnect();
    }

    @Override
    public void onMessage(String string, IOAcknowledge iOAcknowledge) {
    }

    @Override
    public void onMessage(JSONObject jSONObject, IOAcknowledge iOAcknowledge) {
    }

    protected void subscribeToRKey(String string) {
        this.getSocket().emit("subscribe", string);
    }

}
