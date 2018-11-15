/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package de.cisha.android.board.broadcast.standings;

import android.view.View;
import android.widget.AdapterView;

class AbstractStandingsFragment
implements AdapterView.OnItemClickListener {
    AbstractStandingsFragment() {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        AbstractStandingsFragment.this.itemSelected(n);
    }
}
