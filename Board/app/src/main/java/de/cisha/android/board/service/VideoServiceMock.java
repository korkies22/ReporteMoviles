// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import android.app.Activity;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.MinMaxEloRange;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoSeries;
import java.util.Map;
import java.util.List;
import android.net.Uri;
import de.cisha.chess.model.CishaUUID;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.chess.model.MoveContainer;
import java.util.HashMap;
import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.chess.model.Game;
import java.util.LinkedList;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoId;
import android.os.Looper;
import android.os.Handler;

public class VideoServiceMock implements IVideoService
{
    private Handler _uiThreadHandler;
    
    public VideoServiceMock() {
        this._uiThreadHandler = new Handler(Looper.getMainLooper());
    }
    
    @Override
    public void getVideo(final VideoId videoId, final VideoSeriesId videoSeriesId, final LoadCommandCallback<Video> loadCommandCallback) {
        this._uiThreadHandler.post((Runnable)new Runnable() {
            final /* synthetic */ Video val.result = new Video("vTitle", "vAuthor", "GM", "vDescription", 12345L, null, null, new LinkedList<Game>(), new LinkedList<VideoCommand>(), new HashMap<MoveContainer, BoardMarks>());
            
            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(this.val.result);
            }
        });
    }
    
    @Override
    public void getVideoSeries(final VideoSeriesId videoSeriesId, final LoadCommandCallback<VideoSeries> loadCommandCallback) {
        final LinkedList<VideoInformation> list = new LinkedList<VideoInformation>();
        list.add(new VideoInformation(new VideoId("id1"), "videoname1", 60000L, "descr", null, false, false, false));
        final StringBuilder sb = new StringBuilder();
        sb.append("seriesname:");
        sb.append(videoSeriesId.getUuid());
        this._uiThreadHandler.post((Runnable)new Runnable() {
            final /* synthetic */ VideoSeries val.result = new VideoSeries(sb.toString(), VideoLanguage.ENGLISH, "descSeries", "goalsSeries", 0, 123456L, new MinMaxEloRange(1200, 1800), "authorSeries", "GM", null, false, false, list);
            
            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(this.val.result);
            }
        });
    }
    
    @Override
    public void getVideoSeriesList(final VideoFilter videoFilter, final LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> loadCommandCallback) {
        final LinkedList<VideoSeriesInformation> list = new LinkedList<VideoSeriesInformation>();
        list.add(new VideoSeriesInformation(new VideoSeriesId("v1"), "Series1", 0, "intro", "goals", VideoLanguage.ENGLISH, "Author", "gm", 5, 3600L, new MinMaxEloRange(1800, 2200), null, false, false));
        this._uiThreadHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(new JSONVideoSeriesInfoListParser.VideoSeriesInfoList(list, 1));
            }
        });
    }
    
    @Override
    public void getVideoSeriesWithSerializeRepresentation(final VideoSeriesId videoSeriesId, final LoadCommandCallback<ISerializedRepresentationHolder<VideoSeries>> loadCommandCallback) {
    }
    
    @Override
    public void getVideoWithSerializedRepresentation(final VideoId videoId, final VideoSeriesId videoSeriesId, final LoadCommandCallback<ISerializedRepresentationHolder<Video>> loadCommandCallback) {
    }
    
    @Override
    public void purchaseVideoSeries(final VideoSeriesId videoSeriesId, final int n, final Activity activity, final LoadCommandCallback<Void> loadCommandCallback) {
    }
}
