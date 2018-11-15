/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentID;

public interface ITournamentService {
    public ITournamentConnection getTournamentConnection(TournamentID var1, ITournamentConnection.TournamentConnectionListener var2, ITournamentConnection.TournamentChangeListener var3);

    public ITournamentGameConnection getTournamentGameConnection(TournamentGameID var1, ITournamentGameConnection.TournamentGameConnectionListener var2);
}
