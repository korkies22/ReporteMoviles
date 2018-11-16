// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import android.content.Context;

public class SocketIOTournamentService implements ITournamentService
{
    private String _language;
    
    public SocketIOTournamentService(final Context context) {
        this._language = context.getResources().getString(2131689583);
    }
    
    @Override
    public ITournamentConnection getTournamentConnection(final TournamentID tournamentID, final ITournamentConnection.TournamentConnectionListener tournamentConnectionListener, final ITournamentConnection.TournamentChangeListener tournamentChangeListener) {
        return new SocketIOTournamentConnection(tournamentID, tournamentConnectionListener, tournamentChangeListener, this._language);
    }
    
    @Override
    public ITournamentGameConnection getTournamentGameConnection(final TournamentGameID tournamentGameID, final ITournamentGameConnection.TournamentGameConnectionListener tournamentGameConnectionListener) {
        return new SocketIOTournamentGameConnection(tournamentGameID, tournamentGameConnectionListener);
    }
}
