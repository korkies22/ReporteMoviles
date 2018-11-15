/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.statistics;

import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.statistics.SinglePlayerStatisticFragment;
import de.cisha.android.board.broadcast.statistics.view.BroadcastPlayerView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SinglePlayerStatisticFragment
implements Runnable {
    final /* synthetic */ TournamentPlayer val$player;

    SinglePlayerStatisticFragment(TournamentPlayer tournamentPlayer) {
        this.val$player = tournamentPlayer;
    }

    @Override
    public void run() {
        if (SinglePlayerStatisticFragment.this.getTournamentHolder().hasTournament()) {
            SinglePlayerStatisticFragment.this._player = this.val$player;
            Tournament tournament = SinglePlayerStatisticFragment.this.getTournamentHolder().getTournament();
            SinglePlayerStatisticFragment.this._playerHeaderView.setDataForPlayer(this.val$player, tournament);
            SinglePlayerStatisticFragment.this._gamesForPlayer = tournament.getGamesForPlayer(this.val$player);
            Collections.sort(SinglePlayerStatisticFragment.this._gamesForPlayer, new Comparator<TournamentGameInfo>(){

                @Override
                public int compare(TournamentGameInfo tournamentGameInfo, TournamentGameInfo tournamentGameInfo2) {
                    if (tournamentGameInfo != null && tournamentGameInfo2 != null) {
                        return tournamentGameInfo.getRoundNumber() - tournamentGameInfo2.getRoundNumber();
                    }
                    return 0;
                }
            });
            SinglePlayerStatisticFragment.this._opponentListAdapter.notifyDataSetChanged();
        }
    }

}
