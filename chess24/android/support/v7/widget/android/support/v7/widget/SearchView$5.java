/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 */
package android.support.v7.widget;

import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;

class SearchView
implements View.OnClickListener {
    SearchView() {
    }

    public void onClick(View view) {
        if (view == SearchView.this.mSearchButton) {
            SearchView.this.onSearchClicked();
            return;
        }
        if (view == SearchView.this.mCloseButton) {
            SearchView.this.onCloseClicked();
            return;
        }
        if (view == SearchView.this.mGoButton) {
            SearchView.this.onSubmitQuery();
            return;
        }
        if (view == SearchView.this.mVoiceButton) {
            SearchView.this.onVoiceClicked();
            return;
        }
        if (view == SearchView.this.mSearchSrcTextView) {
            SearchView.this.forceSuggestionQuery();
        }
    }
}
