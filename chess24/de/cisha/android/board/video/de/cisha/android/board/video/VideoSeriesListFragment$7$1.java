/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.EditText
 */
package de.cisha.android.board.video;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import de.cisha.android.board.video.VideoSeriesListFragment;

class VideoSeriesListFragment
implements Runnable {
    VideoSeriesListFragment() {
    }

    @Override
    public void run() {
        ((InputMethodManager)7.this.this$0.getActivity().getSystemService("input_method")).showSoftInput((View)7.this.this$0._searchEditTextView, 0);
    }
}
