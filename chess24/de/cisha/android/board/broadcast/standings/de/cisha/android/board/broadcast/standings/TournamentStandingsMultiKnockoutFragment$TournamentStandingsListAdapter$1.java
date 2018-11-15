/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.broadcast.standings;

import android.view.View;
import de.cisha.android.board.broadcast.standings.TournamentStandingsMultiKnockoutFragment;
import de.cisha.android.board.broadcast.standings.view.TournamentMatchInfoView;

class TournamentStandingsMultiKnockoutFragment.TournamentStandingsListAdapter
implements View.OnClickListener {
    final /* synthetic */ int val$position;
    final /* synthetic */ TournamentMatchInfoView val$thisView;

    TournamentStandingsMultiKnockoutFragment.TournamentStandingsListAdapter(int n, TournamentMatchInfoView tournamentMatchInfoView) {
        this.val$position = n;
        this.val$thisView = tournamentMatchInfoView;
    }

    public void onClick(View view) {
        TournamentStandingsMultiKnockoutFragment.access$600((TournamentStandingsMultiKnockoutFragment)TournamentStandingsListAdapter.this.this$0)[this.val$position] = TournamentStandingsListAdapter.this.this$0._gamesListViewShownForMatch[this.val$position] ^ true;
        this.val$thisView.setGamesViewEnabled(TournamentStandingsListAdapter.this.this$0._gamesListViewShownForMatch[this.val$position]);
    }
}
