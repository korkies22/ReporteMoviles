/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.EditText
 */
package de.cisha.android.board.video;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

class VideoSeriesListFragment
implements View.OnClickListener {
    VideoSeriesListFragment() {
    }

    public void onClick(View view) {
        if (VideoSeriesListFragment.this._editTextOverlay.isClickable()) {
            VideoSeriesListFragment.this._searchEditTextView.setEnabled(true);
            VideoSeriesListFragment.this._editTextOverlay.setClickable(false);
            VideoSeriesListFragment.this._searchEditTextView.requestFocus();
            VideoSeriesListFragment.this._searchEditTextView.postDelayed(new Runnable(){

                @Override
                public void run() {
                    ((InputMethodManager)VideoSeriesListFragment.this.getActivity().getSystemService("input_method")).showSoftInput((View)VideoSeriesListFragment.this._searchEditTextView, 0);
                }
            }, 50L);
        }
    }

}
