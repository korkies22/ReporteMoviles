/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 */
package de.cisha.android.board.database;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.cisha.android.board.database.AbstractDatabaseListFragment;
import de.cisha.chess.model.GameInfo;
import java.util.List;

private class AbstractDatabaseListFragment.GameInfoListAdapter
extends BaseAdapter {
    private AbstractDatabaseListFragment.GameInfoListAdapter() {
    }

    public int getCount() {
        return AbstractDatabaseListFragment.this._gameInfos.size();
    }

    public GameInfo getItem(int n) {
        return (GameInfo)AbstractDatabaseListFragment.this._gameInfos.get(n);
    }

    public long getItemId(int n) {
        return 0L;
    }

    public View getView(int n, View object, ViewGroup object2) {
        if (object == null) {
            object = AbstractDatabaseListFragment.this.createListItemView();
        }
        object2 = (GameInfo)AbstractDatabaseListFragment.this._gameInfos.get(n);
        AbstractDatabaseListFragment abstractDatabaseListFragment = AbstractDatabaseListFragment.this;
        boolean bl = n >= AbstractDatabaseListFragment.this._numberOfItemsToShow;
        abstractDatabaseListFragment.updateListItem(object, (GameInfo)object2, bl);
        return object;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (AbstractDatabaseListFragment.this._introView != null) {
            View view = AbstractDatabaseListFragment.this._introView;
            int n = AbstractDatabaseListFragment.this._gameInfos.size() > 0 ? 8 : 0;
            view.setVisibility(n);
        }
        AbstractDatabaseListFragment.this._numberOfItemsToShow = AbstractDatabaseListFragment.this.getMaximumNumbersOfItemsToShow();
    }
}
