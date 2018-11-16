// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;

public class BroadcastGameStatus extends GameStatus
{
    private TournamentState _tournamentState;
    
    public BroadcastGameStatus(final GameResult gameResult, final GameEndReason gameEndReason, final TournamentState tournamentState) {
        super(gameResult, gameEndReason);
        this._tournamentState = tournamentState;
    }
    
    @Override
    public boolean isFinished() {
        return this._tournamentState == TournamentState.FINISHED;
    }
    
    public boolean isOngoing() {
        return this._tournamentState == TournamentState.ONGOING;
    }
    
    public boolean isUpcoming() {
        return this._tournamentState == TournamentState.UPCOMING;
    }
}
