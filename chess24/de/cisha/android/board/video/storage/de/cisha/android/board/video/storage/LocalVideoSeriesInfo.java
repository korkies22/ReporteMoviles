/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import java.util.Iterator;
import java.util.List;

public class LocalVideoSeriesInfo {
    private LocalVideoSeriesChangeListener _listener;
    private VideoSeriesId _seriesId;
    private boolean _seriesObjectAvailable;
    private List<LocalVideoInfo> _videoList;

    public LocalVideoSeriesInfo(VideoSeriesId videoSeriesId) {
        this._seriesId = videoSeriesId;
        this._videoList = null;
    }

    private void notifyChangeListeners() {
        if (this._listener != null) {
            this._listener.onLocalVideoChanged(this._seriesObjectAvailable, this._videoList);
        }
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof LocalVideoSeriesInfo)) {
            return false;
        }
        object = (LocalVideoSeriesInfo)object;
        if (object._seriesId != null) {
            if (this._seriesId == null) {
                return false;
            }
            return object._seriesId.equals(this._seriesId);
        }
        return false;
    }

    public long getCurrentLoadedBytes() {
        long l;
        Object object = this._videoList;
        long l2 = l = 0L;
        if (object != null) {
            object = object.iterator();
            do {
                l2 = l;
                if (!object.hasNext()) break;
                l += ((LocalVideoInfo)object.next()).getCurrentLoadedBytes();
            } while (true);
        }
        return l2;
    }

    public float getDownloadProgress() {
        if (this._videoList != null && this._videoList.size() > 0) {
            Iterator<LocalVideoInfo> iterator = this._videoList.iterator();
            double d = 0.0;
            double d2 = 0.0;
            while (iterator.hasNext()) {
                LocalVideoInfo localVideoInfo = iterator.next();
                d += (double)localVideoInfo.getTotalBytes();
                d2 += (double)localVideoInfo.getCurrentLoadedBytes();
            }
            return (float)(d2 / d);
        }
        return 0.0f;
    }

    public VideoSeriesId getSeriesId() {
        return this._seriesId;
    }

    public long getTotalBytes() {
        long l;
        Object object = this._videoList;
        long l2 = l = 0L;
        if (object != null) {
            object = object.iterator();
            do {
                l2 = l;
                if (!object.hasNext()) break;
                l += ((LocalVideoInfo)object.next()).getTotalBytes();
            } while (true);
        }
        return l2;
    }

    public List<LocalVideoInfo> getVideoList() {
        return this._videoList;
    }

    public int hashCode() {
        if (this._seriesId != null) {
            return this._seriesId.hashCode();
        }
        return 0;
    }

    public boolean isSeriesObjectAvailable() {
        return this._seriesObjectAvailable;
    }

    public void setListener(LocalVideoSeriesChangeListener localVideoSeriesChangeListener) {
        this._listener = localVideoSeriesChangeListener;
    }

    public void setLocalVideoList(List<LocalVideoInfo> list) {
        this._videoList = list;
        this.notifyChangeListeners();
    }

    public void setSeriesObjectAvailable(boolean bl) {
        this._seriesObjectAvailable = bl;
        this.notifyChangeListeners();
    }

    public static interface LocalVideoSeriesChangeListener {
        public void onLocalVideoChanged(boolean var1, List<LocalVideoInfo> var2);
    }

}
