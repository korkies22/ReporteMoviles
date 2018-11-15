/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnLongClickListener
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import de.cisha.android.board.broadcast.model.TournamentHolder;

class AbstractTournamentGamesListFragment
implements View.OnLongClickListener {
    AbstractTournamentGamesListFragment() {
    }

    public boolean onLongClick(View view) {
        AbstractTournamentGamesListFragment.this.getTournamentHolder().selectLastRound();
        return true;
    }
}
