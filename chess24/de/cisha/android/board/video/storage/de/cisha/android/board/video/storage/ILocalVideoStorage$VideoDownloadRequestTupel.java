/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;

public static class ILocalVideoStorage.VideoDownloadRequestTupel {
    private long _downloadId;
    private VideoSeriesId _seriesId;
    private VideoId _videoId;

    public ILocalVideoStorage.VideoDownloadRequestTupel(VideoSeriesId videoSeriesId, VideoId videoId, long l) {
        this._seriesId = videoSeriesId;
        this._videoId = videoId;
        this._downloadId = l;
    }

    public long getDownloadId() {
        return this._downloadId;
    }

    public VideoSeriesId getSeriesId() {
        return this._seriesId;
    }

    public VideoId getVideoId() {
        return this._videoId;
    }
}
