// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import io.socket.SocketIOException;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.CishaUUID;
import java.net.MalformedURLException;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.ConnectionListener;
import java.net.URL;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import io.socket.SocketIO;
import java.util.Set;
import io.socket.IOCallback;
import de.cisha.android.board.broadcast.connection.IConnection;

public abstract class AbstractBroadcastConnection implements IConnection, IOCallback
{
    private boolean _isConnected;
    private Set<IConnectionListener> _listeners;
    private long _timeOffset;
    private SocketIO _tournamentsSocket;
    
    public AbstractBroadcastConnection(final IConnectionListener connectionListener) {
        (this._listeners = Collections.newSetFromMap(new WeakHashMap<IConnectionListener, Boolean>())).add(connectionListener);
    }
    
    private void notifyListenerConnectionFailed() {
        synchronized (this) {
            if (this._listeners != null) {
                final Iterator<IConnectionListener> iterator = this._listeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().connectionFailed(this);
                }
            }
            this._isConnected = false;
        }
    }
    
    private void notifyListenerDisconnect() {
        synchronized (this) {
            if (this._isConnected && this._listeners != null) {
                final Iterator<IConnectionListener> iterator = this._listeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().connectionClosed(this);
                }
            }
            this._isConnected = false;
        }
    }
    
    @Override
    public void addConnectionListener(final IConnectionListener connectionListener) {
        this._listeners.add(connectionListener);
    }
    
    @Override
    public void close() {
        if (this._tournamentsSocket != null) {
            this._tournamentsSocket.disconnect();
        }
    }
    
    @Override
    public void connect() {
        ServiceProvider.getInstance().getBroadCastService().getBroadcastServerAdress(new LoadCommandCallbackWithTimeout<NodeServerAddress>(20000) {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                AbstractBroadcastConnection.this._isConnected = false;
                AbstractBroadcastConnection.this.notifyListenerConnectionFailed();
            }
            
            @Override
            protected void succeded(final NodeServerAddress nodeServerAddress) {
                while (true) {
                    while (true) {
                        try {
                            final URL url = new URL(nodeServerAddress.getURLString());
                            if (AbstractBroadcastConnection.this._tournamentsSocket != null && AbstractBroadcastConnection.this._tournamentsSocket.isConnected()) {
                                AbstractBroadcastConnection.this._tournamentsSocket.disconnect();
                            }
                            AbstractBroadcastConnection.this._isConnected = true;
                            AbstractBroadcastConnection.this._tournamentsSocket = new SocketIO(url, AbstractBroadcastConnection.this, new ConnectionListener() {
                                @Override
                                public void onConnectionStateChanged(final boolean b) {
                                }
                            });
                            final CishaUUID authToken = ServiceProvider.getInstance().getLoginService().getAuthToken();
                            final SocketIO access.000 = AbstractBroadcastConnection.this._tournamentsSocket;
                            final AbstractTimeoutIOAcknowledge abstractTimeoutIOAcknowledge = new AbstractTimeoutIOAcknowledge() {
                                @Override
                                void onAck(final Object... array) {
                                    if (array.length > 0 && array[0] instanceof Long) {
                                        AbstractBroadcastConnection.this._timeOffset = (long)array[0] - System.currentTimeMillis();
                                    }
                                    AbstractBroadcastConnection.this.connectingSuccessful();
                                    final Iterator<IConnectionListener> iterator = AbstractBroadcastConnection.this._listeners.iterator();
                                    while (iterator.hasNext()) {
                                        iterator.next().connectionEstablished(AbstractBroadcastConnection.this);
                                    }
                                }
                                
                                @Override
                                void onTimeout() {
                                    AbstractBroadcastConnection.this._tournamentsSocket.disconnect();
                                }
                            };
                            if (authToken != null) {
                                final String uuid = authToken.getUuid();
                                access.000.emit("connect", abstractTimeoutIOAcknowledge, uuid);
                                return;
                            }
                        }
                        catch (MalformedURLException ex) {
                            Logger.getInstance().error(SocketIOTournamentConnection.class.getName(), MalformedURLException.class.getName(), ex);
                            AbstractBroadcastConnection.this.notifyListenerDisconnect();
                            return;
                        }
                        final String uuid = "meinAuthTokenIstOk";
                        continue;
                    }
                }
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
        return this._tournamentsSocket != null && this._tournamentsSocket.isConnected();
    }
    
    @Override
    public void on(final String s, final IOAcknowledge ioAcknowledge, final Object... array) {
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(" Event");
        instance.debug(sb.toString(), s);
        for (int i = 0; i < array.length; ++i) {
            final Object o = array[i];
            final Logger instance2 = Logger.getInstance();
            final String name = this.getClass().getName();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(o);
            instance2.debug(name, sb2.toString());
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
    public void onError(final SocketIOException ex) {
        this.notifyListenerDisconnect();
    }
    
    @Override
    public void onMessage(final String s, final IOAcknowledge ioAcknowledge) {
    }
    
    @Override
    public void onMessage(final JSONObject jsonObject, final IOAcknowledge ioAcknowledge) {
    }
    
    protected void subscribeToRKey(final String s) {
        this.getSocket().emit("subscribe", s);
    }
}
