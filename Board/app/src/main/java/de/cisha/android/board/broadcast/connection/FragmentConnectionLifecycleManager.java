// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.connection;

import android.os.Bundle;
import java.util.TimerTask;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.List;
import android.support.v4.app.Fragment;

public class FragmentConnectionLifecycleManager extends Fragment implements ConnectionLifecycleManager, IConnectionListener
{
    private List<IConnection> _connections;
    private ActivityLifecycle _lifecycle;
    private int _reconnectDelay;
    private boolean _shouldBeAlive;
    private Timer _timer;
    
    public FragmentConnectionLifecycleManager() {
        this._reconnectDelay = 10000;
        this._connections = new LinkedList<IConnection>();
    }
    
    private void diconnectConnections() {
        this._shouldBeAlive = false;
        this._timer.cancel();
        for (final IConnection connection : this._connections) {
            if (connection.isConnected()) {
                connection.close();
            }
        }
    }
    
    private void establishConnections() {
        this._timer = new Timer();
        this._shouldBeAlive = true;
        for (final IConnection connection : this._connections) {
            if (!connection.isConnected()) {
                connection.connect();
            }
        }
    }
    
    private void tryReconnect(final IConnection connection) {
        if (!this._shouldBeAlive || !this._connections.contains(connection)) {
            return;
        }
        try {
            this._timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (FragmentConnectionLifecycleManager.this._shouldBeAlive) {
                        connection.connect();
                    }
                }
            }, this._reconnectDelay);
        }
        catch (IllegalStateException ex) {}
    }
    
    @Override
    public void addConnection(final IConnection connection) {
        if (!this._connections.contains(connection)) {
            this._connections.add(connection);
            connection.addConnectionListener((IConnection.IConnectionListener)this);
        }
        if (this._shouldBeAlive && !connection.isConnected()) {
            connection.connect();
        }
    }
    
    @Override
    public void connectionClosed(final IConnection connection) {
        this.tryReconnect(connection);
    }
    
    @Override
    public void connectionEstablished(final IConnection connection) {
    }
    
    @Override
    public void connectionFailed(final IConnection connection) {
        this.tryReconnect(connection);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setRetainInstance(true);
        if (this._lifecycle == ActivityLifecycle.CREATE_DESTROY) {
            this.establishConnections();
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this._lifecycle == ActivityLifecycle.CREATE_DESTROY) {
            this.diconnectConnections();
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (this._lifecycle == ActivityLifecycle.RESUME_PAUSE) {
            this.diconnectConnections();
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (this._lifecycle == ActivityLifecycle.RESUME_PAUSE) {
            this.establishConnections();
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if (this._lifecycle == ActivityLifecycle.START_STOP) {
            this.establishConnections();
        }
    }
    
    @Override
    public void onStop() {
        super.onStop();
        if (this._lifecycle == ActivityLifecycle.START_STOP) {
            this.diconnectConnections();
        }
    }
    
    @Override
    public void removeConnection(final IConnection connection, final boolean b) {
        this._connections.remove(connection);
        if (b) {
            connection.close();
        }
    }
    
    public void setConnectionLifecycle(final ActivityLifecycle lifecycle) {
        this._lifecycle = lifecycle;
    }
    
    public void setReconnectDelay(final int reconnectDelay) {
        this._reconnectDelay = reconnectDelay;
    }
}
