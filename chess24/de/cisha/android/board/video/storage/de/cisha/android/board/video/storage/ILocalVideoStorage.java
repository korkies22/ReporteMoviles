/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.List;
import org.json.JSONObject;

public interface ILocalVideoStorage {
    public List<VideoSeriesId> getAllLocalVideoSeries();

    public List<VideoId> getAllLocalVideosForSeries(VideoSeriesId var1);

    public List<VideoDownloadRequestTupel> getAllVideoDownloadRequestIds();

    public long getDownloadIdForVideo(VideoSeriesId var1, VideoId var2);

    public long getFilesizeForVideodownload(long var1);

    public Video getVideo(VideoSeriesId var1, VideoId var2, JSONAPIResultParser<Video> var3);

    public VideoSeries getVideoSeries(VideoSeriesId var1, JSONAPIResultParser<VideoSeries> var2);

    public boolean isVideoJSONNotNull(VideoSeriesId var1, VideoId var2);

    public boolean isVideoSeriesJSONNotNull(VideoSeriesId var1);

    public boolean isVideoSeriesLocalAvailable(VideoSeriesId var1);

    public void putVideoDownloadIdWithFilesize(VideoSeriesId var1, VideoId var2, long var3, long var5);

    public boolean putVideoJSON(VideoSeriesId var1, VideoId var2, JSONObject var3);

    public boolean putVideoSeriesJSON(VideoSeriesId var1, JSONObject var2);

    public boolean removeAllDataForVideo(VideoSeriesId var1, VideoId var2);

    public boolean removeAllDataForVideoSeries(VideoSeriesId var1);

    public boolean setVideoSeriesAsLocal(VideoSeriesId var1, boolean var2);

    public static class VideoDownloadRequestTupel {
        private long _downloadId;
        private VideoSeriesId _seriesId;
        private VideoId _videoId;

        public VideoDownloadRequestTupel(VideoSeriesId videoSeriesId, VideoId videoId, long l) {
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

}
