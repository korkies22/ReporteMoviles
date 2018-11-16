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
import android.content.Context;

public class LocalVideoStorageSqlite implements ILocalVideoStorage
{
    private Context _context;
    private String _userPrefix;
    
    public LocalVideoStorageSqlite(final Context context, final String userPrefix) {
        this._context = context;
        this._userPrefix = userPrefix;
    }
    
    private LocalVideoStorageDbHelper createDbHelper() {
        return new LocalVideoStorageDbHelper(this._context, this._userPrefix);
    }
    
    @Override
    public List<VideoSeriesId> getAllLocalVideoSeries() {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final List<VideoSeriesId> allLocalVideoSeries = dbHelper.getAllLocalVideoSeries();
            dbHelper.close();
            return allLocalVideoSeries;
        }
    }
    
    @Override
    public List<VideoId> getAllLocalVideosForSeries(final VideoSeriesId videoSeriesId) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final List<VideoId> allLocalVideosForSeries = dbHelper.getAllLocalVideosForSeries(videoSeriesId);
            dbHelper.close();
            return allLocalVideosForSeries;
        }
    }
    
    @Override
    public List<VideoDownloadRequestTupel> getAllVideoDownloadRequestIds() {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final List<VideoDownloadRequestTupel> allVideoDownloadRequestIds = dbHelper.getAllVideoDownloadRequestIds();
            dbHelper.close();
            return allVideoDownloadRequestIds;
        }
    }
    
    @Override
    public long getDownloadIdForVideo(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final long downloadIdForVideo = dbHelper.getDownloadIdForVideo(videoSeriesId, videoId);
            dbHelper.close();
            return downloadIdForVideo;
        }
    }
    
    @Override
    public long getFilesizeForVideodownload(long filesizeForVideodownload) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            filesizeForVideodownload = dbHelper.getFilesizeForVideodownload(filesizeForVideodownload);
            dbHelper.close();
            return filesizeForVideodownload;
        }
    }
    
    @Override
    public Video getVideo(final VideoSeriesId videoSeriesId, final VideoId videoId, final JSONAPIResultParser<Video> jsonapiResultParser) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final Video video = dbHelper.getVideo(videoSeriesId, videoId, jsonapiResultParser);
            dbHelper.close();
            return video;
        }
    }
    
    @Override
    public VideoSeries getVideoSeries(final VideoSeriesId videoSeriesId, final JSONAPIResultParser<VideoSeries> jsonapiResultParser) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final VideoSeries videoSeries = dbHelper.getVideoSeries(videoSeriesId, jsonapiResultParser);
            dbHelper.close();
            return videoSeries;
        }
    }
    
    @Override
    public boolean isVideoJSONNotNull(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean videoJSONNotNull = dbHelper.isVideoJSONNotNull(videoSeriesId, videoId);
            dbHelper.close();
            return videoJSONNotNull;
        }
    }
    
    @Override
    public boolean isVideoSeriesJSONNotNull(final VideoSeriesId videoSeriesId) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean videoSeriesJSONNotNull = dbHelper.isVideoSeriesJSONNotNull(videoSeriesId);
            dbHelper.close();
            return videoSeriesJSONNotNull;
        }
    }
    
    @Override
    public boolean isVideoSeriesLocalAvailable(final VideoSeriesId videoSeriesId) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean videoSeriesLocalAvailable = dbHelper.isVideoSeriesLocalAvailable(videoSeriesId);
            dbHelper.close();
            return videoSeriesLocalAvailable;
        }
    }
    
    @Override
    public void putVideoDownloadIdWithFilesize(final VideoSeriesId videoSeriesId, final VideoId videoId, final long n, final long n2) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            dbHelper.putVideoDownloadIdWithFilesize(videoSeriesId, videoId, n, n2);
            dbHelper.close();
        }
    }
    
    @Override
    public boolean putVideoJSON(final VideoSeriesId videoSeriesId, final VideoId videoId, final JSONObject jsonObject) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean putVideoJSON = dbHelper.putVideoJSON(videoSeriesId, videoId, jsonObject);
            dbHelper.close();
            return putVideoJSON;
        }
    }
    
    @Override
    public boolean putVideoSeriesJSON(final VideoSeriesId videoSeriesId, final JSONObject jsonObject) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean putVideoSeriesJSON = dbHelper.putVideoSeriesJSON(videoSeriesId, jsonObject);
            dbHelper.close();
            return putVideoSeriesJSON;
        }
    }
    
    @Override
    public boolean removeAllDataForVideo(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean removeAllDataForVideo = dbHelper.removeAllDataForVideo(videoSeriesId, videoId);
            dbHelper.close();
            return removeAllDataForVideo;
        }
    }
    
    @Override
    public boolean removeAllDataForVideoSeries(final VideoSeriesId videoSeriesId) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean removeAllDataForVideoSeries = dbHelper.removeAllDataForVideoSeries(videoSeriesId);
            dbHelper.close();
            return removeAllDataForVideoSeries;
        }
    }
    
    @Override
    public boolean setVideoSeriesAsLocal(final VideoSeriesId videoSeriesId, final boolean b) {
        synchronized (this) {
            final LocalVideoStorageDbHelper dbHelper = this.createDbHelper();
            final boolean setVideoSeriesAsLocal = dbHelper.setVideoSeriesAsLocal(videoSeriesId, b);
            dbHelper.close();
            return setVideoSeriesAsLocal;
        }
    }
}
