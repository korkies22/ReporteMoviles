/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.statistics;

import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import java.util.Comparator;

class SinglePlayerStatisticFragment
implements Comparator<TournamentGameInfo> {
    SinglePlayerStatisticFragment() {
    }

    @Override
    public int compare(TournamentGameInfo tournamentGameInfo, TournamentGameInfo tournamentGameInfo2) {
        if (tournamentGameInfo != null && tournamentGameInfo2 != null) {
            return tournamentGameInfo.getRoundNumber() - tournamentGameInfo2.getRoundNumber();
        }
        return 0;
    }
}
