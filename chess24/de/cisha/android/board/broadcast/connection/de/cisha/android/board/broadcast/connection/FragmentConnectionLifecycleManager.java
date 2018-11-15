/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package de.cisha.android.board.broadcast.connection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.connection.ActivityLifecycle;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.IConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentConnectionLifecycleManager
extends Fragment
implements ConnectionLifecycleManager,
IConnection.IConnectionListener {
    private List<IConnection> _connections = new LinkedList<IConnection>();
    private ActivityLifecycle _lifecycle;
    private int _reconnectDelay = 10000;
    private boolean _shouldBeAlive;
    private Timer _timer;

    private void diconnectConnections() {
        this._shouldBeAlive = false;
        this._timer.cancel();
        for (IConnection iConnection : this._connections) {
            if (!iConnection.isConnected()) continue;
            iConnection.close();
        }
    }

    private void establishConnections() {
        this._timer = new Timer();
        this._shouldBeAlive = true;
        for (IConnection iConnection : this._connections) {
            if (iConnection.isConnected()) continue;
            iConnection.connect();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void tryReconnect(final IConnection iConnection) {
        if (!this._shouldBeAlive || !this._connections.contains(iConnection)) return;
        try {
            this._timer.schedule(new TimerTask(){

                @Override
                public void run() {
                    if (FragmentConnectionLifecycleManager.this._shouldBeAlive) {
                        iConnection.connect();
                    }
                }
            }, this._reconnectDelay);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            return;
        }
    }

    @Override
    public void addConnection(IConnection iConnection) {
        if (!this._connections.contains(iConnection)) {
            this._connections.add(iConnection);
            iConnection.addConnectionListener(this);
        }
        if (this._shouldBeAlive && !iConnection.isConnected()) {
            iConnection.connect();
        }
    }

    @Override
    public void connectionClosed(IConnection iConnection) {
        this.tryReconnect(iConnection);
    }

    @Override
    public void connectionEstablished(IConnection iConnection) {
    }

    @Override
    public void connectionFailed(IConnection iConnection) {
        this.tryReconnect(iConnection);
    }

    @Override
    public void onCreate(Bundle bundle) {
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
    public void removeConnection(IConnection iConnection, boolean bl) {
        this._connections.remove(iConnection);
        if (bl) {
            iConnection.close();
        }
    }

    public void setConnectionLifecycle(ActivityLifecycle activityLifecycle) {
        this._lifecycle = activityLifecycle;
    }

    public void setReconnectDelay(int n) {
        this._reconnectDelay = n;
    }

}
