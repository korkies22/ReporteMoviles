/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.broadcast.standings;

import android.view.View;
import de.cisha.android.board.broadcast.model.TournamentHolder;

class TournamentStandingsMultiKnockoutFragment
implements View.OnClickListener {
    TournamentStandingsMultiKnockoutFragment() {
    }

    public void onClick(View view) {
        TournamentStandingsMultiKnockoutFragment.this.getTournamentHolder().selectPreviousMainRound();
    }
}
