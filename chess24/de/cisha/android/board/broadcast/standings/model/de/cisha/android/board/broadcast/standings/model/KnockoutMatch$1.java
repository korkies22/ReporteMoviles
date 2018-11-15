/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings.model;

import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import java.util.Comparator;

class KnockoutMatch
implements Comparator<TournamentGameInfo> {
    KnockoutMatch() {
    }

    @Override
    public int compare(TournamentGameInfo tournamentGameInfo, TournamentGameInfo tournamentGameInfo2) {
        return tournamentGameInfo.getGameNumber() - tournamentGameInfo2.getGameNumber();
    }
}
