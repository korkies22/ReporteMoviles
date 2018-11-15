/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemLongClickListener
 */
package de.cisha.android.board.database;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment;
import de.cisha.android.ui.patterns.dialogs.RookieMoreDialogFragment;
import de.cisha.chess.model.GameInfo;
import java.util.List;

class AbstractDatabaseListFragment
implements AdapterView.OnItemLongClickListener {
    AbstractDatabaseListFragment() {
    }

    public boolean onItemLongClick(AdapterView<?> object, View object2, int n, long l) {
        if (n >= AbstractDatabaseListFragment.this._numberOfItemsToShow) {
            AbstractDatabaseListFragment.this.showConversionDialog();
            return true;
        }
        object = AbstractDatabaseListFragment.this._listAdapter.getItem(n);
        if ((object = AbstractDatabaseListFragment.this.getListOptionsForGameInfo((GameInfo)object)) != null && object.size() > 0) {
            object2 = new RookieMoreDialogFragment();
            object2.setListOptions((List<RookieMoreDialogFragment.ListOption>)object);
            object2.show(AbstractDatabaseListFragment.this.getChildFragmentManager(), de.cisha.android.board.database.AbstractDatabaseListFragment.MOREDIALOG);
            object2.setOnDialogCloseListener(new AbstractDialogFragment.OnDialogCloseListener(){

                @Override
                public void onDialogClosed() {
                    AbstractDatabaseListFragment.this.updateList();
                }
            });
            return true;
        }
        return false;
    }

}
