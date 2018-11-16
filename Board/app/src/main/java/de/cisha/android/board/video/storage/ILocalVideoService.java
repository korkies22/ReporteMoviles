// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import java.util.List;
import de.cisha.android.board.video.model.VideoSeriesId;

public interface ILocalVideoService
{
    void disableOfflineModeForSeries(final VideoSeriesId p0);
    
    void enableOfflineModeForSeries(final VideoSeriesId p0);
    
    List<VideoSeriesId> getAllVideoSeriesIdsInOfflineMode();
    
    List<VideoSeriesInformation> getAllVideoSeriesInfoAvailable();
    
    LocalVideoInfo getLocalVideo(final VideoId p0, final VideoSeriesId p1);
    
    LocalVideoSeriesInfo getLocalVideoSeries(final VideoSeriesId p0);
    
    Video getVideo(final VideoId p0, final VideoSeriesId p1);
    
    VideoSeries getVideoSeries(final VideoSeriesId p0);
    
    boolean isVideoSeriesOfflineModeEnabled(final VideoSeriesId p0);
}
