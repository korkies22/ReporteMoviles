/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package de.cisha.android.board.broadcast.statistics;

import android.view.View;
import android.widget.AdapterView;

class TeamPlayersListFragment
implements AdapterView.OnItemClickListener {
    TeamPlayersListFragment() {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        TeamPlayersListFragment.this.selectPlayerAtPosition(n);
    }
}
