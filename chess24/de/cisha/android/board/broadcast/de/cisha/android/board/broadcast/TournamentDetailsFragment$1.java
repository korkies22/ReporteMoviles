/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentHolder;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;

class TournamentDetailsFragment
implements Runnable {
    final /* synthetic */ Tournament val$tournament;

    TournamentDetailsFragment(Tournament tournament) {
        this.val$tournament = tournament;
    }

    @Override
    public void run() {
        boolean bl = TournamentDetailsFragment.this._tournamentHolder.hasTournament();
        TournamentDetailsFragment.this._tournamentHolder.setTournament(this.val$tournament);
        TournamentDetailsFragment.this.showMenuItemsForTournament(this.val$tournament);
        if (bl ^ true) {
            View view = TournamentDetailsFragment.this._menBar.findViewById(2131296389);
            view.setSelected(true);
            TournamentDetailsFragment.this.menuItemClicked((MenuBarItem)view);
        }
    }
}
