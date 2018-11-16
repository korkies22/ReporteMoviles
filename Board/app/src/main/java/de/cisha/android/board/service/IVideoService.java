// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.app.Activity;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoId;

public interface IVideoService
{
    void getVideo(final VideoId p0, final VideoSeriesId p1, final LoadCommandCallback<Video> p2);
    
    void getVideoSeries(final VideoSeriesId p0, final LoadCommandCallback<VideoSeries> p1);
    
    void getVideoSeriesList(final VideoFilter p0, final LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> p1);
    
    void getVideoSeriesWithSerializeRepresentation(final VideoSeriesId p0, final LoadCommandCallback<ISerializedRepresentationHolder<VideoSeries>> p1);
    
    void getVideoWithSerializedRepresentation(final VideoId p0, final VideoSeriesId p1, final LoadCommandCallback<ISerializedRepresentationHolder<Video>> p2);
    
    void purchaseVideoSeries(final VideoSeriesId p0, final int p1, final Activity p2, final LoadCommandCallback<Void> p3);
}
