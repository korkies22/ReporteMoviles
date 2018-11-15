/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import java.util.TimerTask;

class RemoteGameAdapter
extends TimerTask {
    RemoteGameAdapter() {
    }

    @Override
    public void run() {
        if (7.this.this$0._connection != null) {
            7.this.this$0._connection.disconnect();
            7.this.this$0._delegate.onOpponentDeclinedRematch();
        }
    }
}
