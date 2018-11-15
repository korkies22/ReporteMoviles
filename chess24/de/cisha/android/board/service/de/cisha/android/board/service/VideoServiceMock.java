/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 */
package de.cisha.android.board.service;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.service.ISerializedRepresentationHolder;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.jsonparser.JSONVideoSeriesInfoListParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.MinMaxEloRange;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoFilter;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoInformation;
import de.cisha.android.board.video.model.VideoLanguage;
import de.cisha.android.board.video.model.VideoSeries;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoSeriesInformation;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.MoveContainer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VideoServiceMock
implements IVideoService {
    private Handler _uiThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void getVideo(VideoId object, VideoSeriesId videoSeriesId, final LoadCommandCallback<Video> loadCommandCallback) {
        object = new Video("vTitle", "vAuthor", "GM", "vDescription", 12345L, null, null, new LinkedList<Game>(), new LinkedList<VideoCommand>(), new HashMap<MoveContainer, BoardMarks>());
        this._uiThreadHandler.post(new Runnable((Video)object){
            final /* synthetic */ Video val$result;
            {
                this.val$result = video;
            }

            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(this.val$result);
            }
        });
    }

    @Override
    public void getVideoSeries(VideoSeriesId object, final LoadCommandCallback<VideoSeries> loadCommandCallback) {
        LinkedList<VideoInformation> linkedList = new LinkedList<VideoInformation>();
        linkedList.add(new VideoInformation(new VideoId("id1"), "videoname1", 60000L, "descr", null, false, false, false));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("seriesname:");
        stringBuilder.append(object.getUuid());
        object = new VideoSeries(stringBuilder.toString(), VideoLanguage.ENGLISH, "descSeries", "goalsSeries", 0, 123456L, new MinMaxEloRange(1200, 1800), "authorSeries", "GM", null, false, false, linkedList);
        this._uiThreadHandler.post(new Runnable((VideoSeries)object){
            final /* synthetic */ VideoSeries val$result;
            {
                this.val$result = videoSeries;
            }

            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(this.val$result);
            }
        });
    }

    @Override
    public void getVideoSeriesList(VideoFilter object, final LoadCommandCallback<JSONVideoSeriesInfoListParser.VideoSeriesInfoList> loadCommandCallback) {
        object = new LinkedList();
        object.add(new VideoSeriesInformation(new VideoSeriesId("v1"), "Series1", 0, "intro", "goals", VideoLanguage.ENGLISH, "Author", "gm", 5, 3600L, new MinMaxEloRange(1800, 2200), null, false, false));
        this._uiThreadHandler.post(new Runnable((List)object){
            final /* synthetic */ List val$result;
            {
                this.val$result = list;
            }

            @Override
            public void run() {
                loadCommandCallback.loadingSucceded(new JSONVideoSeriesInfoListParser.VideoSeriesInfoList(this.val$result, 1));
            }
        });
    }

    @Override
    public void getVideoSeriesWithSerializeRepresentation(VideoSeriesId videoSeriesId, LoadCommandCallback<ISerializedRepresentationHolder<VideoSeries>> loadCommandCallback) {
    }

    @Override
    public void getVideoWithSerializedRepresentation(VideoId videoId, VideoSeriesId videoSeriesId, LoadCommandCallback<ISerializedRepresentationHolder<Video>> loadCommandCallback) {
    }

    @Override
    public void purchaseVideoSeries(VideoSeriesId videoSeriesId, int n, Activity activity, LoadCommandCallback<Void> loadCommandCallback) {
    }

}
