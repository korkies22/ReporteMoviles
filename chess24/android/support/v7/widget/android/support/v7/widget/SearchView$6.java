/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.SearchableInfo
 *  android.text.Editable
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnKeyListener
 */
package android.support.v7.widget;

import android.app.SearchableInfo;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

class SearchView
implements View.OnKeyListener {
    SearchView() {
    }

    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        if (SearchView.this.mSearchable == null) {
            return false;
        }
        if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
            return SearchView.this.onSuggestionsKey(view, n, keyEvent);
        }
        if (!SearchView.this.mSearchSrcTextView.isEmpty() && keyEvent.hasNoModifiers() && keyEvent.getAction() == 1 && n == 66) {
            view.cancelLongPress();
            SearchView.this.launchQuerySearch(0, null, SearchView.this.mSearchSrcTextView.getText().toString());
            return true;
        }
        return false;
    }
}
