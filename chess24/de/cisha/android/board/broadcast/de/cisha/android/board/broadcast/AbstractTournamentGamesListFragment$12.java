/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;

class AbstractTournamentGamesListFragment
implements Runnable {
    final /* synthetic */ TournamentRoundInfo val$tournamentRoundInfo;

    AbstractTournamentGamesListFragment(TournamentRoundInfo tournamentRoundInfo) {
        this.val$tournamentRoundInfo = tournamentRoundInfo;
    }

    @Override
    public void run() {
        AbstractTournamentGamesListFragment.this._roundDescription.setText((CharSequence)this.val$tournamentRoundInfo.getRoundString());
        TournamentRoundInfo tournamentRoundInfo = this.val$tournamentRoundInfo.getMainRound();
        TournamentRoundInfo tournamentRoundInfo2 = this.val$tournamentRoundInfo;
        int n = 0;
        int n2 = tournamentRoundInfo != tournamentRoundInfo2 ? 1 : 0;
        tournamentRoundInfo = AbstractTournamentGamesListFragment.this._previousFastButton;
        int n3 = 8;
        int n4 = n2 != 0 ? 0 : 8;
        tournamentRoundInfo.setVisibility(n4);
        tournamentRoundInfo = AbstractTournamentGamesListFragment.this._nextFastButton;
        n4 = n3;
        if (n2 != 0) {
            n4 = 0;
        }
        tournamentRoundInfo.setVisibility(n4);
        tournamentRoundInfo = AbstractTournamentGamesListFragment.this._previousButton;
        n2 = AbstractTournamentGamesListFragment.this.getTournamentHolder().hasPreviousRound() ? 0 : 4;
        tournamentRoundInfo.setVisibility(n2);
        tournamentRoundInfo = AbstractTournamentGamesListFragment.this._nextButton;
        n2 = AbstractTournamentGamesListFragment.this.getTournamentHolder().hasNextRound() ? n : 4;
        tournamentRoundInfo.setVisibility(n2);
    }
}
