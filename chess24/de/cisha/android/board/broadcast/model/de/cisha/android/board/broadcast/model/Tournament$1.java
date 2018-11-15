/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.Comparator;

class Tournament
implements Comparator<TournamentGameInfo> {
    Tournament() {
    }

    @Override
    public int compare(TournamentGameInfo tournamentGameInfo, TournamentGameInfo tournamentGameInfo2) {
        return tournamentGameInfo.getMatchNumber() - tournamentGameInfo2.getMatchNumber();
    }
}
