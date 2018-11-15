/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package de.cisha.android.board.broadcast.model;

import android.content.Context;
import android.content.res.Resources;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.broadcast.model.SocketIOTournamentConnection;
import de.cisha.android.board.broadcast.model.SocketIOTournamentGameConnection;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentID;

public class SocketIOTournamentService
implements ITournamentService {
    private String _language;

    public SocketIOTournamentService(Context context) {
        this._language = context.getResources().getString(2131689583);
    }

    @Override
    public ITournamentConnection getTournamentConnection(TournamentID tournamentID, ITournamentConnection.TournamentConnectionListener tournamentConnectionListener, ITournamentConnection.TournamentChangeListener tournamentChangeListener) {
        return new SocketIOTournamentConnection(tournamentID, tournamentConnectionListener, tournamentChangeListener, this._language);
    }

    @Override
    public ITournamentGameConnection getTournamentGameConnection(TournamentGameID tournamentGameID, ITournamentGameConnection.TournamentGameConnectionListener tournamentGameConnectionListener) {
        return new SocketIOTournamentGameConnection(tournamentGameID, tournamentGameConnectionListener);
    }
}
