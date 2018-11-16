// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import de.cisha.android.board.video.model.VideoId;
import android.net.Uri;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.Set;

public class LocalVideoInfo
{
    private long _currentLoadedBytes;
    private float _downloadProgress;
    private boolean _isVideoObjectAvailable;
    private Set<LocalVideoStateChangeListener> _listeners;
    private VideoSeriesId _seriesId;
    private LocalVideoServiceDownloadState _state;
    private long _totalBytes;
    private Uri _videoFile;
    private VideoId _videoId;
    
    public LocalVideoInfo(final VideoId videoId, final VideoSeriesId seriesId) {
        this._videoId = videoId;
        this._seriesId = seriesId;
        this._listeners = Collections.newSetFromMap(new WeakHashMap<LocalVideoStateChangeListener, Boolean>());
    }
    
    private void notifyStateListeners() {
        final Iterator<LocalVideoStateChangeListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onLocalVideoStateChanged(this, this._isVideoObjectAvailable, this._downloadProgress, this._videoFile, this._state);
        }
    }
    
    public void addListener(final LocalVideoStateChangeListener localVideoStateChangeListener) {
        this._listeners.add(localVideoStateChangeListener);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalVideoInfo)) {
            return false;
        }
        final LocalVideoInfo localVideoInfo = (LocalVideoInfo)o;
        return localVideoInfo._videoId != null && this._videoId != null && localVideoInfo._videoId.equals(this._videoId);
    }
    
    public long getCurrentLoadedBytes() {
        return this._currentLoadedBytes;
    }
    
    public float getDownloadProgress() {
        return this._downloadProgress;
    }
    
    public VideoSeriesId getSeriesId() {
        return this._seriesId;
    }
    
    public LocalVideoServiceDownloadState getState() {
        return this._state;
    }
    
    public long getTotalBytes() {
        return this._totalBytes;
    }
    
    public Uri getVideoFile() {
        return this._videoFile;
    }
    
    public VideoId getVideoId() {
        return this._videoId;
    }
    
    @Override
    public int hashCode() {
        if (this._videoId != null) {
            return this._videoId.hashCode();
        }
        return 0;
    }
    
    public boolean isVideoObjectAvailable() {
        return this._isVideoObjectAvailable;
    }
    
    public void removeListener(final LocalVideoStateChangeListener localVideoStateChangeListener) {
        this._listeners.remove(localVideoStateChangeListener);
    }
    
    public void setDownloadProgress(final long currentLoadedBytes, final long totalBytes) {
        this._currentLoadedBytes = currentLoadedBytes;
        this._totalBytes = totalBytes;
        final float downloadProgress = currentLoadedBytes / totalBytes;
        if (downloadProgress != this._downloadProgress) {
            this._downloadProgress = downloadProgress;
            this.notifyStateListeners();
        }
    }
    
    public void setState(final LocalVideoServiceDownloadState state) {
        if (state != this._state) {
            this._state = state;
            this.notifyStateListeners();
        }
    }
    
    public void setVideoFile(final Uri videoFile) {
        if ((videoFile != null && !videoFile.equals((Object)this._videoFile)) || (videoFile == null && this._videoFile != null)) {
            this._videoFile = videoFile;
            this.notifyStateListeners();
        }
    }
    
    public void setVideoObjectAvailable(final boolean isVideoObjectAvailable) {
        if (isVideoObjectAvailable != this._isVideoObjectAvailable) {
            this._isVideoObjectAvailable = isVideoObjectAvailable;
            this.notifyStateListeners();
        }
    }
    
    public enum LocalVideoServiceDownloadState
    {
        COMPLETED, 
        DOWNLOADING, 
        WAITING_FOR_DISK_SPACE, 
        WAITING_FOR_WIRELESS_LAN;
    }
    
    public interface LocalVideoStateChangeListener
    {
        void onLocalVideoStateChanged(final LocalVideoInfo p0, final boolean p1, final float p2, final Uri p3, final LocalVideoServiceDownloadState p4);
    }
}
