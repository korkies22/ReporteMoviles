/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  org.json.JSONObject
 */
package de.cisha.android.board.video;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.analyze.AbstractAnalyseFragment;
import de.cisha.android.board.analyze.AnalyzeNavigationBarController;
import de.cisha.android.board.analyze.MoveListViewController;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.VideoAnalysisNavigationBarController;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.model.BoardMarks;
import de.cisha.android.board.model.BoardMarksManager;
import de.cisha.android.board.service.IVideoService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.video.CommandContainer;
import de.cisha.android.board.video.command.VideoCommand;
import de.cisha.android.board.video.model.UserActionObservable;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.video.model.VideoGameHolder;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.storage.ILocalVideoService;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.view.BoardView;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class VideoFragment
extends AbstractAnalyseFragment
implements VideoAnalysisNavigationBarController.VideoDelegate,
UserActionObservable.UserActionObserver {
    private static final String SERIES_KEY = "key_series_id";
    private static final String VIDEO_KEY = "videoID";
    private CommandContainer _container;
    private VideoGameHolder _gameHolder;
    private LocalVideoInfo _localVideo;
    private BoardMarksManager _markingManager;
    private int _savedPosition;
    private Video _video;
    private VideoAnalysisNavigationBarController _videoViewController;

    public static VideoFragment createVideoFragment(VideoId object, VideoSeriesId videoSeriesId) {
        Bundle bundle = new Bundle();
        if (object != null && object.getUuid() != null && videoSeriesId != null && videoSeriesId.getUuid() != null) {
            bundle.putString(VIDEO_KEY, object.getUuid());
            bundle.putString(SERIES_KEY, videoSeriesId.getUuid());
        }
        object = new VideoFragment();
        object.setArguments(bundle);
        return object;
    }

    private void initVideoViewControllerUri() {
        if (this._video != null) {
            boolean bl = this._localVideo != null && this._localVideo.isVideoObjectAvailable();
            Uri uri = bl ? this._localVideo.getVideoFile() : this._video.getVideoUrlMp4();
            this._videoViewController.setVideo(uri);
        }
    }

    private void loadVideo(final VideoId videoId, final VideoSeriesId videoSeriesId) {
        this.showDialogWaiting(true, true, null, null);
        ServiceProvider.getInstance().getVideoService().getVideo(videoId, videoSeriesId, (LoadCommandCallback<Video>)new LoadCommandCallbackWithTimeout<Video>(){

            @Override
            protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                VideoFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoFragment.this.hideDialogWaiting();
                        VideoFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                            @Override
                            public void performReload() {
                                VideoFragment.this.loadVideo(videoId, videoSeriesId);
                            }
                        }, true);
                    }

                });
            }

            @Override
            protected void succeded(final Video video) {
                VideoFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        VideoFragment.this.hideDialogWaiting();
                        VideoFragment.this.videoLoaded(video);
                    }
                });
            }

        });
    }

    private void videoLoaded(Video video) {
        this._video = video;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                VideoFragment.this._markingManager.resetWithInitialValues(VideoFragment.this._video.getBoardMarkings());
                VideoFragment.this.getGameHolder().setGames(VideoFragment.this._video.getGames());
                VideoFragment.this._container.addCommands(VideoFragment.this._video.getCommands());
                VideoFragment.this.initVideoViewControllerUri();
            }
        });
    }

    @Override
    protected VideoGameHolder getGameHolder() {
        if (this._gameHolder == null) {
            this._gameHolder = new VideoGameHolder();
            this.getUserActionObservable().addUserActionObserver(this);
        }
        return this._gameHolder;
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131690400);
    }

    @Override
    public String getTrackingName() {
        return "Video";
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this._markingManager = new BoardMarksManager();
        this._container = new CommandContainer();
        if (this.getArguments() != null) {
            object = this.getArguments().getString(VIDEO_KEY);
            final String string = this.getArguments().getString(SERIES_KEY);
            if (object != null && string != null) {
                Executors.newSingleThreadExecutor().execute(new Runnable((String)object){
                    final /* synthetic */ String val$videoIdString;
                    {
                        this.val$videoIdString = string2;
                    }

                    @Override
                    public void run() {
                        VideoSeriesId videoSeriesId = new VideoSeriesId(string);
                        VideoId videoId = new VideoId(this.val$videoIdString);
                        ILocalVideoService iLocalVideoService = ServiceProvider.getInstance().getLocalVideoService();
                        if (iLocalVideoService.isVideoSeriesOfflineModeEnabled(videoSeriesId)) {
                            VideoFragment.this._localVideo = iLocalVideoService.getLocalVideo(videoId, videoSeriesId);
                            if (VideoFragment.this._localVideo.isVideoObjectAvailable()) {
                                VideoFragment.this.videoLoaded(iLocalVideoService.getVideo(videoId, videoSeriesId));
                                return;
                            }
                            VideoFragment.this.loadVideo(videoId, videoSeriesId);
                            return;
                        }
                        VideoFragment.this.loadVideo(videoId, videoSeriesId);
                    }
                });
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (bundle != null) {
            this._savedPosition = bundle.getInt("currentPosition");
        }
        layoutInflater = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.getGameHolder().addMovesObserver(this._markingManager);
        this._markingManager.addMarkingObserver(this.getBoardView());
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        this.getUserActionObservable().removeUserActionObserver(this);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        this._markingManager.removeMarkingObserver(this.getBoardView());
        this._videoViewController.destroy();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        if (this._videoViewController != null) {
            this._videoViewController.pauseVideo();
        }
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("currentPosition", this._videoViewController.getCurrentPosition());
    }

    @Override
    protected void setupMenuBar(AnalyzeNavigationBarController analyzeNavigationBarController) {
        this._videoViewController = new VideoAnalysisNavigationBarController((Context)this.getActivity(), this._savedPosition);
        this._videoViewController.setVideoDelegate(this);
        analyzeNavigationBarController.addAnalyseBarItem(this._videoViewController);
        super.setupMenuBar(analyzeNavigationBarController);
        this.getMoveListController().setUseUserMoves(true);
        analyzeNavigationBarController.selectNavigationBarItem(this._videoViewController);
        this.initVideoViewControllerUri();
    }

    @Override
    public void userDidAction() {
        this._videoViewController.pauseVideo();
    }

    @Override
    public void videoPlayed(final int n) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (VideoFragment.this._container != null) {
                    VideoFragment.this._container.executeCommandsUntil(n, VideoFragment.this._markingManager, VideoFragment.this.getGameHolder());
                }
            }
        });
    }

    @Override
    public void videoStarted() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                BoardView boardView = VideoFragment.this.getBoardView();
                if (VideoFragment.this._container != null && boardView != null) {
                    VideoFragment.this._container.executeAllCommandsuntilCurrentPosition(VideoFragment.this._markingManager, VideoFragment.this.getGameHolder());
                }
            }
        });
    }

}
