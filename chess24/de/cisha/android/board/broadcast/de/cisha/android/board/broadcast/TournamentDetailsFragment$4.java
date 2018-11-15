/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentConnection;

class TournamentDetailsFragment
implements Runnable {
    TournamentDetailsFragment() {
    }

    @Override
    public void run() {
        TournamentDetailsFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentDetailsFragment.this._connectionManager.removeConnection(TournamentDetailsFragment.this._connection, true);
                TournamentDetailsFragment.this._flagReloadTournament = true;
            }
        });
    }

}
