/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;

class VideoSeriesOverviewFragment
implements Runnable {
    VideoSeriesOverviewFragment() {
    }

    @Override
    public void run() {
        if (VideoSeriesOverviewFragment.this._localVideoService.isVideoSeriesOfflineModeEnabled(VideoSeriesOverviewFragment.this._seriesId)) {
            VideoSeriesOverviewFragment.this.setLocalVideoSeries(VideoSeriesOverviewFragment.this._localVideoService.getLocalVideoSeries(VideoSeriesOverviewFragment.this._seriesId));
            return;
        }
        VideoSeriesOverviewFragment.this.loadVideoSeries();
    }
}
