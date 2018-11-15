/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 */
package android.support.v7.widget;

import android.view.View;
import android.widget.AdapterView;

class SearchView
implements AdapterView.OnItemSelectedListener {
    SearchView() {
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
        SearchView.this.onItemSelected(n);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
