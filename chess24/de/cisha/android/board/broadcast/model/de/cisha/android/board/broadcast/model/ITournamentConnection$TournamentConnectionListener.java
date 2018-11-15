/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

public static interface ITournamentConnection.TournamentConnectionListener
extends IConnection.IConnectionListener {
    public void tournamentLoaded(Tournament var1);

    public void tournamentLoadingFailed(APIStatusCode var1);
}
