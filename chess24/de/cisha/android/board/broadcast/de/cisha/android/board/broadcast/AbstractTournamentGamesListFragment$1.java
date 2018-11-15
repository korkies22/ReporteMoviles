/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package de.cisha.android.board.broadcast;

import android.view.View;
import android.widget.AdapterView;

class AbstractTournamentGamesListFragment
implements AdapterView.OnItemClickListener {
    AbstractTournamentGamesListFragment() {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        AbstractTournamentGamesListFragment.this.listItemClicked(n);
    }
}
