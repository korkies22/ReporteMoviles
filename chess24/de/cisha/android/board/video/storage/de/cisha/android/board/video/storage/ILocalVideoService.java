/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.storage;

import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.storage.LocalVideoSeriesInfo;
import java.util.List;

public interface ILocalVideoService {
    public void disableOfflineModeForSeries(VideoSeriesId var1);

    public void enableOfflineModeForSeries(VideoSeriesId var1);

    public List<VideoSeriesId> getAllVideoSeriesIdsInOfflineMode();

    public List<VideoSeriesInformation> getAllVideoSeriesInfoAvailable();

    public LocalVideoInfo getLocalVideo(VideoId var1, VideoSeriesId var2);

    public LocalVideoSeriesInfo getLocalVideoSeries(VideoSeriesId var1);

    public Video getVideo(VideoId var1, VideoSeriesId var2);

    public VideoSeries getVideoSeries(VideoSeriesId var1);

    public boolean isVideoSeriesOfflineModeEnabled(VideoSeriesId var1);
}
