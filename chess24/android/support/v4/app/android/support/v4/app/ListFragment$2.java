/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListView
 */
package android.support.v4.app;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

class ListFragment
implements AdapterView.OnItemClickListener {
    ListFragment() {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        ListFragment.this.onListItemClick((ListView)adapterView, view, n, l);
    }
}
