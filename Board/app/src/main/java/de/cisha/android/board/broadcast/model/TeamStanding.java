// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public class TeamStanding extends TournamentsStandings
{
    private float _boardPoints;
    private float _teamPoints;
    
    public TeamStanding(final float teamPoints, final float boardPoints, final int n, final int n2, final int n3) {
        super(n, n2, n3);
        this._teamPoints = teamPoints;
        this._boardPoints = boardPoints;
    }
    
    public float getBoardPoints() {
        return this._boardPoints;
    }
    
    public float getTeamPoints() {
        return this._teamPoints;
    }
}
