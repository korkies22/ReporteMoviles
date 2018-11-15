/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast;

import de.cisha.android.board.broadcast.TournamentGamesListFragment;

class TournamentGamesListFragment
implements Runnable {
    TournamentGamesListFragment() {
    }

    @Override
    public void run() {
        TournamentGamesListFragment.this._adapter.notifyDataSetChanged();
    }
}
