// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public interface TournamentGamesObserver
{
    void allGameInfosChanged();
    
    void gameInfoChanged(final TournamentGameInfo p0);
    
    void onSelectRound(final TournamentRoundInfo p0);
    
    void registeredForChangesOn(final Tournament p0, final TournamentRoundInfo p1, final boolean p2);
}
