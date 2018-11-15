/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 */
package de.cisha.android.board.broadcast;

import android.widget.AbsListView;

class AbstractTournamentGamesListFragment
implements AbsListView.OnScrollListener {
    AbstractTournamentGamesListFragment() {
    }

    public void onScroll(AbsListView absListView, int n, int n2, int n3) {
    }

    public void onScrollStateChanged(AbsListView object, int n) {
        object = AbstractTournamentGamesListFragment.this;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        ((de.cisha.android.board.broadcast.AbstractTournamentGamesListFragment)object)._scrolling = bl;
    }
}
