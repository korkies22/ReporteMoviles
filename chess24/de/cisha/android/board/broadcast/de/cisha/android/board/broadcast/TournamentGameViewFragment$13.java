/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;

class TournamentGameViewFragment
implements Runnable {
    TournamentGameViewFragment() {
    }

    @Override
    public void run() {
        TournamentGameViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this._connectionManager.removeConnection(TournamentGameViewFragment.this._gameConnection, true);
                TournamentGameViewFragment.this._flagReloadGame = true;
            }
        });
    }

}
