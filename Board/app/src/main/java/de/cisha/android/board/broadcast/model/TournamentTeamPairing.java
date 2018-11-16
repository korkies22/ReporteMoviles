// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.chess.model.GameResult;
import java.util.Iterator;
import java.util.List;

public class TournamentTeamPairing
{
    private List<TournamentGameInfo> _games;
    private TournamentTeam _team1;
    private TournamentTeam _team2;
    
    public TournamentTeamPairing(final TournamentTeam team1, final TournamentTeam team2, final List<TournamentGameInfo> games) {
        this._team1 = team1;
        this._team2 = team2;
        this._games = games;
    }
    
    private float getPointsTeam(final boolean b) {
        final Iterator<TournamentGameInfo> iterator = this._games.iterator();
        float n = 0.0f;
        while (iterator.hasNext()) {
            final TournamentGameInfo tournamentGameInfo = iterator.next();
            final GameResult result = tournamentGameInfo.getStatus().getResult();
            TournamentPlayer tournamentPlayer;
            if (b) {
                tournamentPlayer = tournamentGameInfo.getPlayerLeft();
            }
            else {
                tournamentPlayer = tournamentGameInfo.getPlayerRight();
            }
            float n2;
            if (tournamentPlayer == tournamentGameInfo.getPlayerWhite()) {
                n2 = result.getScoreWhite();
            }
            else {
                n2 = result.getScoreBlack();
            }
            n += n2;
        }
        return n;
    }
    
    public List<TournamentGameInfo> getGames() {
        return this._games;
    }
    
    public float getPointsTeam1() {
        return this.getPointsTeam(true);
    }
    
    public float getPointsTeam2() {
        return this.getPointsTeam(false);
    }
    
    public TournamentTeam getTeam1() {
        return this._team1;
    }
    
    public TournamentTeam getTeam2() {
        return this._team2;
    }
    
    public boolean isOngoing() {
        final Iterator<TournamentGameInfo> iterator = this._games.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isOngoing()) {
                return true;
            }
        }
        return false;
    }
}
