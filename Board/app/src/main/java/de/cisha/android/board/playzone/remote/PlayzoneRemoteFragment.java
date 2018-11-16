// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote;

import android.graphics.Color;
import android.view.animation.Transformation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation;
import android.graphics.Typeface;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import de.cisha.android.board.playzone.view.TimeControlView;
import android.widget.TextView;
import android.widget.FrameLayout;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.playzone.remote.view.DisconnectedOpponentView;
import de.cisha.android.board.playzone.remote.view.DisconnectedDeviceView;
import de.cisha.android.board.playzone.remote.view.FlyInOutView;
import android.content.Intent;
import de.cisha.chess.model.GameStatus;
import de.cisha.android.board.service.ITrackingService;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import android.widget.LinearLayout;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import android.view.View;
import android.content.Context;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.chess.model.ClockSetting;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import android.support.v4.app.FragmentActivity;
import de.cisha.chess.model.Game;
import de.cisha.android.board.IContentPresenter;
import android.view.ViewGroup;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.NodeServerAddress;
import java.util.List;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.view.aftergame.AfterGameView;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.playzone.AbstractPlayzoneFragment;

public class PlayzoneRemoteFragment extends AbstractPlayzoneFragment implements PlayzoneConnectionListener
{
    public static final String REQUEST_RESUME_GAME = "956743";
    private ModelHolder<AfterGameInformation> _afterGameInfoHolder;
    private AfterGameView _afterGameView;
    private boolean _cancelLoadingPlayzoneInfo;
    private TimeControl _choosenTimeControl;
    private EloRange _eloRange;
    private String _engineUuidString;
    private boolean _flagIsOnlineEngineGame;
    private GameBackgroundHolderService _gameHolderService;
    private PlayzoneGameSessionInfo _gameSessionInfo;
    private boolean _initialLoad;
    private boolean _isRatedGame;
    private RemoteGameAdapter _model;
    private List<PlayzoneGameSessionInfo> _openGameSessions;
    private ModelHolder<Boolean> _opponentDeclinedRematchHolder;
    private NodeServerAddress _pairingServerAddress;
    private List<TimeControl> _times;
    private PlayzoneWaitingScreen _waitingDialog;
    private boolean _waitingDialogIsVisible;
    private WaitingScreenState _waitingDialogState;
    
    public PlayzoneRemoteFragment() {
        this._waitingDialog = null;
    }
    
    public static PlayzoneRemoteFragment createFragmentWithOnlineEngine(final String engineUuidString, final TimeControl choosenTimeControl, final boolean isRatedGame) {
        final PlayzoneRemoteFragment playzoneRemoteFragment = new PlayzoneRemoteFragment();
        playzoneRemoteFragment._engineUuidString = engineUuidString;
        playzoneRemoteFragment._flagIsOnlineEngineGame = true;
        playzoneRemoteFragment._choosenTimeControl = choosenTimeControl;
        playzoneRemoteFragment._isRatedGame = isRatedGame;
        return playzoneRemoteFragment;
    }
    
    public static PlayzoneRemoteFragment createFragmentWithSessionToken(final PlayzoneGameSessionInfo gameSessionInfo, final boolean flagIsOnlineEngineGame) {
        final PlayzoneRemoteFragment playzoneRemoteFragment = new PlayzoneRemoteFragment();
        playzoneRemoteFragment._gameSessionInfo = gameSessionInfo;
        playzoneRemoteFragment._flagIsOnlineEngineGame = flagIsOnlineEngineGame;
        return playzoneRemoteFragment;
    }
    
    public static PlayzoneRemoteFragment createFragmentWithTimeControl(final TimeControl choosenTimeControl, final EloRange eloRange) {
        final PlayzoneRemoteFragment playzoneRemoteFragment = new PlayzoneRemoteFragment();
        playzoneRemoteFragment._choosenTimeControl = choosenTimeControl;
        playzoneRemoteFragment._eloRange = eloRange;
        return playzoneRemoteFragment;
    }
    
    private void hideWaitingDialog() {
        this._waitingDialogIsVisible = false;
        final FragmentActivity activity = this.getActivity();
        if (activity != null) {
            activity.runOnUiThread((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (PlayzoneRemoteFragment.this._waitingDialog != null) {
                        PlayzoneRemoteFragment.this._waitingDialog.hide();
                    }
                }
            });
        }
    }
    
    private boolean isReadyToPlay() {
        return this._pairingServerAddress != null && this._times != null && this._times.size() > 0 && this._openGameSessions != null && this._openGameSessions.size() == 0;
    }
    
    private void loadAvailableTimes() {
        if (this.networkConnectionAvailable()) {
            if (this._times == null || this._times.size() == 0) {
                this.hideWaitingDialog();
                if (!this.hasRunningGame()) {
                    this.showDialogWaiting(true, true, "loading...", new RookieDialogLoading.OnCancelListener() {
                        @Override
                        public void onCancel() {
                            PlayzoneRemoteFragment.this._cancelLoadingPlayzoneInfo = true;
                        }
                    });
                }
                ServiceProvider.getInstance().getPlayzoneService().getAvailableClocks(new LoadCommandCallback<List<TimeControl>>() {
                    @Override
                    public void loadingCancelled() {
                    }
                    
                    @Override
                    public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        PlayzoneRemoteFragment.this.reactOnLoadingFailure(apiStatusCode, s, "times");
                    }
                    
                    @Override
                    public void loadingSucceded(final List<TimeControl> list) {
                        if (!PlayzoneRemoteFragment.this._cancelLoadingPlayzoneInfo) {
                            PlayzoneRemoteFragment.this._times = list;
                            PlayzoneRemoteFragment.this.loadingFinished();
                        }
                    }
                });
            }
        }
        else {
            this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_NO_NETWORK);
        }
    }
    
    private void loadOpenGameSessions() {
        if (this.networkConnectionAvailable()) {
            ServiceProvider.getInstance().getPlayzoneService().getOpenGames(new LoadCommandCallback<List<PlayzoneGameSessionInfo>>() {
                @Override
                public void loadingCancelled() {
                }
                
                @Override
                public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    final Logger instance = Logger.getInstance();
                    final String name = this.getClass().getName();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("error loading open Games: errorCode: ");
                    sb.append(apiStatusCode);
                    sb.append(" message: ");
                    sb.append(s);
                    instance.error(name, sb.toString());
                    PlayzoneRemoteFragment.this.loadingFinished();
                }
                
                @Override
                public void loadingSucceded(final List<PlayzoneGameSessionInfo> list) {
                    PlayzoneRemoteFragment.this._openGameSessions = list;
                    if (!PlayzoneRemoteFragment.this.hasRunningGame() && list != null && list.size() > 0) {
                        PlayzoneRemoteFragment.this.resumeGame(list.get(0));
                        PlayzoneRemoteFragment.this._openGameSessions = null;
                    }
                    PlayzoneRemoteFragment.this.loadingFinished();
                }
            });
        }
    }
    
    private void loadServerAddresses() {
        ServiceProvider.getInstance().getConfigService().getPairingServerURL(new LoadCommandCallback<NodeServerAddress>() {
            @Override
            public void loadingCancelled() {
            }
            
            @Override
            public void loadingFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                PlayzoneRemoteFragment.this.reactOnLoadingFailure(apiStatusCode, s, "pairingServerAdress");
            }
            
            @Override
            public void loadingSucceded(final NodeServerAddress nodeServerAddress) {
                PlayzoneRemoteFragment.this._pairingServerAddress = nodeServerAddress;
                PlayzoneRemoteFragment.this.loadingFinished();
            }
        });
    }
    
    private void loadServerAddressesWithReload(final Runnable runnable) {
        if (this._pairingServerAddress != null) {
            this.runOnUiThreadBetweenStartAndDestroy(runnable);
            return;
        }
        this.hideDialogWaiting();
        this.showDialogWaiting(true, true, "", null);
        ServiceProvider.getInstance().getConfigService().getPairingServerURL(new LoadCommandCallbackWithTimeout<NodeServerAddress>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                PlayzoneRemoteFragment.this.hideDialogWaiting();
                PlayzoneRemoteFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
                    @Override
                    public void performReload() {
                        PlayzoneRemoteFragment.this.loadServerAddressesWithReload(runnable);
                    }
                }, false);
            }
            
            @Override
            protected void succeded(final NodeServerAddress nodeServerAddress) {
                PlayzoneRemoteFragment.this.hideDialogWaiting();
                PlayzoneRemoteFragment.this._pairingServerAddress = nodeServerAddress;
                PlayzoneRemoteFragment.this.runOnUiThreadBetweenStartAndDestroy(runnable);
            }
        });
    }
    
    private void loadingFinished() {
        if (!this.hasRunningGame() && this.isReadyToPlay()) {
            this.hideDialogWaiting();
            this.showMenusForPlayzoneState(PlayzoneState.CHOOSING_BEFORE);
            if (this._engineUuidString != null && this._choosenTimeControl != null) {
                this.showDialogWaiting(true, true, "", null);
                this.gameChosen(this._model = new RemoteGameAdapter.Builder(this._engineUuidString, new ClockSetting(this._choosenTimeControl.getMinutes() * 60, this._choosenTimeControl.getIncrement()), this._isRatedGame, ServiceProvider.getInstance().getLoginService().getAuthToken(), this, this._pairingServerAddress).build(), true);
                this._engineUuidString = null;
                return;
            }
            if (this._choosenTimeControl != null) {
                this.startPairing(this._choosenTimeControl);
            }
        }
    }
    
    private void reactOnLoadingFailure(final APIStatusCode apiStatusCode, final String s, final String s2) {
        if (!this._cancelLoadingPlayzoneInfo && !this.hasRunningGame()) {
            this.hideDialogWaiting();
            this.showMenusForPlayzoneState(PlayzoneState.PLAYING);
            this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_NO_NETWORK);
        }
    }
    
    private void resumeGame(final PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        this.loadServerAddressesWithReload(new Runnable() {
            @Override
            public void run() {
                PlayzoneRemoteFragment.this._model = new RemoteGameAdapter.Builder(ServiceProvider.getInstance().getLoginService().getAuthToken(), playzoneGameSessionInfo.getGameToken(), playzoneGameSessionInfo.getColor(), PlayzoneRemoteFragment.this, new NodeServerAddress(playzoneGameSessionInfo.getHost(), playzoneGameSessionInfo.getPort()), PlayzoneRemoteFragment.this._pairingServerAddress).build();
                PlayzoneRemoteFragment.this.showDialogWaiting(true, false, "", new RookieDialogLoading.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        if (PlayzoneRemoteFragment.this._model != null) {
                            PlayzoneRemoteFragment.this._model.destroy();
                        }
                    }
                });
                PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, true);
            }
        });
    }
    
    private void setAfterGameInfo(final GameEndInformation gameEndInformation) {
        final boolean playerIsWhite = this.getGameAdapter().playerIsWhite();
        final AfterGameInformation model = new AfterGameInformation(gameEndInformation, playerIsWhite, this.getGameAdapter().getPlayerInfo(playerIsWhite), this.getGameAdapter().getPlayerInfo(playerIsWhite ^ true));
        if (this._afterGameInfoHolder != null) {
            this._afterGameInfoHolder.setModel(model);
            return;
        }
        this._afterGameInfoHolder = new ModelHolder<AfterGameInformation>(model);
    }
    
    private void showWaitingScreenView(final WaitingScreenState waitingDialogState) {
        this._waitingDialogState = waitingDialogState;
        this._waitingDialogIsVisible = true;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (PlayzoneRemoteFragment.this._waitingDialog == null) {
                    PlayzoneRemoteFragment.this._waitingDialog = new PlayzoneWaitingScreen((Context)PlayzoneRemoteFragment.this.getActivity());
                    PlayzoneRemoteFragment.this._fragmentView.addView((View)PlayzoneRemoteFragment.this._waitingDialog, -1, -1);
                }
                PlayzoneRemoteFragment.this._waitingDialog.show(waitingDialogState);
            }
        });
    }
    
    private void startPairing(final TimeControl timeControl) {
        if (timeControl != null) {
            this.loadServerAddressesWithReload(new Runnable() {
                @Override
                public void run() {
                    final RemoteGameAdapter.Builder builder = new RemoteGameAdapter.Builder(new ClockSetting(timeControl.getMinutes() * 60, timeControl.getIncrement()), ServiceProvider.getInstance().getLoginService().getAuthToken(), PlayzoneRemoteFragment.this, PlayzoneRemoteFragment.this._pairingServerAddress);
                    if (PlayzoneRemoteFragment.this._eloRange != null) {
                        builder.setEloRange(PlayzoneRemoteFragment.this._eloRange);
                    }
                    PlayzoneRemoteFragment.this._model = builder.build();
                    PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, true);
                    PlayzoneRemoteFragment.this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_SEARCHING);
                    PlayzoneRemoteFragment.this.showMenusForPlayzoneState(PlayzoneState.PLAYING);
                }
            });
        }
    }
    
    private void stopButtonPressed() {
        if (this._model != null) {
            this._model.destroy();
        }
    }
    
    public void gameChosen(final RemoteGameAdapter remoteGameAdapter, final boolean b) {
        super.gameChosen(remoteGameAdapter);
        remoteGameAdapter.setPlayzoneConnectionListener(this);
        if (b) {
            this._gameHolderService.startUpInService(this.getClass().getName(), remoteGameAdapter);
        }
    }
    
    @Override
    protected View getAfterGameView() {
        final BoardAction boardAction = new BoardAction() {
            @Override
            public void perform() {
                if (PlayzoneRemoteFragment.this._model != null) {
                    PlayzoneRemoteFragment.this._model.destroy();
                }
            }
        };
        final BoardAction boardAction2 = new BoardAction() {
            @Override
            public void perform() {
                if (PlayzoneRemoteFragment.this._model != null) {
                    PlayzoneRemoteFragment.this._model.requestRematch();
                }
            }
        };
        final BoardAction boardAction3 = new BoardAction() {
            @Override
            public void perform() {
                PlayzoneRemoteFragment.this.startAnalyzeGame(PlayzoneRemoteFragment.this.getGameAdapter().getGame());
            }
        };
        final BoardAction boardAction4 = new BoardAction() {
            @Override
            public void perform() {
                PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
            }
        };
        if (this._afterGameInfoHolder == null) {
            return (View)new LinearLayout((Context)this.getActivity());
        }
        return (View)(this._afterGameView = new AfterGameView((Context)this.getActivity(), this._times, this._afterGameInfoHolder, this._opponentDeclinedRematchHolder, boardAction2, boardAction, boardAction3, boardAction4, this._model.getOpponent(), this.getChildFragmentManager(), this.onSavenInstanceStateCalled() ^ true));
    }
    
    @Override
    protected View getBeforeGameView() {
        return (View)new LinearLayout((Context)this.getActivity());
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        int n;
        if (this._flagIsOnlineEngineGame) {
            n = 2131690113;
        }
        else {
            n = 2131690112;
        }
        return resources.getString(n);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        if (this._flagIsOnlineEngineGame) {
            return MenuItem.PLAYVSCOMPUTER;
        }
        return MenuItem.PLAYNOW;
    }
    
    @Override
    public String getTrackingName() {
        return "PlayzoneOnline";
    }
    
    @Override
    protected boolean needAuthToken() {
        return true;
    }
    
    @Override
    protected boolean needNetwork() {
        return true;
    }
    
    @Override
    protected boolean needRegisteredUser() {
        return false;
    }
    
    @Override
    public void networkStateChanged(final boolean b) {
        if (this._waitingDialog != null && this._waitingDialog.getState() == WaitingScreenState.WAITINGSCREENSTATE_NO_NETWORK && b) {
            this.loadAvailableTimes();
        }
        super.networkStateChanged(b);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._cancelLoadingPlayzoneInfo = false;
        this._opponentDeclinedRematchHolder = new ModelHolder<Boolean>(false);
    }
    
    @Override
    public void onDestroy() {
        this._cancelLoadingPlayzoneInfo = true;
        if (this._gameHolderService != null) {
            this._gameHolderService.stopServiceIfNoGameActive();
        }
        super.onDestroy();
    }
    
    @Override
    public void onDestroyView() {
        this._waitingDialog = null;
        super.onDestroyView();
    }
    
    @Override
    public void onGameEnd(final GameEndInformation gameEndInformation) {
        this._opponentDeclinedRematchHolder.setModel(false);
        final boolean playerIsWhite = this.getGameAdapter().playerIsWhite();
        this.setAfterGameInfo(new AfterGameInformation(gameEndInformation, playerIsWhite, this.getGameAdapter().getPlayerInfo(playerIsWhite), this.getGameAdapter().getPlayerInfo(playerIsWhite ^ true)));
        super.onGameEnd(gameEndInformation);
        this.onMyConnectionStateChanged(true);
        this.onOpponentsConnectionStateChanged(true, 20, false);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game ended", null);
    }
    
    @Override
    public void onGameSessionEnded(final GameStatus gameStatus) {
        this.setAfterGameInfo(new GameEndInformation(gameStatus, this.getGameAdapter().getPosition().getActiveColor()));
        super.onGameSessionEnded(gameStatus);
    }
    
    @Override
    public void onGameSessionOver(final Game game, final GameEndInformation afterGameInfo) {
        super.onGameSessionOver(game, afterGameInfo);
        this.setAfterGameInfo(afterGameInfo);
        this.hideDialogWaiting();
        this.showMenusForPlayzoneState(PlayzoneState.AFTER);
    }
    
    @Override
    public void onGameStart() {
        this.showMenusForPlayzoneState(PlayzoneState.PLAYING);
        this.getActivity().startService(new Intent((Context)this.getActivity(), (Class)GameBackgroundHolderService.class));
        this.hideWaitingDialog();
        this.hideDialogWaiting();
        this.onGameStart();
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game started", null);
        this._opponentDeclinedRematchHolder.setModel(false);
    }
    
    @Override
    public void onMyConnectionStateChanged(final boolean b) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                final FlyInOutView flyInOutView = (FlyInOutView)PlayzoneRemoteFragment.this._fragmentView.findViewById(2131296716);
                flyInOutView.bringToFront();
                if (PlayzoneRemoteFragment.this._model == null || !PlayzoneRemoteFragment.this._model.isGameActive()) {
                    flyInOutView.setVisibility(8);
                    return;
                }
                if (b) {
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game disconnected", null);
                    flyInOutView.flyOutToBottom();
                    return;
                }
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game reconnected", null);
                flyInOutView.flyInFromBottom();
                final DisconnectedDeviceView disconnectedDeviceView = (DisconnectedDeviceView)flyInOutView.findViewById(2131296715);
                disconnectedDeviceView.setGameWillAborted(PlayzoneRemoteFragment.this._model.getPosition().getHalfMoveNumber() < 2);
                disconnectedDeviceView.setTimeout(PlayzoneRemoteFragment.this._model.getConnectionTimeout(true) / 1000);
            }
        });
    }
    
    @Override
    public void onOpponentDeclinedRematch() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                PlayzoneRemoteFragment.this._opponentDeclinedRematchHolder.setModel(true);
            }
        });
    }
    
    @Override
    public void onOpponentsConnectionStateChanged(final boolean b, final int n, final boolean b2) {
        if (this._fragmentView != null) {
            this._fragmentView.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    final FlyInOutView flyInOutView = (FlyInOutView)PlayzoneRemoteFragment.this._fragmentView.findViewById(2131296718);
                    flyInOutView.bringToFront();
                    if (!PlayzoneRemoteFragment.this._model.isGameActive()) {
                        flyInOutView.setVisibility(8);
                        return;
                    }
                    if (b) {
                        flyInOutView.flyOutToBottom();
                        return;
                    }
                    flyInOutView.flyInFromBottom();
                    final DisconnectedOpponentView disconnectedOpponentView = (DisconnectedOpponentView)flyInOutView.findViewById(2131296717);
                    disconnectedOpponentView.setGameWillAborted(b2);
                    disconnectedOpponentView.setTimeout(n / 1000);
                }
            });
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (this._gameHolderService != null) {
            this._gameHolderService.setNotifyInTitleBar(this.getClass().getName(), this._model != null && this._model.isGameActive());
        }
    }
    
    @Override
    public void onReceivedRematchOffer() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (PlayzoneRemoteFragment.this._afterGameView != null) {
                    PlayzoneRemoteFragment.this._afterGameView.showOpponentsRematchOffer();
                }
            }
        });
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (this._gameHolderService != null) {
            this._gameHolderService.setNotifyInTitleBar(this.getClass().getName(), false);
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if (this.getGameAdapter() == null && !this._initialLoad) {
            this._initialLoad = true;
            ServiceProvider.getInstance().getPlayzoneService().getBackgroundGameHolderService((Context)this.getActivity(), (PlayzoneService.BinderCallback)new PlayzoneService.BinderCallback() {
                @Override
                public void onServiceBinded(final GameBackgroundHolderService gameBackgroundHolderService) {
                    PlayzoneRemoteFragment.this._gameHolderService = gameBackgroundHolderService;
                    PlayzoneRemoteFragment.this._model = PlayzoneRemoteFragment.this._gameHolderService.getRunningGameAdapter(PlayzoneRemoteFragment.this.getClass().getName());
                    if (PlayzoneRemoteFragment.this._model != null) {
                        PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, false);
                        PlayzoneRemoteFragment.this.showMenusForPlayzoneState(PlayzoneState.PLAYING);
                    }
                    else if (!PlayzoneRemoteFragment.this.hasRunningGame()) {
                        if (PlayzoneRemoteFragment.this._gameSessionInfo != null && PlayzoneRemoteFragment.this.getGameAdapter() == null) {
                            PlayzoneRemoteFragment.this.resumeGame(PlayzoneRemoteFragment.this._gameSessionInfo);
                        }
                        else {
                            PlayzoneRemoteFragment.this.loadOpenGameSessions();
                        }
                    }
                    PlayzoneRemoteFragment.this.loadAvailableTimes();
                    PlayzoneRemoteFragment.this.loadServerAddresses();
                    PlayzoneRemoteFragment.this._gameHolderService.setNotifyInTitleBar(PlayzoneRemoteFragment.this.getClass().getName(), false);
                }
            });
        }
    }
    
    @Override
    public void onStartGameFailed(final int n) {
        this.getActivity().runOnUiThread((Runnable)new Runnable() {
            @Override
            public void run() {
                PlayzoneRemoteFragment.this.stopButtonPressed();
                PlayzoneRemoteFragment.this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_NO_OPPONENT_FOUND);
                switch (n) {
                    default: {}
                    case 12: {
                        PlayzoneRemoteFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
                            @Override
                            public void run() {
                                PlayzoneRemoteFragment.this.getContentPresenter().showConversionDialog(null, ConversionContext.PLAYZONE);
                            }
                        });
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", "informNotAllowed");
                    }
                    case 11: {
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", "no opponent");
                    }
                }
            }
        });
    }
    
    @Override
    public void onStop() {
        if (this._model != null && !this._model.isGameActive()) {
            this._opponentDeclinedRematchHolder.setModel(true);
        }
        super.onStop();
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this._waitingDialogIsVisible) {
            this.showWaitingScreenView(this._waitingDialogState);
        }
    }
    
    @Override
    protected void showDialogWaiting(final boolean b, final boolean b2, final String s, final RookieDialogLoading.OnCancelListener onCancelListener) {
        if (!this.isLoading()) {
            super.showDialogWaiting(b, b2, s, onCancelListener);
        }
    }
    
    @Override
    public boolean showMenu() {
        return false;
    }
    
    public class PlayzoneWaitingScreen extends FrameLayout
    {
        private View _chooseDifferentTimeButton;
        private View _networkSettingsButton;
        private View _playVsComputerButton;
        private WaitingScreenState _state;
        private View _stopButton;
        private View _stopButtonCircle;
        private View _stopButtonFrame;
        private TextView _text;
        private TimeControlView _timeControlView;
        private View _tryAgainButton;
        private WaitingAnimation _waitingAnimation;
        
        public PlayzoneWaitingScreen(final Context context) {
            super(context);
            LayoutInflater.from(context).inflate(2131427506, (ViewGroup)this, true);
            this.findViewById(2131296713).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                }
            });
            final TextView textView = (TextView)this.findViewById(2131296795);
            textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/TREBUCBD.TTF"));
            this._waitingAnimation = new WaitingAnimation(textView);
            this._playVsComputerButton = this.findViewById(2131296791);
            this._chooseDifferentTimeButton = this.findViewById(2131296701);
            this._tryAgainButton = this.findViewById(2131296790);
            this._networkSettingsButton = this.findViewById(2131296789);
            this._text = (TextView)this.findViewById(2131296796);
            this._stopButtonFrame = this.findViewById(2131296794);
            this._timeControlView = (TimeControlView)this.findViewById(2131296797);
            this._tryAgainButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    PlayzoneRemoteFragment.this.hideWaitingDialog();
                    PlayzoneRemoteFragment.this.startPairing(PlayzoneRemoteFragment.this._choosenTimeControl);
                }
            });
            this._playVsComputerButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
                }
            });
            this._chooseDifferentTimeButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
                }
            });
            (this._stopButton = this.findViewById(2131296792)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    PlayzoneRemoteFragment.this.stopButtonPressed();
                    PlayzoneWaitingScreen.this.setState(WaitingScreenState.WAITINGSCREENSTATE_NO_OPPONENT_FOUND);
                }
            });
            this._networkSettingsButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    PlayzoneRemoteFragment.this.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
                }
            });
            this._stopButtonCircle = this.findViewById(2131296793);
            this.setState(WaitingScreenState.WAITINGSCREENSTATE_SEARCHING);
        }
        
        private void setState(final WaitingScreenState state) {
            this._state = state;
            switch (PlayzoneRemoteFragment.20..SwitchMap.de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment.WaitingScreenState[state.ordinal()]) {
                default: {}
                case 4: {
                    this._tryAgainButton.setVisibility(8);
                    this._playVsComputerButton.setVisibility(0);
                    this._chooseDifferentTimeButton.setVisibility(8);
                    this._timeControlView.setVisibility(8);
                    this._stopButtonFrame.setVisibility(8);
                    this._networkSettingsButton.setVisibility(0);
                    this._text.setText(2131690159);
                    this._waitingAnimation.cancel();
                }
                case 3: {
                    this._tryAgainButton.setVisibility(0);
                    this._playVsComputerButton.setVisibility(0);
                    this._chooseDifferentTimeButton.setVisibility(0);
                    this._timeControlView.setVisibility(0);
                    this._stopButtonFrame.setVisibility(8);
                    this._networkSettingsButton.setVisibility(8);
                    this._text.setText(2131690160);
                    this._waitingAnimation.cancel();
                }
                case 2: {
                    this.setVisibility(0);
                    this.bringToFront();
                    this._tryAgainButton.setVisibility(8);
                    this._playVsComputerButton.setVisibility(8);
                    this._chooseDifferentTimeButton.setVisibility(8);
                    this._networkSettingsButton.setVisibility(8);
                    this._text.setText(2131690175);
                    if (PlayzoneRemoteFragment.this._choosenTimeControl != null) {
                        this._timeControlView.setTimeControlValue(PlayzoneRemoteFragment.this._choosenTimeControl.getMinutes(), PlayzoneRemoteFragment.this._choosenTimeControl.getIncrement());
                    }
                    this._timeControlView.setVisibility(0);
                    this._waitingAnimation.reset();
                    this._stopButtonFrame.setVisibility(0);
                    this._stopButtonCircle.startAnimation((Animation)this._waitingAnimation);
                }
                case 1: {
                    this.setVisibility(4);
                }
            }
        }
        
        public WaitingScreenState getState() {
            return this._state;
        }
        
        public void hide() {
            this.setState(WaitingScreenState.WAITINGSCREENSTATE_HIDDEN);
        }
        
        public void show(final WaitingScreenState state) {
            this.setState(state);
        }
        
        private class WaitingAnimation extends RotateAnimation
        {
            private TextView _textView;
            
            public WaitingAnimation(final TextView textView) {
                super(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
                this._textView = textView;
                this.setDuration(3000L);
                this.setRepeatCount(-1);
                this.setRepeatMode(1);
                this.setInterpolator((Interpolator)new LinearInterpolator());
            }
            
            protected void applyTransformation(final float n, final Transformation transformation) {
                super.applyTransformation(n, transformation);
                this._textView.setTextColor(Color.argb(244, 0, 0, (int)(180.0 * ((Math.sin(n * 3.141592653589793 * 2.0) + 1.0) / 2.0)) + 20));
            }
        }
    }
    
    public enum WaitingScreenState
    {
        WAITINGSCREENSTATE_HIDDEN, 
        WAITINGSCREENSTATE_NO_NETWORK, 
        WAITINGSCREENSTATE_NO_OPPONENT_FOUND, 
        WAITINGSCREENSTATE_SEARCHING;
    }
}
