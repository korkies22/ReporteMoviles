// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public class PlayerStanding extends TournamentsStandings
{
    private float _points;
    
    public PlayerStanding(final float points, final int n, final int n2, final int n3) {
        super(n, n2, n3);
        this._points = points;
    }
    
    public float getPoints() {
        return this._points;
    }
}
