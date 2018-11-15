/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import java.util.TimerTask;

class DummyTournamentGameConnection
extends TimerTask {
    DummyTournamentGameConnection() {
    }

    @Override
    public void run() {
        AbstractMoveContainer abstractMoveContainer = DummyTournamentGameConnection.this._game.copy();
        DummyTournamentGameConnection.this._myListener.tournamentGameLoaded((Game)abstractMoveContainer);
    }
}
