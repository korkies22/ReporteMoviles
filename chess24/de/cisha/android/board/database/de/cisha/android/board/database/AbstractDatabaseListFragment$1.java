/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package de.cisha.android.board.database;

import android.view.View;
import android.widget.AdapterView;
import de.cisha.chess.model.GameInfo;

class AbstractDatabaseListFragment
implements AdapterView.OnItemClickListener {
    AbstractDatabaseListFragment() {
    }

    public void onItemClick(AdapterView<?> object, View view, int n, long l) {
        if (n < AbstractDatabaseListFragment.this._numberOfItemsToShow) {
            object = AbstractDatabaseListFragment.this._listAdapter.getItem(n);
            AbstractDatabaseListFragment.this.itemClicked((GameInfo)object);
            return;
        }
        AbstractDatabaseListFragment.this.showConversionDialog();
    }
}
