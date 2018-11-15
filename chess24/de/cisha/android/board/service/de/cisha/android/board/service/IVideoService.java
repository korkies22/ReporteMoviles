/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package de.cisha.android.board.service;

import android.app.Activity;
import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;

public interface IVideoService {
    public void getVideo(VideoId var1, VideoSeriesId var2, LoadCommandCallback<Video> var3);

    public void getVideoSeries(VideoSeriesId var1, LoadCommandCallback<VideoSeries> var2);

    public void getVideoSeriesList(VideoFilter var1, LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> var2);

    public void getVideoSeriesWithSerializeRepresentation(VideoSeriesId var1, LoadCommandCallback<ISerializedRepresentationHolder<VideoSeries>> var2);

    public void getVideoWithSerializedRepresentation(VideoId var1, VideoSeriesId var2, LoadCommandCallback<ISerializedRepresentationHolder<Video>> var3);

    public void purchaseVideoSeries(VideoSeriesId var1, int var2, Activity var3, LoadCommandCallback<Void> var4);
}
