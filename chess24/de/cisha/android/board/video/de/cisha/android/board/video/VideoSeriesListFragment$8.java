/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 */
package de.cisha.android.board.video;

import android.view.View;
import android.widget.EditText;

class VideoSeriesListFragment
implements View.OnClickListener {
    VideoSeriesListFragment() {
    }

    public void onClick(View view) {
        boolean bl = VideoSeriesListFragment.this._currentSearchString != null;
        VideoSeriesListFragment.this.setCurrentSearchString(null);
        VideoSeriesListFragment.this._searchEditTextView.setText((CharSequence)"");
        if (bl) {
            VideoSeriesListFragment.this.loadSeriesList();
        }
    }
}
