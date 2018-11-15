/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.ScaleAnimation
 *  android.widget.TextView
 *  android.widget.Toast
 */
package de.cisha.android.board.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.analyze.AbstractAnalyseFragment;
import de.cisha.android.board.analyze.AnalyzeNavigationBarController;
import de.cisha.android.board.analyze.MoveListViewController;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.InfoNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.ResetToDeleteUserMovesNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.ShareAnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.board.broadcast.connection.ActivityLifecycle;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManagerFactory;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.BroadcastChessClock;
import de.cisha.android.board.broadcast.model.BroadcastGameHolder;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.broadcast.model.ITournamentService;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.view.TournamentPlayerInfoView;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.ui.patterns.navigationbar.MenuBarItem;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;

public class TournamentGameViewFragment
extends AbstractAnalyseFragment
implements ITournamentGameConnection.TournamentGameConnectionListener,
BroadcastGameHolder.LiveModeChangeListener,
MovesObserver,
ClockListener {
    private static final String ARG_KEY_GAMEID = "TournamentGameId";
    private static final String ARG_KEY_SHARE_URL = "ARG_KEY_SHARE_URL";
    private static final int RECONNECT_DELAY = 10000;
    private BroadcastGameHolder _broadcastGameHolder;
    private ChessClockView _chessClockView;
    private BroadcastChessClock _clock;
    private ConnectionLifecycleManager _connectionManager;
    private boolean _flagReloadGame = true;
    private ITournamentGameConnection _gameConnection;
    private TextView _liveNumber;
    private View _liveText;
    private TournamentPlayerInfoView _playerLeftView;
    private TournamentPlayerInfoView _playerRightView;
    private ShareAnalyzeNavigationBarItem _shareActionController;
    private String _shareLink;
    private Handler _uiThreadHandler;
    private View _userMoveButton;
    private boolean _userMoveMode = false;
    private View _userMoveText;

    public static TournamentGameViewFragment createFragmentWith(String string) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY_GAMEID, string);
        return TournamentGameViewFragment.createFragmentWithArguments(bundle);
    }

    public static TournamentGameViewFragment createFragmentWith(String string, String string2) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY_GAMEID, string);
        bundle.putString(ARG_KEY_SHARE_URL, string2);
        return TournamentGameViewFragment.createFragmentWithArguments(bundle);
    }

    private static TournamentGameViewFragment createFragmentWithArguments(Bundle bundle) {
        TournamentGameViewFragment tournamentGameViewFragment = new TournamentGameViewFragment();
        tournamentGameViewFragment.setArguments(bundle);
        return tournamentGameViewFragment;
    }

    private void exitUserMoveModeClicked() {
        this.getGameHolder().gotoLastNonUserMove();
    }

    private void initChessClock() {
        if (this.getGame() != null) {
            if (this._clock != null) {
                this._clock.removeClockListener(this);
                this._clock.stop();
            }
            this._clock = new BroadcastChessClock(this.getGame().getClockSetting());
            this._clock.addOnClockListener(this);
            this.initiateClockWithMoveTimes();
        }
    }

    private void initialiseConnection() {
        Object object;
        if (this.getArguments() != null) {
            TournamentGameID tournamentGameID = new TournamentGameID(this.getArguments().getString(ARG_KEY_GAMEID));
            this._shareLink = this.getArguments().getString(ARG_KEY_SHARE_URL);
            object = tournamentGameID;
            if (this._shareActionController != null) {
                object = tournamentGameID;
                if (this.getActivity() != null) {
                    object = this._shareActionController.getMenuItem((Context)this.getActivity());
                    boolean bl = this._shareLink != null;
                    object.setEnabled(bl);
                    object = tournamentGameID;
                }
            }
        } else {
            object = null;
        }
        if (this._gameConnection == null && object != null) {
            this._gameConnection = ServiceProvider.getInstance().getTournamentService().getTournamentGameConnection((TournamentGameID)object, this);
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.BROADCAST, "Tournament game selected", object.getUuid());
        }
        if (this._gameConnection != null) {
            if (!this._gameConnection.isConnected()) {
                this.showDialogWaiting(true, true, "", null);
            }
            this._connectionManager.addConnection(this._gameConnection);
        }
    }

    private void initiateClockWithMoveTimes() {
        if (this._clock != null) {
            this._clock.resetWithGame(this.getGame());
            this.updateClock();
        }
    }

    private boolean isGameRunning() {
        if (!(this._broadcastGameHolder == null || this._broadcastGameHolder.getGameStatus() != null && this._broadcastGameHolder.getGameStatus().isFinished())) {
            return true;
        }
        return false;
    }

    private void loadGame() {
        if (this._flagReloadGame) {
            this._gameConnection.loadGame();
        } else {
            this.hideDialogWaiting();
        }
        this._gameConnection.subscribeToGame();
    }

    private void setClockRunning(final boolean bl) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this._chessClockView.setRunning(bl);
            }
        });
    }

    private void setUserMoveMode(boolean bl) {
        ScaleAnimation scaleAnimation;
        this._userMoveButton.clearAnimation();
        this._userMoveText.clearAnimation();
        if (bl) {
            scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
            this._userMoveButton.setVisibility(0);
            this._userMoveText.setVisibility(0);
        } else {
            scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
        }
        scaleAnimation.setDuration(500L);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.startNow();
        this._userMoveButton.setAnimation((Animation)scaleAnimation);
        this._userMoveText.setAnimation((Animation)scaleAnimation);
        this._userMoveButton.invalidate();
        this._userMoveText.invalidate();
        scaleAnimation.startNow();
    }

    private void updateClock() {
        Object object = this._broadcastGameHolder;
        boolean bl = true;
        boolean bl2 = object != null && this._broadcastGameHolder.getLastLiveGameMove() != null ? this._broadcastGameHolder.getLastLiveGameMove().getPiece().getColor() : true;
        object = this._clock;
        boolean bl3 = !bl2;
        object.setActiveColor(bl3, false);
        object = this._chessClockView;
        bl2 = !bl2 ? bl : false;
        object.setActiveClockMark(bl2);
        object = this.getGame();
        if (!object.isFinished()) {
            if ((object = object.getFirstMove()) != null && !this._clock.isRunning() && !object.isUserMove()) {
                this._clock.start();
                return;
            }
        } else {
            this._clock.stop();
        }
    }

    private void updateViewsWithGame() {
        Game game = this.getGame();
        if (game != null) {
            this._playerLeftView.setOpponnent(game.getWhitePlayer());
            this._playerRightView.setOpponnent(game.getBlackPlayer());
            if (game.isFinished()) {
                this._chessClockView.setGameResult(game.getResult().getResult());
                return;
            }
            if (this._clock != null) {
                this._clock.start();
            }
        }
    }

    private void userMoveModeChanged(boolean bl) {
        if (bl != this._userMoveMode) {
            this.setUserMoveMode(bl);
        }
        this._userMoveMode = bl;
    }

    @Override
    public void allMovesChanged(MoveContainer moveContainer) {
    }

    @Override
    public boolean canSkipMoves() {
        return true;
    }

    @Override
    public void connectionClosed(IConnection iConnection) {
    }

    @Override
    public void connectionEstablished(IConnection iConnection) {
        this.loadGame();
    }

    @Override
    public void connectionFailed(IConnection iConnection) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                Toast.makeText((Context)TournamentGameViewFragment.this.getActivity(), (int)2131689598, (int)0).show();
            }
        });
    }

    @Override
    public void gameStatusChanged(final GameStatus gameStatus) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this._broadcastGameHolder.setGameStatus(gameStatus);
                if (gameStatus != null) {
                    TournamentGameViewFragment.this._chessClockView.setGameResult(gameStatus.getResult());
                    TournamentGameViewFragment.this.updateClock();
                    if (gameStatus.isFinished()) {
                        TournamentGameViewFragment.this._liveText.setVisibility(8);
                        TournamentGameViewFragment.this._liveNumber.setVisibility(8);
                        return;
                    }
                } else {
                    TournamentGameViewFragment.this._liveText.setVisibility(8);
                    TournamentGameViewFragment.this._liveNumber.setVisibility(8);
                }
            }
        });
    }

    @Override
    protected BroadcastGameHolder getGameHolder() {
        if (this._broadcastGameHolder == null) {
            this._broadcastGameHolder = new BroadcastGameHolder(new Game());
        }
        return this._broadcastGameHolder;
    }

    @Override
    public String getHeaderText(Resources resources) {
        return resources.getString(2131689580);
    }

    @Override
    public String getTrackingName() {
        return "TournamentGame";
    }

    @Override
    public void liveModeChanged(final boolean bl) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                if (TournamentGameViewFragment.this._liveText != null) {
                    boolean bl2 = bl;
                    int n = 0;
                    int n2 = !bl2 && TournamentGameViewFragment.this.isGameRunning() ? 1 : 0;
                    View view = TournamentGameViewFragment.this._liveText;
                    n2 = n2 != 0 ? n : 8;
                    view.setVisibility(n2);
                }
            }
        });
    }

    @Override
    public void moveDeleted(final int n) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this._broadcastGameHolder.deleteMoveInMainlineWithId(n);
                TournamentGameViewFragment.this.initiateClockWithMoveTimes();
            }
        });
    }

    @Override
    public void moveTimeChanged(int n, int n2) {
        Move move = this._broadcastGameHolder.getMoveById(n);
        if (move != null) {
            move.setMoveTimeInMills(n2);
            this.initiateClockWithMoveTimes();
        }
    }

    @Override
    public void newMove(final int n, final SEP sEP, final int n2, final int n3) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                Move move = TournamentGameViewFragment.this._broadcastGameHolder.addNewServerMove(n, sEP, n3, n2);
                if (move == null) {
                    TournamentGameViewFragment.this._flagReloadGame = true;
                    TournamentGameViewFragment.this._gameConnection.close();
                    return;
                }
                boolean bl = move.getPiece().getColor();
                if (TournamentGameViewFragment.this._clock != null) {
                    TournamentGameViewFragment.this._clock.addTimeUsage(move.getMoveTimeInMills(), bl);
                    TournamentGameViewFragment.this._clock.setActiveColor(bl ^ true, false);
                }
                if (TournamentGameViewFragment.this._chessClockView != null) {
                    TournamentGameViewFragment.this._chessClockView.setActiveClockMark(true ^ bl);
                }
                TournamentGameViewFragment.this.updateClock();
            }
        });
    }

    @Override
    public void newMove(int n, String string, int n2, int n3) {
        this.newMove(n, new SEP(this._broadcastGameHolder.getPosition().translateCastling(string)), n2, n3);
    }

    @Override
    public void newMove(Move move) {
    }

    @Override
    public void numberOfNewMovesChanged(int n) {
        TextView textView = this._liveNumber;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n);
        textView.setText((CharSequence)stringBuilder.toString());
        boolean bl = true;
        int n2 = n > 0 && this.isGameRunning() ? 1 : 0;
        textView = this._liveNumber;
        n2 = n2 != 0 ? 0 : 8;
        textView.setVisibility(n2);
        textView = this._liveText;
        if (n <= 0) {
            bl = false;
        }
        textView.setSelected(bl);
    }

    @Override
    public void onClockChanged(final long l, final boolean bl) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this._chessClockView.setTime(bl, l);
            }
        });
    }

    @Override
    public void onClockFlag(boolean bl) {
    }

    @Override
    public void onClockStarted() {
        this.setClockRunning(true);
    }

    @Override
    public void onClockStopped() {
        this.setClockRunning(false);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._uiThreadHandler = new Handler();
        this._connectionManager = ConnectionLifecycleManagerFactory.createManagerForFragment(this, ActivityLifecycle.CREATE_DESTROY, 10000);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = super.onCreateView(layoutInflater, viewGroup, bundle);
        bundle = (ViewGroup)viewGroup.findViewById(2131296315);
        layoutInflater.inflate(2131427384, (ViewGroup)bundle);
        this._chessClockView = (ChessClockView)viewGroup.findViewById(2131296365);
        this._playerLeftView = (TournamentPlayerInfoView)viewGroup.findViewById(2131296369);
        this._playerRightView = (TournamentPlayerInfoView)viewGroup.findViewById(2131296370);
        this._liveNumber = (TextView)bundle.findViewById(2131296367);
        this._liveText = bundle.findViewById(2131296368);
        this._userMoveButton = viewGroup.findViewById(2131296385);
        this._userMoveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                TournamentGameViewFragment.this.exitUserMoveModeClicked();
            }
        });
        this._userMoveText = viewGroup.findViewById(2131296386);
        this._liveText.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                TournamentGameViewFragment.this.getGameHolder().enableLiveMode();
            }
        });
        this.getGameHolder().addLiveModeChangeListener(this);
        this.getGameHolder().addMovesObserver(this);
        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();
        this._uiThreadHandler.removeCallbacksAndMessages(null);
        this.initialiseConnection();
        this.updateViewsWithGame();
        Move move = this.getGameHolder().getCurrentMove();
        if (move != null) {
            this.setUserMoveMode(move.isUserMove());
            boolean bl = move == this.getGameHolder().getLastLiveGameMove();
            this.liveModeChanged(bl);
            this.updateClock();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this._uiThreadHandler.postDelayed(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

                    @Override
                    public void run() {
                        TournamentGameViewFragment.this._connectionManager.removeConnection(TournamentGameViewFragment.this._gameConnection, true);
                        TournamentGameViewFragment.this._flagReloadGame = true;
                    }
                });
            }

        }, 120000L);
    }

    @Override
    public void selectedMoveChanged(Move move) {
        boolean bl = move == null ? false : move.isUserMove();
        this.userMoveModeChanged(bl);
    }

    @Override
    protected void setupMenuBar(AnalyzeNavigationBarController analyzeNavigationBarController) {
        super.setupMenuBar(analyzeNavigationBarController);
        analyzeNavigationBarController.addAnalyseBarItem(new InfoNavigationBarItem(this.getGameHolder(), this, this.getContentPresenter()));
        analyzeNavigationBarController.addAnalyseBarItem(new ResetToDeleteUserMovesNavigationBarItem(this.getGameHolder()));
        this.getMoveListController().setUseUserMoves(true);
        this._shareActionController = new ShareAnalyzeNavigationBarItem(new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", TournamentGameViewFragment.this._shareLink);
                intent.putExtra("android.intent.extra.SUBJECT", TournamentGameViewFragment.this.getResources().getString(2131690296));
                intent.setType("text/plain");
                TournamentGameViewFragment.this.startActivity(Intent.createChooser((Intent)intent, (CharSequence)TournamentGameViewFragment.this.getResources().getString(2131690072)));
                ServiceProvider.getInstance().getTrackingService().trackScreenOpen("TournamentGame: ShareDialog");
            }
        });
        this._shareActionController.getMenuItem((Context)this.getActivity()).setEnabled(false);
        analyzeNavigationBarController.addAnalyseBarItem(this._shareActionController);
    }

    @Override
    public void tournamentGameLoaded(final Game game) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this.setGame(game);
                TournamentGameViewFragment.this.hideDialogWaiting();
                TournamentGameViewFragment.this._flagReloadGame = false;
                TournamentGameViewFragment.this.liveModeChanged(true);
                TournamentGameViewFragment.this.updateViewsWithGame();
                TournamentGameViewFragment.this.initChessClock();
            }
        });
    }

    @Override
    public void tournamentGameLoadingFailed(final APIStatusCode aPIStatusCode) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable(){

            @Override
            public void run() {
                TournamentGameViewFragment.this.hideDialogWaiting();
                TournamentGameViewFragment.this.showViewForErrorCode(aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        TournamentGameViewFragment.this._flagReloadGame = true;
                        TournamentGameViewFragment.this.loadGame();
                    }
                }, true);
            }

        });
    }

}
