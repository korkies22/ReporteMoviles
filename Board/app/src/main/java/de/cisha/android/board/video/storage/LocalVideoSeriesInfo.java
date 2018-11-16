// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import java.util.Iterator;
import java.util.List;
import de.cisha.android.board.video.model.VideoSeriesId;

public class LocalVideoSeriesInfo
{
    private LocalVideoSeriesChangeListener _listener;
    private VideoSeriesId _seriesId;
    private boolean _seriesObjectAvailable;
    private List<LocalVideoInfo> _videoList;
    
    public LocalVideoSeriesInfo(final VideoSeriesId seriesId) {
        this._seriesId = seriesId;
        this._videoList = null;
    }
    
    private void notifyChangeListeners() {
        if (this._listener != null) {
            this._listener.onLocalVideoChanged(this._seriesObjectAvailable, this._videoList);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LocalVideoSeriesInfo)) {
            return false;
        }
        final LocalVideoSeriesInfo localVideoSeriesInfo = (LocalVideoSeriesInfo)o;
        return localVideoSeriesInfo._seriesId != null && this._seriesId != null && localVideoSeriesInfo._seriesId.equals(this._seriesId);
    }
    
    public long getCurrentLoadedBytes() {
        final List<LocalVideoInfo> videoList = this._videoList;
        long n2;
        long n = n2 = 0L;
        if (videoList != null) {
            final Iterator<LocalVideoInfo> iterator = videoList.iterator();
            while (true) {
                n2 = n;
                if (!iterator.hasNext()) {
                    break;
                }
                n += iterator.next().getCurrentLoadedBytes();
            }
        }
        return n2;
    }
    
    public float getDownloadProgress() {
        if (this._videoList != null && this._videoList.size() > 0) {
            final Iterator<LocalVideoInfo> iterator = this._videoList.iterator();
            double n = 0.0;
            double n2 = 0.0;
            while (iterator.hasNext()) {
                final LocalVideoInfo localVideoInfo = iterator.next();
                n += localVideoInfo.getTotalBytes();
                n2 += localVideoInfo.getCurrentLoadedBytes();
            }
            return (float)(n2 / n);
        }
        return 0.0f;
    }
    
    public VideoSeriesId getSeriesId() {
        return this._seriesId;
    }
    
    public long getTotalBytes() {
        final List<LocalVideoInfo> videoList = this._videoList;
        long n2;
        long n = n2 = 0L;
        if (videoList != null) {
            final Iterator<LocalVideoInfo> iterator = videoList.iterator();
            while (true) {
                n2 = n;
                if (!iterator.hasNext()) {
                    break;
                }
                n += iterator.next().getTotalBytes();
            }
        }
        return n2;
    }
    
    public List<LocalVideoInfo> getVideoList() {
        return this._videoList;
    }
    
    @Override
    public int hashCode() {
        if (this._seriesId != null) {
            return this._seriesId.hashCode();
        }
        return 0;
    }
    
    public boolean isSeriesObjectAvailable() {
        return this._seriesObjectAvailable;
    }
    
    public void setListener(final LocalVideoSeriesChangeListener listener) {
        this._listener = listener;
    }
    
    public void setLocalVideoList(final List<LocalVideoInfo> videoList) {
        this._videoList = videoList;
        this.notifyChangeListeners();
    }
    
    public void setSeriesObjectAvailable(final boolean seriesObjectAvailable) {
        this._seriesObjectAvailable = seriesObjectAvailable;
        this.notifyChangeListeners();
    }
    
    public interface LocalVideoSeriesChangeListener
    {
        void onLocalVideoChanged(final boolean p0, final List<LocalVideoInfo> p1);
    }
}
