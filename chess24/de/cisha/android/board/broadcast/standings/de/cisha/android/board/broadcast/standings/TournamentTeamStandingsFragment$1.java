/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings;

import de.cisha.android.board.broadcast.standings.TournamentTeamStandingsFragment;

class TournamentTeamStandingsFragment
implements Runnable {
    TournamentTeamStandingsFragment() {
    }

    @Override
    public void run() {
        TournamentTeamStandingsFragment.this._standingsListAdapter.notifyDataSetChanged();
    }
}
