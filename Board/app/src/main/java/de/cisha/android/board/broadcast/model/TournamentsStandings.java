// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public abstract class TournamentsStandings
{
    private int _draws;
    private int _losses;
    private int _wins;
    
    public TournamentsStandings(final int wins, final int draws, final int losses) {
        this._wins = wins;
        this._draws = draws;
        this._losses = losses;
    }
    
    public int getDraws() {
        return this._draws;
    }
    
    public int getLosses() {
        return this._losses;
    }
    
    public int getWins() {
        return this._wins;
    }
}
