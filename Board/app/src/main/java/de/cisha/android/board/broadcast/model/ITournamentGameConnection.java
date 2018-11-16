// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.GameStatus;
import de.cisha.android.board.broadcast.connection.IConnection;

public interface ITournamentGameConnection extends IConnection
{
    void loadGame();
    
    void subscribeToGame();
    
    public interface TournamentGameConnectionListener extends IConnectionListener
    {
        void gameStatusChanged(final GameStatus p0);
        
        void moveDeleted(final int p0);
        
        void moveTimeChanged(final int p0, final int p1);
        
        void newMove(final int p0, final SEP p1, final int p2, final int p3);
        
        void newMove(final int p0, final String p1, final int p2, final int p3);
        
        void tournamentGameLoaded(final Game p0);
        
        void tournamentGameLoadingFailed(final APIStatusCode p0);
    }
}
