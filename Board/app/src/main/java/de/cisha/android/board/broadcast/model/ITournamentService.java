// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

public interface ITournamentService
{
    ITournamentConnection getTournamentConnection(final TournamentID p0, final ITournamentConnection.TournamentConnectionListener p1, final ITournamentConnection.TournamentChangeListener p2);
    
    ITournamentGameConnection getTournamentGameConnection(final TournamentGameID p0, final ITournamentGameConnection.TournamentGameConnectionListener p1);
}
