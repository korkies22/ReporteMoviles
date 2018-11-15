/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.view.KeyEvent
 *  android.view.View
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package de.cisha.android.board.video;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

class VideoSeriesListFragment
implements TextView.OnEditorActionListener {
    VideoSeriesListFragment() {
    }

    public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
        if (n == 3) {
            VideoSeriesListFragment.this.setCurrentSearchString(VideoSeriesListFragment.this._searchEditTextView.getText().toString());
            VideoSeriesListFragment.this.loadSeriesList();
            VideoSeriesListFragment.this._searchEditTextView.setEnabled(false);
            VideoSeriesListFragment.this._editTextOverlay.setClickable(true);
        }
        return false;
    }
}
