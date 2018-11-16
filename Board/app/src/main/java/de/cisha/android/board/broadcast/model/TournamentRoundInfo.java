// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public abstract class TournamentRoundInfo implements Comparable<TournamentRoundInfo>
{
    public TournamentRoundInfo getMainRound() {
        return this;
    }
    
    public abstract String getRoundString();
}
