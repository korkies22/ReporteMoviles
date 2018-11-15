/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;

class RemoteGameAdapter
implements Runnable {
    RemoteGameAdapter() {
    }

    @Override
    public void run() {
        RemoteGameAdapter.this._delegate.onStartGameFailed(0);
        RemoteGameAdapter.this._connection.disconnect();
    }
}
