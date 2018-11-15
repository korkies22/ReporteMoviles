/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 */
package android.support.v7.widget;

import android.view.View;

class SearchView
implements View.OnFocusChangeListener {
    SearchView() {
    }

    public void onFocusChange(View view, boolean bl) {
        if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
            SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange((View)SearchView.this, bl);
        }
    }
}
