// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import org.json.JSONObject;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.List;

public interface ILocalVideoStorage
{
    List<VideoSeriesId> getAllLocalVideoSeries();
    
    List<VideoId> getAllLocalVideosForSeries(final VideoSeriesId p0);
    
    List<VideoDownloadRequestTupel> getAllVideoDownloadRequestIds();
    
    long getDownloadIdForVideo(final VideoSeriesId p0, final VideoId p1);
    
    long getFilesizeForVideodownload(final long p0);
    
    Video getVideo(final VideoSeriesId p0, final VideoId p1, final JSONAPIResultParser<Video> p2);
    
    VideoSeries getVideoSeries(final VideoSeriesId p0, final JSONAPIResultParser<VideoSeries> p1);
    
    boolean isVideoJSONNotNull(final VideoSeriesId p0, final VideoId p1);
    
    boolean isVideoSeriesJSONNotNull(final VideoSeriesId p0);
    
    boolean isVideoSeriesLocalAvailable(final VideoSeriesId p0);
    
    void putVideoDownloadIdWithFilesize(final VideoSeriesId p0, final VideoId p1, final long p2, final long p3);
    
    boolean putVideoJSON(final VideoSeriesId p0, final VideoId p1, final JSONObject p2);
    
    boolean putVideoSeriesJSON(final VideoSeriesId p0, final JSONObject p1);
    
    boolean removeAllDataForVideo(final VideoSeriesId p0, final VideoId p1);
    
    boolean removeAllDataForVideoSeries(final VideoSeriesId p0);
    
    boolean setVideoSeriesAsLocal(final VideoSeriesId p0, final boolean p1);
    
    public static class VideoDownloadRequestTupel
    {
        private long _downloadId;
        private VideoSeriesId _seriesId;
        private VideoId _videoId;
        
        public VideoDownloadRequestTupel(final VideoSeriesId seriesId, final VideoId videoId, final long downloadId) {
            this._seriesId = seriesId;
            this._videoId = videoId;
            this._downloadId = downloadId;
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
