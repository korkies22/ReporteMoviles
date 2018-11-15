/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import java.util.TimerTask;

class DummyTournamentGameConnection
extends TimerTask {
    DummyTournamentGameConnection() {
    }

    @Override
    public void run() {
        DummyTournamentGameConnection.this.notifyListenersConnectionEstablished();
    }
}
