// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.Iterator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import de.cisha.android.board.video.model.VideoId;
import org.json.JSONObject;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.Map;

public class LocalVideoStorageMock implements ILocalVideoStorage
{
    private Map<VideoSeriesId, JSONObject> _mapSeriesJson;
    private Map<VideoSeriesId, Map<VideoId, Long>> _mapVideoDownloadRequestId;
    private Map<VideoSeriesId, Map<VideoId, JSONObject>> _mapVideoJson;
    private Map<Long, Long> _mapVideoSize;
    private Set<VideoSeriesId> _setLocalSeriesIds;
    
    public LocalVideoStorageMock() {
        this._setLocalSeriesIds = new HashSet<VideoSeriesId>();
        this._mapSeriesJson = new HashMap<VideoSeriesId, JSONObject>();
        this._mapVideoJson = new HashMap<VideoSeriesId, Map<VideoId, JSONObject>>();
        this._mapVideoDownloadRequestId = new HashMap<VideoSeriesId, Map<VideoId, Long>>();
        this._mapVideoSize = new HashMap<Long, Long>();
    }
    
    @Override
    public List<VideoSeriesId> getAllLocalVideoSeries() {
        return new LinkedList<VideoSeriesId>(this._setLocalSeriesIds);
    }
    
    @Override
    public List<VideoId> getAllLocalVideosForSeries(final VideoSeriesId videoSeriesId) {
        final LinkedList<Object> list = (LinkedList<Object>)new LinkedList<VideoId>();
        final Map<VideoId, JSONObject> map = this._mapVideoJson.get(videoSeriesId);
        if (map != null) {
            list.addAll(map.keySet());
        }
        return (List<VideoId>)list;
    }
    
    @Override
    public List<VideoDownloadRequestTupel> getAllVideoDownloadRequestIds() {
        final LinkedList<VideoDownloadRequestTupel> list = new LinkedList<VideoDownloadRequestTupel>();
        for (final Map.Entry<VideoSeriesId, Map<VideoId, Long>> entry : this._mapVideoDownloadRequestId.entrySet()) {
            for (final Map.Entry<VideoId, Long> entry2 : entry.getValue().entrySet()) {
                list.add(new VideoDownloadRequestTupel(entry.getKey(), entry2.getKey(), entry2.getValue()));
            }
        }
        return list;
    }
    
    @Override
    public long getDownloadIdForVideo(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        final Map<VideoId, Long> map = this._mapVideoDownloadRequestId.get(videoSeriesId);
        if (map != null) {
            final Long n = map.get(videoId);
            if (n != null) {
                return n;
            }
        }
        return 0L;
    }
    
    @Override
    public long getFilesizeForVideodownload(final long n) {
        final Long n2 = this._mapVideoSize.get(n);
        if (n2 != null) {
            return n2;
        }
        return 0L;
    }
    
    @Override
    public Video getVideo(final VideoSeriesId videoSeriesId, final VideoId videoId, final JSONAPIResultParser<Video> jsonapiResultParser) {
        final Map<VideoId, JSONObject> map = this._mapVideoJson.get(videoSeriesId);
        if (map != null) {
            final JSONObject jsonObject = map.get(videoId);
            if (jsonObject != null) {
                try {
                    return jsonapiResultParser.parseResult(jsonObject);
                }
                catch (JSONException ex) {
                    Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), JSONException.class.getName(), (Throwable)ex);
                }
                catch (InvalidJsonForObjectException ex2) {
                    Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
                }
            }
        }
        return null;
    }
    
    @Override
    public VideoSeries getVideoSeries(final VideoSeriesId videoSeriesId, final JSONAPIResultParser<VideoSeries> jsonapiResultParser) {
        final JSONObject jsonObject = this._mapSeriesJson.get(videoSeriesId);
        if (jsonObject != null) {
            try {
                return jsonapiResultParser.parseResult(jsonObject);
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), JSONException.class.getName(), (Throwable)ex);
            }
            catch (InvalidJsonForObjectException ex2) {
                Logger.getInstance().debug(LocalVideoStorageMock.class.getName(), InvalidJsonForObjectException.class.getName(), ex2);
            }
        }
        return null;
    }
    
    @Override
    public boolean isVideoJSONNotNull(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        final Map<VideoId, JSONObject> map = this._mapVideoJson.get(videoSeriesId);
        return map != null && map.containsKey(videoId);
    }
    
    @Override
    public boolean isVideoSeriesJSONNotNull(final VideoSeriesId videoSeriesId) {
        return this._mapSeriesJson.containsKey(videoSeriesId);
    }
    
    @Override
    public boolean isVideoSeriesLocalAvailable(final VideoSeriesId videoSeriesId) {
        return this._setLocalSeriesIds.contains(videoSeriesId);
    }
    
    @Override
    public void putVideoDownloadIdWithFilesize(final VideoSeriesId videoSeriesId, final VideoId videoId, final long n, final long n2) {
        Map<VideoId, Long> map;
        if ((map = this._mapVideoDownloadRequestId.get(videoSeriesId)) == null) {
            map = new HashMap<VideoId, Long>();
            this._mapVideoDownloadRequestId.put(videoSeriesId, map);
        }
        map.put(videoId, n);
        this._mapVideoSize.put(n, n2);
    }
    
    @Override
    public boolean putVideoJSON(final VideoSeriesId videoSeriesId, final VideoId videoId, final JSONObject jsonObject) {
        final Map<VideoId, JSONObject> map = this._mapVideoJson.get(videoSeriesId);
        final boolean b = false;
        boolean b2 = false;
        if (map == null) {
            final HashMap<VideoId, JSONObject> hashMap = new HashMap<VideoId, JSONObject>();
            this._mapVideoJson.put(videoSeriesId, hashMap);
            hashMap.put(videoId, jsonObject);
            boolean b3 = b;
            if (jsonObject != null) {
                b3 = true;
            }
            return b3;
        }
        final JSONObject jsonObject2 = map.put(videoId, jsonObject);
        if (jsonObject2 != null) {
            return jsonObject2.equals(jsonObject) ^ true;
        }
        if (jsonObject != null) {
            b2 = true;
        }
        return b2;
    }
    
    @Override
    public boolean putVideoSeriesJSON(final VideoSeriesId videoSeriesId, final JSONObject jsonObject) {
        final JSONObject jsonObject2 = this._mapSeriesJson.get(videoSeriesId);
        this._mapSeriesJson.put(videoSeriesId, jsonObject);
        if (jsonObject == null) {
            return jsonObject2 != null;
        }
        return true ^ jsonObject.equals(jsonObject2);
    }
    
    @Override
    public boolean removeAllDataForVideo(final VideoSeriesId videoSeriesId, final VideoId videoId) {
        final Map<VideoId, JSONObject> map = this._mapVideoJson.get(videoSeriesId);
        boolean b = false;
        if (map != null) {
            if (map.remove(videoId) != null) {
                b = true;
            }
            return b;
        }
        return false;
    }
    
    @Override
    public boolean removeAllDataForVideoSeries(final VideoSeriesId videoSeriesId) {
        this._mapVideoJson.remove(videoSeriesId);
        final JSONObject remove = this._mapSeriesJson.remove(videoSeriesId);
        boolean b = false;
        final boolean b2 = remove != null;
        if (this._mapVideoDownloadRequestId.remove(videoSeriesId) != null) {
            b = true;
        }
        return b2 & b;
    }
    
    @Override
    public boolean setVideoSeriesAsLocal(final VideoSeriesId videoSeriesId, final boolean b) {
        if (b) {
            return this._setLocalSeriesIds.add(videoSeriesId);
        }
        return this._setLocalSeriesIds.remove(videoSeriesId);
    }
}
