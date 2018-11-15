/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.SearchView;

class SearchView.SearchAutoComplete
implements Runnable {
    SearchView.SearchAutoComplete() {
    }

    @Override
    public void run() {
        SearchAutoComplete.this.showSoftInputIfNecessary();
    }
}
