/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentState;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;

public class BroadcastGameStatus
extends GameStatus {
    private TournamentState _tournamentState;

    public BroadcastGameStatus(GameResult gameResult, GameEndReason gameEndReason, TournamentState tournamentState) {
        super(gameResult, gameEndReason);
        this._tournamentState = tournamentState;
    }

    @Override
    public boolean isFinished() {
        if (this._tournamentState == TournamentState.FINISHED) {
            return true;
        }
        return false;
    }

    public boolean isOngoing() {
        if (this._tournamentState == TournamentState.ONGOING) {
            return true;
        }
        return false;
    }

    public boolean isUpcoming() {
        if (this._tournamentState == TournamentState.UPCOMING) {
            return true;
        }
        return false;
    }
}
