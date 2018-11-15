/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.DummyTournamentService;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import java.util.TimerTask;

class DummyTournamentService
extends TimerTask {
    DummyTournamentService() {
    }

    @Override
    public void run() {
        1.this.val$listener.connectionEstablished(1.this.this$0._connection);
    }
}
