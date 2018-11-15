/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.broadcast.video;

import android.support.v4.app.FragmentActivity;
import android.view.View;

class VideoStreamFragment
implements Runnable {
    VideoStreamFragment() {
    }

    @Override
    public void run() {
        VideoStreamFragment.this.showLoadingView();
        VideoStreamFragment.this.getActivity().findViewById(2131297246).setVisibility(8);
    }
}
