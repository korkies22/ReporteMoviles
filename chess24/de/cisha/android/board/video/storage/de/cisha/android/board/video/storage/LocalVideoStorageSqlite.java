/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import android.content.Context;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.android.board.video.storage.LocalVideoStorageDbHelper;
import java.util.List;
import org.json.JSONObject;

public class LocalVideoStorageSqlite
implements ILocalVideoStorage {
    private Context _context;
    private String _userPrefix;

    public LocalVideoStorageSqlite(Context context, String string) {
        this._context = context;
        this._userPrefix = string;
    }

    private LocalVideoStorageDbHelper createDbHelper() {
        return new LocalVideoStorageDbHelper(this._context, this._userPrefix);
    }

    @Override
    public List<VideoSeriesId> getAllLocalVideoSeries() {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            List<VideoSeriesId> list = localVideoStorageDbHelper.getAllLocalVideoSeries();
            localVideoStorageDbHelper.close();
            return list;
        }
    }

    @Override
    public List<VideoId> getAllLocalVideosForSeries(VideoSeriesId object) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            object = localVideoStorageDbHelper.getAllLocalVideosForSeries((VideoSeriesId)object);
            localVideoStorageDbHelper.close();
            return object;
        }
    }

    @Override
    public List<ILocalVideoStorage.VideoDownloadRequestTupel> getAllVideoDownloadRequestIds() {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            List<ILocalVideoStorage.VideoDownloadRequestTupel> list = localVideoStorageDbHelper.getAllVideoDownloadRequestIds();
            localVideoStorageDbHelper.close();
            return list;
        }
    }

    @Override
    public long getDownloadIdForVideo(VideoSeriesId videoSeriesId, VideoId videoId) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            long l = localVideoStorageDbHelper.getDownloadIdForVideo(videoSeriesId, videoId);
            localVideoStorageDbHelper.close();
            return l;
        }
    }

    @Override
    public long getFilesizeForVideodownload(long l) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            l = localVideoStorageDbHelper.getFilesizeForVideodownload(l);
            localVideoStorageDbHelper.close();
            return l;
        }
    }

    @Override
    public Video getVideo(VideoSeriesId object, VideoId videoId, JSONAPIResultParser<Video> jSONAPIResultParser) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            object = localVideoStorageDbHelper.getVideo((VideoSeriesId)object, videoId, jSONAPIResultParser);
            localVideoStorageDbHelper.close();
            return object;
        }
    }

    @Override
    public VideoSeries getVideoSeries(VideoSeriesId object, JSONAPIResultParser<VideoSeries> jSONAPIResultParser) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            object = localVideoStorageDbHelper.getVideoSeries((VideoSeriesId)object, jSONAPIResultParser);
            localVideoStorageDbHelper.close();
            return object;
        }
    }

    @Override
    public boolean isVideoJSONNotNull(VideoSeriesId videoSeriesId, VideoId videoId) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            boolean bl = localVideoStorageDbHelper.isVideoJSONNotNull(videoSeriesId, videoId);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }

    @Override
    public boolean isVideoSeriesJSONNotNull(VideoSeriesId videoSeriesId) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            boolean bl = localVideoStorageDbHelper.isVideoSeriesJSONNotNull(videoSeriesId);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }

    @Override
    public boolean isVideoSeriesLocalAvailable(VideoSeriesId videoSeriesId) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            boolean bl = localVideoStorageDbHelper.isVideoSeriesLocalAvailable(videoSeriesId);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }

    @Override
    public void putVideoDownloadIdWithFilesize(VideoSeriesId videoSeriesId, VideoId videoId, long l, long l2) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            localVideoStorageDbHelper.putVideoDownloadIdWithFilesize(videoSeriesId, videoId, l, l2);
            localVideoStorageDbHelper.close();
            return;
        }
    }

    @Override
    public boolean putVideoJSON(VideoSeriesId videoSeriesId, VideoId videoId, JSONObject jSONObject) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            boolean bl = localVideoStorageDbHelper.putVideoJSON(videoSeriesId, videoId, jSONObject);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }

    @Override
    public boolean putVideoSeriesJSON(VideoSeriesId videoSeriesId, JSONObject jSONObject) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            boolean bl = localVideoStorageDbHelper.putVideoSeriesJSON(videoSeriesId, jSONObject);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }

    @Override
    public boolean removeAllDataForVideo(VideoSeriesId videoSeriesId, VideoId videoId) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            boolean bl = localVideoStorageDbHelper.removeAllDataForVideo(videoSeriesId, videoId);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }

    @Override
    public boolean removeAllDataForVideoSeries(VideoSeriesId videoSeriesId) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            boolean bl = localVideoStorageDbHelper.removeAllDataForVideoSeries(videoSeriesId);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }

    @Override
    public boolean setVideoSeriesAsLocal(VideoSeriesId videoSeriesId, boolean bl) {
        synchronized (this) {
            LocalVideoStorageDbHelper localVideoStorageDbHelper = this.createDbHelper();
            bl = localVideoStorageDbHelper.setVideoSeriesAsLocal(videoSeriesId, bl);
            localVideoStorageDbHelper.close();
            return bl;
        }
    }
}
