/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.android.board.playzone.remote.PlayzoneConnectionListener;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import java.util.Timer;
import java.util.TimerTask;

class RemoteGameAdapter
implements Runnable {
    final /* synthetic */ boolean val$connected;

    RemoteGameAdapter(boolean bl) {
        this.val$connected = bl;
    }

    @Override
    public void run() {
        RemoteGameAdapter.this._reconnectStateWaitingForResponse = false;
        if (RemoteGameAdapter.this._myConnectionState != this.val$connected) {
            RemoteGameAdapter.this._myConnectionState = this.val$connected;
            RemoteGameAdapter.this._timoutForReconnect = System.currentTimeMillis() + (long)RemoteGameAdapter.this.getConnectionTimeout(true);
            if (RemoteGameAdapter.this._connectionListener != null && (RemoteGameAdapter.this._gameHolder.isGameActive() || this.val$connected)) {
                RemoteGameAdapter.this._connectionListener.onMyConnectionStateChanged(this.val$connected);
            }
        }
        if (!this.val$connected && RemoteGameAdapter.this._gameHolder.isGameActive()) {
            if (System.currentTimeMillis() + 500L < RemoteGameAdapter.this._timoutForReconnect) {
                RemoteGameAdapter.this._timer.schedule((TimerTask)new RemoteGameAdapter.ReconnectTask(RemoteGameAdapter.this, null), 200L);
                RemoteGameAdapter.this._reconnectStateWaitingForResponse = true;
            }
            if (RemoteGameAdapter.this._timeoutTask == null) {
                RemoteGameAdapter.this._timeoutTask = new RemoteGameAdapter.TimeoutTask(RemoteGameAdapter.this, null);
                RemoteGameAdapter.this._timer.schedule((TimerTask)RemoteGameAdapter.this._timeoutTask, RemoteGameAdapter.this.getConnectionTimeout(true));
            }
        }
        if (this.val$connected && RemoteGameAdapter.this._timeoutTask != null) {
            RemoteGameAdapter.this._timeoutTask.cancel();
            RemoteGameAdapter.this._timeoutTask = null;
        }
    }
}
