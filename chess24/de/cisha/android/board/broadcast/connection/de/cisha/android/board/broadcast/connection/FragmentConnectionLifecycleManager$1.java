/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.connection;

import de.cisha.android.board.broadcast.connection.IConnection;
import java.util.TimerTask;

class FragmentConnectionLifecycleManager
extends TimerTask {
    final /* synthetic */ IConnection val$connection;

    FragmentConnectionLifecycleManager(IConnection iConnection) {
        this.val$connection = iConnection;
    }

    @Override
    public void run() {
        if (FragmentConnectionLifecycleManager.this._shouldBeAlive) {
            this.val$connection.connect();
        }
    }
}
