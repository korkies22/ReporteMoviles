/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentRoundInfo;
import java.util.List;

class AbstractTournamentGamesListFragment
implements Runnable {
    final /* synthetic */ boolean val$newInstance;
    final /* synthetic */ Tournament val$tournament;

    AbstractTournamentGamesListFragment(boolean bl, Tournament tournament) {
        this.val$newInstance = bl;
        this.val$tournament = tournament;
    }

    @Override
    public void run() {
        if (this.val$newInstance) {
            View view = AbstractTournamentGamesListFragment.this._navigationViewGroup;
            int n = this.val$tournament.getRounds().size() < 2 ? 8 : 0;
            view.setVisibility(n);
        }
    }
}
