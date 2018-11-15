/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoStorage;
import de.cisha.chess.util.Logger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalVideoStorageMock
implements ILocalVideoStorage {
    private Map<VideoSeriesId, JSONObject> _mapSeriesJson = new HashMap<VideoSeriesId, JSONObject>();
    private Map<VideoSeriesId, Map<VideoId, Long>> _mapVideoDownloadRequestId = new HashMap<VideoSeriesId, Map<VideoId, Long>>();
    private Map<VideoSeriesId, Map<VideoId, JSONObject>> _mapVideoJson = new HashMap<VideoSeriesId, Map<VideoId, JSONObject>>();
    private Map<Long, Long> _mapVideoSize = new HashMap<Long, Long>();
    private Set<VideoSeriesId> _setLocalSeriesIds = new HashSet<VideoSeriesId>();

    @Override
    public List<VideoSeriesId> getAllLocalVideoSeries() {
        return new LinkedList<VideoSeriesId>(this._setLocalSeriesIds);
    }

    @Override
    public List<VideoId> getAllLocalVideosForSeries(VideoSeriesId object) {
        LinkedList<VideoId> linkedList = new LinkedList<VideoId>();
        if ((object = this._mapVideoJson.get(object)) != null) {
            linkedList.addAll(object.keySet());
        }
        return linkedList;
    }

    @Override
    public List<ILocalVideoStorage.VideoDownloadRequestTupel> getAllVideoDownloadRequestIds() {
        LinkedList<ILocalVideoStorage.VideoDownloadRequestTupel> linkedList = new LinkedList<ILocalVideoStorage.VideoDownloadRequestTupel>();
        for (Map.Entry<VideoSeriesId, Map<VideoId, Long>> entry : this._mapVideoDownloadRequestId.entrySet()) {
            for (Map.Entry<VideoId, Long> entry2 : entry.getValue().entrySet()) {
                linkedList.add(new ILocalVideoStorage.VideoDownloadRequestTupel(entry.getKey(), entry2.getKey(), entry2.getValue()));
            }
        }
        return linkedList;
    }

    @Override
    public long getDownloadIdForVideo(VideoSeriesId object, VideoId videoId) {
        if ((object = this._mapVideoDownloadRequestId.get(object)) != null && (object = (Long)object.get(videoId)) != null) {
            return object.longValue();
        }
        return 0L;
    }

    @Override
    public long getFilesizeForVideodownload(long l) {
        Long l2 = this._mapVideoSize.get(l);
        if (l2 != null) {
            return l2;
        }
        return 0L;
    }

    @Override
    public Video getVideo(VideoSeriesId object, VideoId videoId, JSONAPIResultParser<Video> jSONAPIResultParser) {
        if ((object = this._mapVideoJson.get(object)) != null && (object = (JSONObject)object.get(videoId)) != null) {
            try {
                object = jSONAPIResultParser.parseResult((JSONObject)object);
                return object;
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
            }
        }
        return null;
    }

    @Override
    public VideoSeries getVideoSeries(VideoSeriesId object, JSONAPIResultParser<VideoSeries> jSONAPIResultParser) {
        if ((object = this._mapSeriesJson.get(object)) != null) {
            try {
                object = jSONAPIResultParser.parseResult((JSONObject)object);
                return object;
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
            }
        }
        return null;
    }

    @Override
    public boolean isVideoJSONNotNull(VideoSeriesId object, VideoId videoId) {
        if ((object = this._mapVideoJson.get(object)) != null) {
            return object.containsKey(videoId);
        }
        return false;
    }

    @Override
    public boolean isVideoSeriesJSONNotNull(VideoSeriesId videoSeriesId) {
        return this._mapSeriesJson.containsKey(videoSeriesId);
    }

    @Override
    public boolean isVideoSeriesLocalAvailable(VideoSeriesId videoSeriesId) {
        return this._setLocalSeriesIds.contains(videoSeriesId);
    }

    @Override
    public void putVideoDownloadIdWithFilesize(VideoSeriesId videoSeriesId, VideoId videoId, long l, long l2) {
        Map<VideoId, Long> map;
        Map<VideoId, Long> map2 = map = this._mapVideoDownloadRequestId.get(videoSeriesId);
        if (map == null) {
            map2 = new HashMap<VideoId, Long>();
            this._mapVideoDownloadRequestId.put(videoSeriesId, map2);
        }
        map2.put(videoId, l);
        this._mapVideoSize.put(l, l2);
    }

    @Override
    public boolean putVideoJSON(VideoSeriesId videoSeriesId, VideoId videoId, JSONObject jSONObject) {
        Map<VideoId, JSONObject> map = this._mapVideoJson.get(videoSeriesId);
        boolean bl = false;
        boolean bl2 = false;
        if (map != null) {
            videoSeriesId = map.put(videoId, jSONObject);
            if (videoSeriesId != null) {
                return ((Object)videoSeriesId).equals((Object)jSONObject) ^ true;
            }
            if (jSONObject != null) {
                bl2 = true;
            }
            return bl2;
        }
        map = new HashMap<VideoId, JSONObject>();
        this._mapVideoJson.put(videoSeriesId, map);
        map.put(videoId, jSONObject);
        bl2 = bl;
        if (jSONObject != null) {
            bl2 = true;
        }
        return bl2;
    }

    @Override
    public boolean putVideoSeriesJSON(VideoSeriesId videoSeriesId, JSONObject jSONObject) {
        JSONObject jSONObject2 = this._mapSeriesJson.get(videoSeriesId);
        this._mapSeriesJson.put(videoSeriesId, jSONObject);
        if (jSONObject == null) {
            if (jSONObject2 != null) {
                return true;
            }
            return false;
        }
        return true ^ jSONObject.equals((Object)jSONObject2);
    }

    @Override
    public boolean removeAllDataForVideo(VideoSeriesId object, VideoId videoId) {
        object = this._mapVideoJson.get(object);
        boolean bl = false;
        if (object != null) {
            if (object.remove(videoId) != null) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @Override
    public boolean removeAllDataForVideoSeries(VideoSeriesId videoSeriesId) {
        this._mapVideoJson.remove(videoSeriesId);
        JSONObject jSONObject = this._mapSeriesJson.remove(videoSeriesId);
        boolean bl = false;
        boolean bl2 = jSONObject != null;
        if (this._mapVideoDownloadRequestId.remove(videoSeriesId) != null) {
            bl = true;
        }
        return bl2 & bl;
    }

    @Override
    public boolean setVideoSeriesAsLocal(VideoSeriesId videoSeriesId, boolean bl) {
        if (bl) {
            return this._setLocalSeriesIds.add(videoSeriesId);
        }
        return this._setLocalSeriesIds.remove(videoSeriesId);
    }
}
