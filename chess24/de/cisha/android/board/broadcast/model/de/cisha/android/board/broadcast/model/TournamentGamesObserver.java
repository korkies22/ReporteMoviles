/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

public interface TournamentGamesObserver {
    public void allGameInfosChanged();

    public void gameInfoChanged(TournamentGameInfo var1);

    public void onSelectRound(TournamentRoundInfo var1);

    public void registeredForChangesOn(Tournament var1, TournamentRoundInfo var2, boolean var3);
}
