/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.video.model.VideoSeries;

class VideoSeriesOverviewFragment
implements Runnable {
    VideoSeriesOverviewFragment() {
    }

    @Override
    public void run() {
        VideoSeriesOverviewFragment.this.hideDialogWaiting();
        if (VideoSeriesOverviewFragment.this._currentVideoSeries != null) {
            VideoSeriesOverviewFragment.this.videoLoaded(VideoSeriesOverviewFragment.this._currentVideoSeries);
        }
    }
}
