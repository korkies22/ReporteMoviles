/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.standings;

import de.cisha.android.board.broadcast.standings.TournamentPlayerStandingsFragment;

class TournamentPlayerStandingsFragment
implements Runnable {
    TournamentPlayerStandingsFragment() {
    }

    @Override
    public void run() {
        TournamentPlayerStandingsFragment.this._standingsListAdapter.notifyDataSetChanged();
    }
}
