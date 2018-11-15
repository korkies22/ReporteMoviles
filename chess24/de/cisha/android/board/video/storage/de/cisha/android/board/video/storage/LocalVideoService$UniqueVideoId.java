/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.LocalVideoService;
import de.cisha.chess.model.CishaUUID;

private static class LocalVideoService.UniqueVideoId
extends CishaUUID {
    private final VideoSeriesId _seriesId;
    private final VideoId _videoId;

    public LocalVideoService.UniqueVideoId(VideoSeriesId videoSeriesId, VideoId videoId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("S");
        stringBuilder.append(videoSeriesId.getUuid());
        stringBuilder.append("V");
        stringBuilder.append(videoId.getUuid());
        super(stringBuilder.toString(), true);
        this._seriesId = videoSeriesId;
        this._videoId = videoId;
    }

    public VideoSeriesId getSeriesId() {
        return this._seriesId;
    }

    public VideoId getVideoId() {
        return this._videoId;
    }
}
