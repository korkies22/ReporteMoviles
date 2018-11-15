/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package android.support.v7.widget;

import android.view.View;
import android.widget.AdapterView;

class SearchView
implements AdapterView.OnItemClickListener {
    SearchView() {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        SearchView.this.onItemClicked(n, 0, null);
    }
}
