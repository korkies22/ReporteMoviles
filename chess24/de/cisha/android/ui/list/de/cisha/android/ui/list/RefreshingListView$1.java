/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package de.cisha.android.ui.list;

import android.view.View;
import android.widget.AdapterView;

class RefreshingListView
implements AdapterView.OnItemClickListener {
    final /* synthetic */ AdapterView.OnItemClickListener val$listener;

    RefreshingListView(AdapterView.OnItemClickListener onItemClickListener) {
        this.val$listener = onItemClickListener;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        if (n >= RefreshingListView.this.getContentStartIndex()) {
            this.val$listener.onItemClick(adapterView, view, n - RefreshingListView.this.getContentStartIndex(), l);
        }
    }
}
