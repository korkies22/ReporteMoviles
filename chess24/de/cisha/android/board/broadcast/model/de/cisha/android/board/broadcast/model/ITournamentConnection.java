/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

public interface ITournamentConnection
extends IConnection {
    public void loadTournament();

    public void subscribeToTournament();

    public static interface TournamentChangeListener {
        public void tournamentAllGamesChanged();

        public void tournamentGameChanged(TournamentGameInfo var1);
    }

    public static interface TournamentConnectionListener
    extends IConnection.IConnectionListener {
        public void tournamentLoaded(Tournament var1);

        public void tournamentLoadingFailed(APIStatusCode var1);
    }

}
