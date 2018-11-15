/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 */
package de.cisha.android.ui.list;

import android.view.View;
import android.widget.AdapterView;

class RefreshingListView
implements AdapterView.OnItemSelectedListener {
    final /* synthetic */ AdapterView.OnItemSelectedListener val$listener;

    RefreshingListView(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.val$listener = onItemSelectedListener;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
        if (n >= RefreshingListView.this.getContentStartIndex()) {
            this.val$listener.onItemSelected(adapterView, view, n - RefreshingListView.this.getContentStartIndex(), l);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        this.val$listener.onNothingSelected(adapterView);
    }
}
