/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import java.util.List;

class VideoSeriesOverviewFragment
implements Runnable {
    final /* synthetic */ List val$localVideoInfos;
    final /* synthetic */ boolean val$seriesObjectAvailable;

    VideoSeriesOverviewFragment(boolean bl, List list) {
        this.val$seriesObjectAvailable = bl;
        this.val$localVideoInfos = list;
    }

    @Override
    public void run() {
        if (this.val$seriesObjectAvailable && VideoSeriesOverviewFragment.this._currentVideoSeries == null) {
            VideoSeries videoSeries = VideoSeriesOverviewFragment.this._localVideoService.getVideoSeries(VideoSeriesOverviewFragment.this._seriesId);
            VideoSeriesOverviewFragment.this._currentVideoSeries = videoSeries;
            if (videoSeries != null) {
                VideoSeriesOverviewFragment.this.videoLoaded(videoSeries);
            }
        }
        VideoSeriesOverviewFragment.this.setLocalVideoInfos(this.val$localVideoInfos);
    }
}
