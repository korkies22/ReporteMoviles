/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;

public static interface ITournamentConnection.TournamentChangeListener {
    public void tournamentAllGamesChanged();

    public void tournamentGameChanged(TournamentGameInfo var1);
}
