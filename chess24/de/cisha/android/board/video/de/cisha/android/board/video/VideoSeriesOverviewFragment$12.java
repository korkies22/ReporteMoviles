/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.video;

import android.widget.TextView;

class VideoSeriesOverviewFragment
implements Runnable {
    VideoSeriesOverviewFragment() {
    }

    @Override
    public void run() {
        Runnable runnable = VideoSeriesOverviewFragment.this._setProgressTextAction;
        if (runnable != null) {
            VideoSeriesOverviewFragment.this._textViewDownloadProgress.postDelayed(runnable, 500L);
        }
    }
}
