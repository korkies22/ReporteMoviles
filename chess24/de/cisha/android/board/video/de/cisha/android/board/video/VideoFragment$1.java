/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video;

import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoInfo;

class VideoFragment
implements Runnable {
    final /* synthetic */ String val$seriesIdString;
    final /* synthetic */ String val$videoIdString;

    VideoFragment(String string, String string2) {
        this.val$seriesIdString = string;
        this.val$videoIdString = string2;
    }

    @Override
    public void run() {
        VideoSeriesId videoSeriesId = new VideoSeriesId(this.val$seriesIdString);
        VideoId videoId = new VideoId(this.val$videoIdString);
        ILocalVideoService iLocalVideoService = ServiceProvider.getInstance().getLocalVideoService();
        if (iLocalVideoService.isVideoSeriesOfflineModeEnabled(videoSeriesId)) {
            VideoFragment.this._localVideo = iLocalVideoService.getLocalVideo(videoId, videoSeriesId);
            if (VideoFragment.this._localVideo.isVideoObjectAvailable()) {
                VideoFragment.this.videoLoaded(iLocalVideoService.getVideo(videoId, videoSeriesId));
                return;
            }
            VideoFragment.this.loadVideo(videoId, videoSeriesId);
            return;
        }
        VideoFragment.this.loadVideo(videoId, videoSeriesId);
    }
}
