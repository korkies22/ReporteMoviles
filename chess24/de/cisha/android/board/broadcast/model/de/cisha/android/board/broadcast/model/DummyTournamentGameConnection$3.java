/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.position.Position;
import java.util.List;
import java.util.TimerTask;

class DummyTournamentGameConnection
extends TimerTask {
    DummyTournamentGameConnection() {
    }

    @Override
    public void run() {
        Object object = DummyTournamentGameConnection.this._game.getLastMoveinMainLine() != null ? DummyTournamentGameConnection.this._game.getLastMoveinMainLine().getResultingPosition() : DummyTournamentGameConnection.this._game.getStartingPosition();
        if ((object = object.getAllMoves()).size() > 0) {
            DummyTournamentGameConnection.this.notifyListenersNewMove((Move)object.get(0));
            return;
        }
        DummyTournamentGameConnection.this.notifyListenersGameEnd();
        DummyTournamentGameConnection.this.close();
    }
}
