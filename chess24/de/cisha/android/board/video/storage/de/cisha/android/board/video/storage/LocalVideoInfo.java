/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package de.cisha.android.board.video.storage;

import android.net.Uri;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class LocalVideoInfo {
    private long _currentLoadedBytes;
    private float _downloadProgress;
    private boolean _isVideoObjectAvailable;
    private Set<LocalVideoStateChangeListener> _listeners;
    private VideoSeriesId _seriesId;
    private LocalVideoServiceDownloadState _state;
    private long _totalBytes;
    private Uri _videoFile;
    private VideoId _videoId;

    public LocalVideoInfo(VideoId videoId, VideoSeriesId videoSeriesId) {
        this._videoId = videoId;
        this._seriesId = videoSeriesId;
        this._listeners = Collections.newSetFromMap(new WeakHashMap());
    }

    private void notifyStateListeners() {
        Iterator<LocalVideoStateChangeListener> iterator = this._listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onLocalVideoStateChanged(this, this._isVideoObjectAvailable, this._downloadProgress, this._videoFile, this._state);
        }
    }

    public void addListener(LocalVideoStateChangeListener localVideoStateChangeListener) {
        this._listeners.add(localVideoStateChangeListener);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocalVideoInfo)) {
            return false;
        }
        object = (LocalVideoInfo)object;
        if (object._videoId != null && this._videoId != null && object._videoId.equals(this._videoId)) {
            return true;
        }
        return false;
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

    public int hashCode() {
        if (this._videoId != null) {
            return this._videoId.hashCode();
        }
        return 0;
    }

    public boolean isVideoObjectAvailable() {
        return this._isVideoObjectAvailable;
    }

    public void removeListener(LocalVideoStateChangeListener localVideoStateChangeListener) {
        this._listeners.remove(localVideoStateChangeListener);
    }

    public void setDownloadProgress(long l, long l2) {
        this._currentLoadedBytes = l;
        this._totalBytes = l2;
        float f = (float)((double)l / (double)l2);
        if (f != this._downloadProgress) {
            this._downloadProgress = f;
            this.notifyStateListeners();
        }
    }

    public void setState(LocalVideoServiceDownloadState localVideoServiceDownloadState) {
        if (localVideoServiceDownloadState != this._state) {
            this._state = localVideoServiceDownloadState;
            this.notifyStateListeners();
        }
    }

    public void setVideoFile(Uri uri) {
        if (uri != null && !uri.equals((Object)this._videoFile) || uri == null && this._videoFile != null) {
            this._videoFile = uri;
            this.notifyStateListeners();
        }
    }

    public void setVideoObjectAvailable(boolean bl) {
        if (bl != this._isVideoObjectAvailable) {
            this._isVideoObjectAvailable = bl;
            this.notifyStateListeners();
        }
    }

    public static enum LocalVideoServiceDownloadState {
        COMPLETED,
        DOWNLOADING,
        WAITING_FOR_DISK_SPACE,
        WAITING_FOR_WIRELESS_LAN;
        

        private LocalVideoServiceDownloadState() {
        }
    }

    public static interface LocalVideoStateChangeListener {
        public void onLocalVideoStateChanged(LocalVideoInfo var1, boolean var2, float var3, Uri var4, LocalVideoServiceDownloadState var5);
    }

}
