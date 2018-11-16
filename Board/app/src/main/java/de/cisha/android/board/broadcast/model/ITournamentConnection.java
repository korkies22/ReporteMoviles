// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.broadcast.connection.IConnection;

public interface ITournamentConnection extends IConnection
{
    void loadTournament();
    
    void subscribeToTournament();
    
    public interface TournamentChangeListener
    {
        void tournamentAllGamesChanged();
        
        void tournamentGameChanged(final TournamentGameInfo p0);
    }
    
    public interface TournamentConnectionListener extends IConnectionListener
    {
        void tournamentLoaded(final Tournament p0);
        
        void tournamentLoadingFailed(final APIStatusCode p0);
    }
}
