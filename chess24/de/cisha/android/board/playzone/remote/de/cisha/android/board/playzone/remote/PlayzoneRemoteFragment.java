/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 *  android.view.animation.RotateAnimation
 *  android.view.animation.Transformation
 *  android.widget.FrameLayout
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.action.BoardAction;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.AbstractConversionDialogFragment;
import de.cisha.android.board.modalfragments.ConversionContext;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.GameBackgroundHolderService;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.remote.PlayzoneConnectionListener;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.model.RemoteGameAdapter;
import de.cisha.android.board.playzone.remote.view.DisconnectedDeviceView;
import de.cisha.android.board.playzone.remote.view.DisconnectedOpponentView;
import de.cisha.android.board.playzone.remote.view.FlyInOutView;
import de.cisha.android.board.playzone.remote.view.aftergame.AfterGameView;
import de.cisha.android.board.playzone.view.TimeControlView;
import de.cisha.android.board.service.IConfigService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.PlayzoneService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

public class PlayzoneRemoteFragment
extends AbstractPlayzoneFragment
implements PlayzoneConnectionListener {
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
    private PlayzoneWaitingScreen _waitingDialog = null;
    private boolean _waitingDialogIsVisible;
    private WaitingScreenState _waitingDialogState;

    public static PlayzoneRemoteFragment createFragmentWithOnlineEngine(String string, TimeControl timeControl, boolean bl) {
        PlayzoneRemoteFragment playzoneRemoteFragment = new PlayzoneRemoteFragment();
        playzoneRemoteFragment._engineUuidString = string;
        playzoneRemoteFragment._flagIsOnlineEngineGame = true;
        playzoneRemoteFragment._choosenTimeControl = timeControl;
        playzoneRemoteFragment._isRatedGame = bl;
        return playzoneRemoteFragment;
    }

    public static PlayzoneRemoteFragment createFragmentWithSessionToken(PlayzoneGameSessionInfo playzoneGameSessionInfo, boolean bl) {
        PlayzoneRemoteFragment playzoneRemoteFragment = new PlayzoneRemoteFragment();
        playzoneRemoteFragment._gameSessionInfo = playzoneGameSessionInfo;
        playzoneRemoteFragment._flagIsOnlineEngineGame = bl;
        return playzoneRemoteFragment;
    }

    public static PlayzoneRemoteFragment createFragmentWithTimeControl(TimeControl timeControl, EloRange eloRange) {
        PlayzoneRemoteFragment playzoneRemoteFragment = new PlayzoneRemoteFragment();
        playzoneRemoteFragment._choosenTimeControl = timeControl;
        playzoneRemoteFragment._eloRange = eloRange;
        return playzoneRemoteFragment;
    }

    private void hideWaitingDialog() {
        this._waitingDialogIsVisible = false;
        FragmentActivity fragmentActivity = this.getActivity();
        if (fragmentActivity != null) {
            fragmentActivity.runOnUiThread(new Runnable(){

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
        if (this._pairingServerAddress != null && this._times != null && this._times.size() > 0 && this._openGameSessions != null && this._openGameSessions.size() == 0) {
            return true;
        }
        return false;
    }

    private void loadAvailableTimes() {
        if (this.networkConnectionAvailable()) {
            if (this._times == null || this._times.size() == 0) {
                this.hideWaitingDialog();
                if (!this.hasRunningGame()) {
                    this.showDialogWaiting(true, true, "loading...", new RookieDialogLoading.OnCancelListener(){

                        @Override
                        public void onCancel() {
                            PlayzoneRemoteFragment.this._cancelLoadingPlayzoneInfo = true;
                        }
                    });
                }
                ServiceProvider.getInstance().getPlayzoneService().getAvailableClocks(new LoadCommandCallback<List<TimeControl>>(){

                    @Override
                    public void loadingCancelled() {
                    }

                    @Override
                    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                        PlayzoneRemoteFragment.this.reactOnLoadingFailure(aPIStatusCode, string, "times");
                    }

                    @Override
                    public void loadingSucceded(List<TimeControl> list) {
                        if (!PlayzoneRemoteFragment.this._cancelLoadingPlayzoneInfo) {
                            PlayzoneRemoteFragment.this._times = list;
                            PlayzoneRemoteFragment.this.loadingFinished();
                        }
                    }
                });
                return;
            }
        } else {
            this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_NO_NETWORK);
        }
    }

    private void loadOpenGameSessions() {
        if (this.networkConnectionAvailable()) {
            ServiceProvider.getInstance().getPlayzoneService().getOpenGames(new LoadCommandCallback<List<PlayzoneGameSessionInfo>>(){

                @Override
                public void loadingCancelled() {
                }

                @Override
                public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> object, JSONObject object2) {
                    object = Logger.getInstance();
                    object2 = this.getClass().getName();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("error loading open Games: errorCode: ");
                    stringBuilder.append((Object)aPIStatusCode);
                    stringBuilder.append(" message: ");
                    stringBuilder.append(string);
                    object.error((String)object2, stringBuilder.toString());
                    PlayzoneRemoteFragment.this.loadingFinished();
                }

                @Override
                public void loadingSucceded(List<PlayzoneGameSessionInfo> list) {
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
        ServiceProvider.getInstance().getConfigService().getPairingServerURL(new LoadCommandCallback<NodeServerAddress>(){

            @Override
            public void loadingCancelled() {
            }

            @Override
            public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                PlayzoneRemoteFragment.this.reactOnLoadingFailure(aPIStatusCode, string, "pairingServerAdress");
            }

            @Override
            public void loadingSucceded(NodeServerAddress nodeServerAddress) {
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
        ServiceProvider.getInstance().getConfigService().getPairingServerURL((LoadCommandCallback<NodeServerAddress>)new LoadCommandCallbackWithTimeout<NodeServerAddress>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                PlayzoneRemoteFragment.this.hideDialogWaiting();
                PlayzoneRemoteFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        PlayzoneRemoteFragment.this.loadServerAddressesWithReload(runnable);
                    }
                }, false);
            }

            @Override
            protected void succeded(NodeServerAddress nodeServerAddress) {
                PlayzoneRemoteFragment.this.hideDialogWaiting();
                PlayzoneRemoteFragment.this._pairingServerAddress = nodeServerAddress;
                PlayzoneRemoteFragment.this.runOnUiThreadBetweenStartAndDestroy(runnable);
            }

        });
    }

    private void loadingFinished() {
        if (!this.hasRunningGame() && this.isReadyToPlay()) {
            this.hideDialogWaiting();
            this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.CHOOSING_BEFORE);
            if (this._engineUuidString != null && this._choosenTimeControl != null) {
                this.showDialogWaiting(true, true, "", null);
                CishaUUID cishaUUID = ServiceProvider.getInstance().getLoginService().getAuthToken();
                ClockSetting clockSetting = new ClockSetting(this._choosenTimeControl.getMinutes() * 60, this._choosenTimeControl.getIncrement());
                this._model = new RemoteGameAdapter.Builder(this._engineUuidString, clockSetting, this._isRatedGame, cishaUUID, this, this._pairingServerAddress).build();
                this.gameChosen(this._model, true);
                this._engineUuidString = null;
                return;
            }
            if (this._choosenTimeControl != null) {
                this.startPairing(this._choosenTimeControl);
            }
        }
    }

    private void reactOnLoadingFailure(APIStatusCode aPIStatusCode, String string, String string2) {
        if (!this._cancelLoadingPlayzoneInfo && !this.hasRunningGame()) {
            this.hideDialogWaiting();
            this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
            this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_NO_NETWORK);
        }
    }

    private void resumeGame(final PlayzoneGameSessionInfo playzoneGameSessionInfo) {
        this.loadServerAddressesWithReload(new Runnable(){

            @Override
            public void run() {
                CishaUUID cishaUUID = ServiceProvider.getInstance().getLoginService().getAuthToken();
                PlayzoneRemoteFragment.this._model = new RemoteGameAdapter.Builder(cishaUUID, playzoneGameSessionInfo.getGameToken(), playzoneGameSessionInfo.getColor(), PlayzoneRemoteFragment.this, new NodeServerAddress(playzoneGameSessionInfo.getHost(), playzoneGameSessionInfo.getPort()), PlayzoneRemoteFragment.this._pairingServerAddress).build();
                PlayzoneRemoteFragment.this.showDialogWaiting(true, false, "", new RookieDialogLoading.OnCancelListener(){

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

    private void setAfterGameInfo(GameEndInformation gameEndInformation) {
        boolean bl = this.getGameAdapter().playerIsWhite();
        gameEndInformation = new AfterGameInformation(gameEndInformation, bl, this.getGameAdapter().getPlayerInfo(bl), this.getGameAdapter().getPlayerInfo(bl ^ true));
        if (this._afterGameInfoHolder != null) {
            this._afterGameInfoHolder.setModel((AfterGameInformation)gameEndInformation);
            return;
        }
        this._afterGameInfoHolder = new ModelHolder<GameEndInformation>(gameEndInformation);
    }

    private void showWaitingScreenView(final WaitingScreenState waitingScreenState) {
        this._waitingDialogState = waitingScreenState;
        this._waitingDialogIsVisible = true;
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (PlayzoneRemoteFragment.this._waitingDialog == null) {
                    PlayzoneRemoteFragment.this._waitingDialog = new PlayzoneWaitingScreen((Context)PlayzoneRemoteFragment.this.getActivity());
                    PlayzoneRemoteFragment.this._fragmentView.addView((View)PlayzoneRemoteFragment.this._waitingDialog, -1, -1);
                }
                PlayzoneRemoteFragment.this._waitingDialog.show(waitingScreenState);
            }
        });
    }

    private void startPairing(final TimeControl timeControl) {
        if (timeControl != null) {
            this.loadServerAddressesWithReload(new Runnable(){

                @Override
                public void run() {
                    Object object = ServiceProvider.getInstance().getLoginService().getAuthToken();
                    object = new RemoteGameAdapter.Builder(new ClockSetting(timeControl.getMinutes() * 60, timeControl.getIncrement()), (CishaUUID)object, (IGameModelDelegate)PlayzoneRemoteFragment.this, PlayzoneRemoteFragment.this._pairingServerAddress);
                    if (PlayzoneRemoteFragment.this._eloRange != null) {
                        object.setEloRange(PlayzoneRemoteFragment.this._eloRange);
                    }
                    PlayzoneRemoteFragment.this._model = object.build();
                    PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, true);
                    PlayzoneRemoteFragment.this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_SEARCHING);
                    PlayzoneRemoteFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
                }
            });
        }
    }

    private void stopButtonPressed() {
        if (this._model != null) {
            this._model.destroy();
        }
    }

    public void gameChosen(RemoteGameAdapter remoteGameAdapter, boolean bl) {
        super.gameChosen(remoteGameAdapter);
        remoteGameAdapter.setPlayzoneConnectionListener(this);
        if (bl) {
            this._gameHolderService.startUpInService(this.getClass().getName(), remoteGameAdapter);
        }
    }

    @Override
    protected View getAfterGameView() {
        BoardAction boardAction = new BoardAction(){

            @Override
            public void perform() {
                if (PlayzoneRemoteFragment.this._model != null) {
                    PlayzoneRemoteFragment.this._model.destroy();
                }
            }
        };
        BoardAction boardAction2 = new BoardAction(){

            @Override
            public void perform() {
                if (PlayzoneRemoteFragment.this._model != null) {
                    PlayzoneRemoteFragment.this._model.requestRematch();
                }
            }
        };
        BoardAction boardAction3 = new BoardAction(){

            @Override
            public void perform() {
                PlayzoneRemoteFragment.this.startAnalyzeGame(PlayzoneRemoteFragment.this.getGameAdapter().getGame());
            }
        };
        BoardAction boardAction4 = new BoardAction(){

            @Override
            public void perform() {
                PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
            }
        };
        if (this._afterGameInfoHolder == null) {
            return new LinearLayout((Context)this.getActivity());
        }
        this._afterGameView = new AfterGameView((Context)this.getActivity(), this._times, this._afterGameInfoHolder, this._opponentDeclinedRematchHolder, boardAction2, boardAction, boardAction3, boardAction4, this._model.getOpponent(), this.getChildFragmentManager(), this.onSavenInstanceStateCalled() ^ true);
        return this._afterGameView;
    }

    @Override
    protected View getBeforeGameView() {
        return new LinearLayout((Context)this.getActivity());
    }

    @Override
    public String getHeaderText(Resources resources) {
        int n = this._flagIsOnlineEngineGame ? 2131690113 : 2131690112;
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
    public void networkStateChanged(boolean bl) {
        if (this._waitingDialog != null && this._waitingDialog.getState() == WaitingScreenState.WAITINGSCREENSTATE_NO_NETWORK && bl) {
            this.loadAvailableTimes();
        }
        super.networkStateChanged(bl);
    }

    @Override
    public void onCreate(Bundle bundle) {
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
    public void onGameEnd(GameEndInformation gameEndInformation) {
        this._opponentDeclinedRematchHolder.setModel(false);
        boolean bl = this.getGameAdapter().playerIsWhite();
        this.setAfterGameInfo(new AfterGameInformation(gameEndInformation, bl, this.getGameAdapter().getPlayerInfo(bl), this.getGameAdapter().getPlayerInfo(bl ^ true)));
        super.onGameEnd(gameEndInformation);
        this.onMyConnectionStateChanged(true);
        this.onOpponentsConnectionStateChanged(true, 20, false);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game ended", null);
    }

    @Override
    public void onGameSessionEnded(GameStatus gameStatus) {
        this.setAfterGameInfo(new GameEndInformation(gameStatus, this.getGameAdapter().getPosition().getActiveColor()));
        super.onGameSessionEnded(gameStatus);
    }

    @Override
    public void onGameSessionOver(Game game, GameEndInformation gameEndInformation) {
        super.onGameSessionOver(game, gameEndInformation);
        this.setAfterGameInfo(gameEndInformation);
        this.hideDialogWaiting();
        this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.AFTER);
    }

    @Override
    public void onGameStart() {
        this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
        Intent intent = new Intent((Context)this.getActivity(), GameBackgroundHolderService.class);
        this.getActivity().startService(intent);
        this.hideWaitingDialog();
        this.hideDialogWaiting();
        PlayzoneRemoteFragment.super.onGameStart();
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game started", null);
        this._opponentDeclinedRematchHolder.setModel(false);
    }

    @Override
    public void onMyConnectionStateChanged(final boolean bl) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                LinearLayout linearLayout = (FlyInOutView)PlayzoneRemoteFragment.this._fragmentView.findViewById(2131296716);
                linearLayout.bringToFront();
                if (PlayzoneRemoteFragment.this._model != null && PlayzoneRemoteFragment.this._model.isGameActive()) {
                    if (bl) {
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game disconnected", null);
                        linearLayout.flyOutToBottom();
                        return;
                    }
                    ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "online game reconnected", null);
                    linearLayout.flyInFromBottom();
                    linearLayout = (DisconnectedDeviceView)linearLayout.findViewById(2131296715);
                    boolean bl2 = PlayzoneRemoteFragment.this._model.getPosition().getHalfMoveNumber() < 2;
                    linearLayout.setGameWillAborted(bl2);
                    linearLayout.setTimeout(PlayzoneRemoteFragment.this._model.getConnectionTimeout(true) / 1000);
                    return;
                }
                linearLayout.setVisibility(8);
            }
        });
    }

    @Override
    public void onOpponentDeclinedRematch() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                PlayzoneRemoteFragment.this._opponentDeclinedRematchHolder.setModel(true);
            }
        });
    }

    @Override
    public void onOpponentsConnectionStateChanged(final boolean bl, final int n, final boolean bl2) {
        if (this._fragmentView != null) {
            this._fragmentView.post(new Runnable(){

                @Override
                public void run() {
                    LinearLayout linearLayout = (FlyInOutView)PlayzoneRemoteFragment.this._fragmentView.findViewById(2131296718);
                    linearLayout.bringToFront();
                    if (PlayzoneRemoteFragment.this._model.isGameActive()) {
                        if (bl) {
                            linearLayout.flyOutToBottom();
                            return;
                        }
                        linearLayout.flyInFromBottom();
                        linearLayout = (DisconnectedOpponentView)linearLayout.findViewById(2131296717);
                        linearLayout.setGameWillAborted(bl2);
                        linearLayout.setTimeout(n / 1000);
                        return;
                    }
                    linearLayout.setVisibility(8);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this._gameHolderService != null) {
            GameBackgroundHolderService gameBackgroundHolderService = this._gameHolderService;
            String string = this.getClass().getName();
            boolean bl = this._model != null && this._model.isGameActive();
            gameBackgroundHolderService.setNotifyInTitleBar(string, bl);
        }
    }

    @Override
    public void onReceivedRematchOffer() {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

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
            ServiceProvider.getInstance().getPlayzoneService().getBackgroundGameHolderService((Context)this.getActivity(), new PlayzoneService.BinderCallback(){

                @Override
                public void onServiceBinded(GameBackgroundHolderService gameBackgroundHolderService) {
                    PlayzoneRemoteFragment.this._gameHolderService = gameBackgroundHolderService;
                    PlayzoneRemoteFragment.this._model = PlayzoneRemoteFragment.this._gameHolderService.getRunningGameAdapter(PlayzoneRemoteFragment.this.getClass().getName());
                    if (PlayzoneRemoteFragment.this._model != null) {
                        PlayzoneRemoteFragment.this.gameChosen(PlayzoneRemoteFragment.this._model, false);
                        PlayzoneRemoteFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
                    } else if (!PlayzoneRemoteFragment.this.hasRunningGame()) {
                        if (PlayzoneRemoteFragment.this._gameSessionInfo != null && PlayzoneRemoteFragment.this.getGameAdapter() == null) {
                            PlayzoneRemoteFragment.this.resumeGame(PlayzoneRemoteFragment.this._gameSessionInfo);
                        } else {
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
        this.getActivity().runOnUiThread(new Runnable(){

            @Override
            public void run() {
                PlayzoneRemoteFragment.this.stopButtonPressed();
                PlayzoneRemoteFragment.this.showWaitingScreenView(WaitingScreenState.WAITINGSCREENSTATE_NO_OPPONENT_FOUND);
                switch (n) {
                    default: {
                        return;
                    }
                    case 12: {
                        PlayzoneRemoteFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                            @Override
                            public void run() {
                                PlayzoneRemoteFragment.this.getContentPresenter().showConversionDialog(null, ConversionContext.PLAYZONE);
                            }
                        });
                        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", "informNotAllowed");
                        return;
                    }
                    case 11: 
                }
                ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", "no opponent");
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
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this._waitingDialogIsVisible) {
            this.showWaitingScreenView(this._waitingDialogState);
        }
    }

    @Override
    protected void showDialogWaiting(boolean bl, boolean bl2, String string, RookieDialogLoading.OnCancelListener onCancelListener) {
        if (!this.isLoading()) {
            super.showDialogWaiting(bl, bl2, string, onCancelListener);
        }
    }

    @Override
    public boolean showMenu() {
        return false;
    }

    public class PlayzoneWaitingScreen
    extends FrameLayout {
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
        private PlayzoneWaitingScreen$WaitingAnimation _waitingAnimation;

        public PlayzoneWaitingScreen(Context context) {
            super(context);
            LayoutInflater.from((Context)context).inflate(2131427506, (ViewGroup)this, true);
            this.findViewById(2131296713).setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
                final /* synthetic */ PlayzoneRemoteFragment val$this$0;
                {
                    this.val$this$0 = playzoneRemoteFragment;
                }

                public void onClick(View view) {
                }
            });
            TextView textView = (TextView)this.findViewById(2131296795);
            textView.setTypeface(Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/TREBUCBD.TTF"));
            this._waitingAnimation = new PlayzoneWaitingScreen$WaitingAnimation(textView);
            this._playVsComputerButton = this.findViewById(2131296791);
            this._chooseDifferentTimeButton = this.findViewById(2131296701);
            this._tryAgainButton = this.findViewById(2131296790);
            this._networkSettingsButton = this.findViewById(2131296789);
            this._text = (TextView)this.findViewById(2131296796);
            this._stopButtonFrame = this.findViewById(2131296794);
            this._timeControlView = (TimeControlView)this.findViewById(2131296797);
            this._tryAgainButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
                final /* synthetic */ PlayzoneRemoteFragment val$this$0;
                {
                    this.val$this$0 = playzoneRemoteFragment;
                }

                public void onClick(View view) {
                    PlayzoneRemoteFragment.this.hideWaitingDialog();
                    PlayzoneRemoteFragment.this.startPairing(PlayzoneRemoteFragment.this._choosenTimeControl);
                }
            });
            this._playVsComputerButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
                final /* synthetic */ PlayzoneRemoteFragment val$this$0;
                {
                    this.val$this$0 = playzoneRemoteFragment;
                }

                public void onClick(View view) {
                    PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
                }
            });
            this._chooseDifferentTimeButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
                final /* synthetic */ PlayzoneRemoteFragment val$this$0;
                {
                    this.val$this$0 = playzoneRemoteFragment;
                }

                public void onClick(View view) {
                    PlayzoneRemoteFragment.this.getContentPresenter().popCurrentFragment();
                }
            });
            this._stopButton = this.findViewById(2131296792);
            this._stopButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
                final /* synthetic */ PlayzoneRemoteFragment val$this$0;
                {
                    this.val$this$0 = playzoneRemoteFragment;
                }

                public void onClick(View view) {
                    PlayzoneRemoteFragment.this.stopButtonPressed();
                    PlayzoneWaitingScreen.this.setState(WaitingScreenState.WAITINGSCREENSTATE_NO_OPPONENT_FOUND);
                }
            });
            this._networkSettingsButton.setOnClickListener(new View.OnClickListener(PlayzoneRemoteFragment.this){
                final /* synthetic */ PlayzoneRemoteFragment val$this$0;
                {
                    this.val$this$0 = playzoneRemoteFragment;
                }

                public void onClick(View view) {
                    PlayzoneRemoteFragment.this.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
                }
            });
            this._stopButtonCircle = this.findViewById(2131296793);
            this.setState(WaitingScreenState.WAITINGSCREENSTATE_SEARCHING);
        }

        private void setState(WaitingScreenState waitingScreenState) {
            this._state = waitingScreenState;
            switch (de.cisha.android.board.playzone.remote.PlayzoneRemoteFragment$20.$SwitchMap$de$cisha$android$board$playzone$remote$PlayzoneRemoteFragment$WaitingScreenState[waitingScreenState.ordinal()]) {
                default: {
                    return;
                }
                case 4: {
                    this._tryAgainButton.setVisibility(8);
                    this._playVsComputerButton.setVisibility(0);
                    this._chooseDifferentTimeButton.setVisibility(8);
                    this._timeControlView.setVisibility(8);
                    this._stopButtonFrame.setVisibility(8);
                    this._networkSettingsButton.setVisibility(0);
                    this._text.setText(2131690159);
                    this._waitingAnimation.cancel();
                    return;
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
                    return;
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
                    return;
                }
                case 1: 
            }
            this.setVisibility(4);
        }

        public WaitingScreenState getState() {
            return this._state;
        }

        public void hide() {
            this.setState(WaitingScreenState.WAITINGSCREENSTATE_HIDDEN);
        }

        public void show(WaitingScreenState waitingScreenState) {
            this.setState(waitingScreenState);
        }

    }

    private class PlayzoneWaitingScreen$WaitingAnimation
    extends RotateAnimation {
        private TextView _textView;

        public PlayzoneWaitingScreen$WaitingAnimation(TextView textView) {
            super(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
            this._textView = textView;
            this.setDuration(3000L);
            this.setRepeatCount(-1);
            this.setRepeatMode(1);
            this.setInterpolator((Interpolator)new LinearInterpolator());
        }

        protected void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            int n = Color.argb((int)244, (int)0, (int)0, (int)((int)(180.0 * ((Math.sin((double)f * 3.141592653589793 * 2.0) + 1.0) / 2.0)) + 20));
            this._textView.setTextColor(n);
        }
    }

    public static enum WaitingScreenState {
        WAITINGSCREENSTATE_SEARCHING,
        WAITINGSCREENSTATE_NO_OPPONENT_FOUND,
        WAITINGSCREENSTATE_HIDDEN,
        WAITINGSCREENSTATE_NO_NETWORK;
        

        private WaitingScreenState() {
        }
    }

}
