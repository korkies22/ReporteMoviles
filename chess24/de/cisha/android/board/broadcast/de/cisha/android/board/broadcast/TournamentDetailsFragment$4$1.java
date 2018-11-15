/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.TournamentDetailsFragment;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentConnection;

class TournamentDetailsFragment
implements Runnable {
    TournamentDetailsFragment() {
    }

    @Override
    public void run() {
        4.this.this$0._connectionManager.removeConnection(4.this.this$0._connection, true);
        4.this.this$0._flagReloadTournament = true;
    }
}
