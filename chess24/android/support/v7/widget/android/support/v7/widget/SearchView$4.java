/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 */
package android.support.v7.widget;

import android.view.View;

class SearchView
implements View.OnLayoutChangeListener {
    SearchView() {
    }

    public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        SearchView.this.adjustDropDownSizeAndPosition();
    }
}
