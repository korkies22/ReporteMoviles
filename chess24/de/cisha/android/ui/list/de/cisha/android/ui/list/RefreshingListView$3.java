/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemLongClickListener
 */
package de.cisha.android.ui.list;

import android.view.View;
import android.widget.AdapterView;

class RefreshingListView
implements AdapterView.OnItemLongClickListener {
    final /* synthetic */ AdapterView.OnItemLongClickListener val$listener;

    RefreshingListView(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.val$listener = onItemLongClickListener;
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int n, long l) {
        if (n >= RefreshingListView.this.getContentStartIndex()) {
            return this.val$listener.onItemLongClick(adapterView, view, n - RefreshingListView.this.getContentStartIndex(), l);
        }
        return false;
    }
}
