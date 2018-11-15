/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.TournamentGameViewFragment;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;

class TournamentGameViewFragment
implements Runnable {
    TournamentGameViewFragment() {
    }

    @Override
    public void run() {
        13.this.this$0._connectionManager.removeConnection(13.this.this$0._gameConnection, true);
        13.this.this$0._flagReloadGame = true;
    }
}
