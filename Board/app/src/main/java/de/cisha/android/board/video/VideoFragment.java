// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import android.content.Context;
import de.cisha.android.board.analyze.AnalyzeNavigationBarController;
import de.cisha.chess.model.MovesObserver;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import de.cisha.android.board.video.storage.ILocalVideoService;
import java.util.concurrent.Executors;
import android.content.res.Resources;
import de.cisha.chess.model.GameHolder;
import de.cisha.android.board.video.command.VideoCommand;
import java.util.Collection;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import android.net.Uri;
import android.os.Bundle;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.video.model.VideoSeriesId;
import de.cisha.android.board.video.model.VideoId;
import de.cisha.android.board.view.BoardView;
import de.cisha.android.board.video.model.Video;
import de.cisha.android.board.model.BoardMarksManager;
import de.cisha.android.board.video.storage.LocalVideoInfo;
import de.cisha.android.board.video.model.VideoGameHolder;
import de.cisha.android.board.video.model.UserActionObservable;
import de.cisha.android.board.analyze.navigationbaritems.VideoAnalysisNavigationBarController;
import de.cisha.android.board.analyze.AbstractAnalyseFragment;

public class VideoFragment extends AbstractAnalyseFragment implements VideoDelegate, UserActionObserver
{
    private static final String SERIES_KEY = "key_series_id";
    private static final String VIDEO_KEY = "videoID";
    private CommandContainer _container;
    private VideoGameHolder _gameHolder;
    private LocalVideoInfo _localVideo;
    private BoardMarksManager _markingManager;
    private int _savedPosition;
    private Video _video;
    private VideoAnalysisNavigationBarController _videoViewController;
    
    public static VideoFragment createVideoFragment(final VideoId videoId, final VideoSeriesId videoSeriesId) {
        final Bundle arguments = new Bundle();
        if (videoId != null && videoId.getUuid() != null && videoSeriesId != null && videoSeriesId.getUuid() != null) {
            arguments.putString("videoID", videoId.getUuid());
            arguments.putString("key_series_id", videoSeriesId.getUuid());
        }
        final VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(arguments);
        return videoFragment;
    }
    
    private void initVideoViewControllerUri() {
        if (this._video != null) {
            Uri video;
            if (this._localVideo != null && this._localVideo.isVideoObjectAvailable()) {
                video = this._localVideo.getVideoFile();
            }
            else {
                video = this._video.getVideoUrlMp4();
            }
            this._videoViewController.setVideo(video);
        }
    }
    
    private void loadVideo(final VideoId videoId, final VideoSeriesId videoSeriesId) {
        this.showDialogWaiting(true, true, null, null);
        ServiceProvider.getInstance().getVideoService().getVideo(videoId, videoSeriesId, new LoadCommandCallbackWithTimeout<Video>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                VideoFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        VideoFragment.this.hideDialogWaiting();
                        VideoFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
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
                VideoFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                    @Override
                    public void run() {
                        VideoFragment.this.hideDialogWaiting();
                        VideoFragment.this.videoLoaded(video);
                    }
                });
            }
        });
    }
    
    private void videoLoaded(final Video video) {
        this._video = video;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
            this.getUserActionObservable().addUserActionObserver((UserActionObservable.UserActionObserver)this);
        }
        return this._gameHolder;
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690400);
    }
    
    @Override
    public String getTrackingName() {
        return "Video";
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._markingManager = new BoardMarksManager();
        this._container = new CommandContainer();
        if (this.getArguments() != null) {
            final String string = this.getArguments().getString("videoID");
            final String string2 = this.getArguments().getString("key_series_id");
            if (string != null && string2 != null) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        final VideoSeriesId videoSeriesId = new VideoSeriesId(string2);
                        final VideoId videoId = new VideoId(string);
                        final ILocalVideoService localVideoService = ServiceProvider.getInstance().getLocalVideoService();
                        if (!localVideoService.isVideoSeriesOfflineModeEnabled(videoSeriesId)) {
                            VideoFragment.this.loadVideo(videoId, videoSeriesId);
                            return;
                        }
                        VideoFragment.this._localVideo = localVideoService.getLocalVideo(videoId, videoSeriesId);
                        if (VideoFragment.this._localVideo.isVideoObjectAvailable()) {
                            VideoFragment.this.videoLoaded(localVideoService.getVideo(videoId, videoSeriesId));
                            return;
                        }
                        VideoFragment.this.loadVideo(videoId, videoSeriesId);
                    }
                });
            }
        }
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        if (bundle != null) {
            this._savedPosition = bundle.getInt("currentPosition");
        }
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.getGameHolder().addMovesObserver(this._markingManager);
        this._markingManager.addMarkingObserver((BoardMarksManager.BoardMarkingObserver)this.getBoardView());
        return onCreateView;
    }
    
    @Override
    public void onDestroy() {
        this.getUserActionObservable().removeUserActionObserver((UserActionObservable.UserActionObserver)this);
        super.onDestroy();
    }
    
    @Override
    public void onDestroyView() {
        this._markingManager.removeMarkingObserver((BoardMarksManager.BoardMarkingObserver)this.getBoardView());
        this._videoViewController.destroy();
        super.onDestroyView();
    }
    
    public void onPause() {
        if (this._videoViewController != null) {
            this._videoViewController.pauseVideo();
        }
        super.onPause();
    }
    
    @Override
    public void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("currentPosition", this._videoViewController.getCurrentPosition());
    }
    
    @Override
    protected void setupMenuBar(final AnalyzeNavigationBarController analyzeNavigationBarController) {
        (this._videoViewController = new VideoAnalysisNavigationBarController((Context)this.getActivity(), this._savedPosition)).setVideoDelegate((VideoAnalysisNavigationBarController.VideoDelegate)this);
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
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                final BoardView access.1000 = VideoFragment.this.getBoardView();
                if (VideoFragment.this._container != null && access.1000 != null) {
                    VideoFragment.this._container.executeAllCommandsuntilCurrentPosition(VideoFragment.this._markingManager, VideoFragment.this.getGameHolder());
                }
            }
        });
    }
}
