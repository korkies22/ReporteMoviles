// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public class MainKnockoutRoundInfo extends TournamentRoundInfo
{
    private int _roundNumber;
    
    public MainKnockoutRoundInfo(final int roundNumber) {
        this._roundNumber = roundNumber;
    }
    
    @Override
    public int compareTo(final TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo instanceof MainKnockoutRoundInfo) {
            return this._roundNumber - ((MainKnockoutRoundInfo)tournamentRoundInfo)._roundNumber;
        }
        return tournamentRoundInfo.getClass().getName().compareTo(this.getClass().getName());
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof MainKnockoutRoundInfo && this._roundNumber == ((MainKnockoutRoundInfo)o)._roundNumber);
    }
    
    @Override
    public String getRoundString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this._roundNumber);
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return this._roundNumber;
    }
}
