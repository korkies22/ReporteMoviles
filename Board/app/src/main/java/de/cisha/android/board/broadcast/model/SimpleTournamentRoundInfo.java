// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public class SimpleTournamentRoundInfo extends TournamentRoundInfo
{
    private int _number;
    
    public SimpleTournamentRoundInfo(final int number) {
        this._number = number;
    }
    
    @Override
    public int compareTo(final TournamentRoundInfo tournamentRoundInfo) {
        if (tournamentRoundInfo instanceof SimpleTournamentRoundInfo) {
            return this._number - ((SimpleTournamentRoundInfo)tournamentRoundInfo)._number;
        }
        return tournamentRoundInfo.getClass().getName().compareTo(this.getClass().getName());
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof SimpleTournamentRoundInfo && this._number == ((SimpleTournamentRoundInfo)o)._number);
    }
    
    @Override
    public String getRoundString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this._number);
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return this._number;
    }
}
