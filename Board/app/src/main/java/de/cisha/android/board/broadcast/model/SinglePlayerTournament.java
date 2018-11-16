// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.jsonparsers.BroadcastTournamentType;
import java.util.List;

public class SinglePlayerTournament extends Tournament
{
    private List<TournamentPlayer> _standings;
    
    public SinglePlayerTournament(final String s, final String s2) {
        super(s, s2, BroadcastTournamentType.SINGLEPLAYER_OPEN);
    }
    
    @Override
    public List<TournamentPlayer> getPlayerStandings() {
        return this._standings;
    }
    
    @Override
    public boolean hasTeams() {
        return false;
    }
    
    @Override
    public boolean isStandingsEnabled() {
        return true;
    }
    
    public void setPlayerStandings(final List<TournamentPlayer> standings) {
        this._standings = standings;
    }
}
