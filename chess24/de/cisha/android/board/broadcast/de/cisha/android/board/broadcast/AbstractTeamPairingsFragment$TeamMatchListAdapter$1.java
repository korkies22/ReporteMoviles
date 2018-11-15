/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import de.cisha.android.board.broadcast.AbstractTeamPairingsFragment;
import de.cisha.android.board.broadcast.model.TournamentTeamPairing;

class AbstractTeamPairingsFragment.TeamMatchListAdapter
implements View.OnClickListener {
    final /* synthetic */ TournamentTeamPairing val$pairing;

    AbstractTeamPairingsFragment.TeamMatchListAdapter(TournamentTeamPairing tournamentTeamPairing) {
        this.val$pairing = tournamentTeamPairing;
    }

    public void onClick(View view) {
        TeamMatchListAdapter.this.toggleOpenCloseSection(this.val$pairing);
    }
}
