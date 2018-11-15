/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 */
package android.support.v7.widget;

import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SuggestionsAdapter;

class SearchView
implements Runnable {
    SearchView() {
    }

    @Override
    public void run() {
        if (SearchView.this.mSuggestionsAdapter != null && SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
            SearchView.this.mSuggestionsAdapter.changeCursor(null);
        }
    }
}
