// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast;

import android.content.Intent;
import de.cisha.android.board.analyze.navigationbaritems.ResetToDeleteUserMovesNavigationBarItem;
import de.cisha.android.board.analyze.navigationbaritems.AnalyzeNavigationBarItem;
import de.cisha.android.board.analyze.view.GameInfoDialogFragment;
import de.cisha.android.board.analyze.navigationbaritems.InfoNavigationBarItem;
import de.cisha.android.board.analyze.AnalyzeNavigationBarController;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManagerFactory;
import de.cisha.android.board.broadcast.connection.ActivityLifecycle;
import de.cisha.chess.model.SEP;
import android.content.res.Resources;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.GameStatus;
import android.widget.Toast;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Move;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import android.content.Context;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import android.os.Bundle;
import de.cisha.chess.model.Game;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import android.os.Handler;
import de.cisha.android.board.analyze.navigationbaritems.ShareAnalyzeNavigationBarItem;
import de.cisha.android.board.broadcast.view.TournamentPlayerInfoView;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.broadcast.connection.ConnectionLifecycleManager;
import de.cisha.android.board.broadcast.model.BroadcastChessClock;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.chess.model.MovesObserver;
import de.cisha.android.board.broadcast.model.BroadcastGameHolder;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.analyze.AbstractAnalyseFragment;

public class TournamentGameViewFragment extends AbstractAnalyseFragment implements TournamentGameConnectionListener, LiveModeChangeListener, MovesObserver, ClockListener
{
    private static final String ARG_KEY_GAMEID = "TournamentGameId";
    private static final String ARG_KEY_SHARE_URL = "ARG_KEY_SHARE_URL";
    private static final int RECONNECT_DELAY = 10000;
    private BroadcastGameHolder _broadcastGameHolder;
    private ChessClockView _chessClockView;
    private BroadcastChessClock _clock;
    private ConnectionLifecycleManager _connectionManager;
    private boolean _flagReloadGame;
    private ITournamentGameConnection _gameConnection;
    private TextView _liveNumber;
    private View _liveText;
    private TournamentPlayerInfoView _playerLeftView;
    private TournamentPlayerInfoView _playerRightView;
    private ShareAnalyzeNavigationBarItem _shareActionController;
    private String _shareLink;
    private Handler _uiThreadHandler;
    private View _userMoveButton;
    private boolean _userMoveMode;
    private View _userMoveText;
    
    public TournamentGameViewFragment() {
        this._flagReloadGame = true;
        this._userMoveMode = false;
    }
    
    public static TournamentGameViewFragment createFragmentWith(final String s) {
        final Bundle bundle = new Bundle();
        bundle.putString("TournamentGameId", s);
        return createFragmentWithArguments(bundle);
    }
    
    public static TournamentGameViewFragment createFragmentWith(final String s, final String s2) {
        final Bundle bundle = new Bundle();
        bundle.putString("TournamentGameId", s);
        bundle.putString("ARG_KEY_SHARE_URL", s2);
        return createFragmentWithArguments(bundle);
    }
    
    private static TournamentGameViewFragment createFragmentWithArguments(final Bundle arguments) {
        final TournamentGameViewFragment tournamentGameViewFragment = new TournamentGameViewFragment();
        tournamentGameViewFragment.setArguments(arguments);
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
            (this._clock = new BroadcastChessClock(this.getGame().getClockSetting())).addOnClockListener(this);
            this.initiateClockWithMoveTimes();
        }
    }
    
    private void initialiseConnection() {
        TournamentGameID tournamentGameID2;
        if (this.getArguments() != null) {
            final TournamentGameID tournamentGameID = new TournamentGameID(this.getArguments().getString("TournamentGameId"));
            this._shareLink = this.getArguments().getString("ARG_KEY_SHARE_URL");
            tournamentGameID2 = tournamentGameID;
            if (this._shareActionController != null) {
                tournamentGameID2 = tournamentGameID;
                if (this.getActivity() != null) {
                    this._shareActionController.getMenuItem((Context)this.getActivity()).setEnabled(this._shareLink != null);
                    tournamentGameID2 = tournamentGameID;
                }
            }
        }
        else {
            tournamentGameID2 = null;
        }
        if (this._gameConnection == null && tournamentGameID2 != null) {
            this._gameConnection = ServiceProvider.getInstance().getTournamentService().getTournamentGameConnection(tournamentGameID2, this);
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.BROADCAST, "Tournament game selected", tournamentGameID2.getUuid());
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
        return this._broadcastGameHolder != null && (this._broadcastGameHolder.getGameStatus() == null || !this._broadcastGameHolder.getGameStatus().isFinished());
    }
    
    private void loadGame() {
        if (this._flagReloadGame) {
            this._gameConnection.loadGame();
        }
        else {
            this.hideDialogWaiting();
        }
        this._gameConnection.subscribeToGame();
    }
    
    private void setClockRunning(final boolean b) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentGameViewFragment.this._chessClockView.setRunning(b);
            }
        });
    }
    
    private void setUserMoveMode(final boolean b) {
        this._userMoveButton.clearAnimation();
        this._userMoveText.clearAnimation();
        ScaleAnimation scaleAnimation;
        if (b) {
            scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
            this._userMoveButton.setVisibility(0);
            this._userMoveText.setVisibility(0);
        }
        else {
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
        final BroadcastGameHolder broadcastGameHolder = this._broadcastGameHolder;
        final boolean b = true;
        final boolean b2 = broadcastGameHolder == null || this._broadcastGameHolder.getLastLiveGameMove() == null || this._broadcastGameHolder.getLastLiveGameMove().getPiece().getColor();
        this._clock.setActiveColor(!b2, false);
        this._chessClockView.setActiveClockMark(!b2 && b);
        final Game game = this.getGame();
        if (!game.isFinished()) {
            final Move firstMove = game.getFirstMove();
            if (firstMove != null && !this._clock.isRunning() && !firstMove.isUserMove()) {
                this._clock.start();
            }
        }
        else {
            this._clock.stop();
        }
    }
    
    private void updateViewsWithGame() {
        final Game game = this.getGame();
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
    
    private void userMoveModeChanged(final boolean b) {
        if (b != this._userMoveMode) {
            this.setUserMoveMode(b);
        }
        this._userMoveMode = b;
    }
    
    @Override
    public void allMovesChanged(final MoveContainer moveContainer) {
    }
    
    @Override
    public boolean canSkipMoves() {
        return true;
    }
    
    public void connectionClosed(final IConnection connection) {
    }
    
    public void connectionEstablished(final IConnection connection) {
        this.loadGame();
    }
    
    public void connectionFailed(final IConnection connection) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                Toast.makeText((Context)TournamentGameViewFragment.this.getActivity(), 2131689598, 0).show();
            }
        });
    }
    
    @Override
    public void gameStatusChanged(final GameStatus gameStatus) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentGameViewFragment.this._broadcastGameHolder.setGameStatus(gameStatus);
                if (gameStatus != null) {
                    TournamentGameViewFragment.this._chessClockView.setGameResult(gameStatus.getResult());
                    TournamentGameViewFragment.this.updateClock();
                    if (gameStatus.isFinished()) {
                        TournamentGameViewFragment.this._liveText.setVisibility(8);
                        TournamentGameViewFragment.this._liveNumber.setVisibility(8);
                    }
                }
                else {
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
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131689580);
    }
    
    @Override
    public String getTrackingName() {
        return "TournamentGame";
    }
    
    @Override
    public void liveModeChanged(final boolean b) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                if (TournamentGameViewFragment.this._liveText != null) {
                    final boolean val.isInLiveMode = b;
                    final boolean b = false;
                    final boolean b2 = !val.isInLiveMode && TournamentGameViewFragment.this.isGameRunning();
                    final View access.1200 = TournamentGameViewFragment.this._liveText;
                    int visibility;
                    if (b2) {
                        visibility = (b ? 1 : 0);
                    }
                    else {
                        visibility = 8;
                    }
                    access.1200.setVisibility(visibility);
                }
            }
        });
    }
    
    @Override
    public void moveDeleted(final int n) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentGameViewFragment.this._broadcastGameHolder.deleteMoveInMainlineWithId(n);
                TournamentGameViewFragment.this.initiateClockWithMoveTimes();
            }
        });
    }
    
    @Override
    public void moveTimeChanged(final int n, final int moveTimeInMills) {
        final Move moveById = this._broadcastGameHolder.getMoveById(n);
        if (moveById != null) {
            moveById.setMoveTimeInMills(moveTimeInMills);
            this.initiateClockWithMoveTimes();
        }
    }
    
    @Override
    public void newMove(final int n, final SEP sep, final int n2, final int n3) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                final Move addNewServerMove = TournamentGameViewFragment.this._broadcastGameHolder.addNewServerMove(n, sep, n3, n2);
                if (addNewServerMove == null) {
                    TournamentGameViewFragment.this._flagReloadGame = true;
                    TournamentGameViewFragment.this._gameConnection.close();
                    return;
                }
                final boolean color = addNewServerMove.getPiece().getColor();
                if (TournamentGameViewFragment.this._clock != null) {
                    TournamentGameViewFragment.this._clock.addTimeUsage(addNewServerMove.getMoveTimeInMills(), color);
                    TournamentGameViewFragment.this._clock.setActiveColor(color ^ true, false);
                }
                if (TournamentGameViewFragment.this._chessClockView != null) {
                    TournamentGameViewFragment.this._chessClockView.setActiveClockMark(true ^ color);
                }
                TournamentGameViewFragment.this.updateClock();
            }
        });
    }
    
    @Override
    public void newMove(final int n, final String s, final int n2, final int n3) {
        this.newMove(n, new SEP(this._broadcastGameHolder.getPosition().translateCastling(s)), n2, n3);
    }
    
    @Override
    public void newMove(final Move move) {
    }
    
    @Override
    public void numberOfNewMovesChanged(final int n) {
        final TextView liveNumber = this._liveNumber;
        final StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(n);
        liveNumber.setText((CharSequence)sb.toString());
        boolean selected = true;
        final boolean b = n > 0 && this.isGameRunning();
        final TextView liveNumber2 = this._liveNumber;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        liveNumber2.setVisibility(visibility);
        final View liveText = this._liveText;
        if (n <= 0) {
            selected = false;
        }
        liveText.setSelected(selected);
    }
    
    @Override
    public void onClockChanged(final long n, final boolean b) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentGameViewFragment.this._chessClockView.setTime(b, n);
            }
        });
    }
    
    @Override
    public void onClockFlag(final boolean b) {
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
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._uiThreadHandler = new Handler();
        this._connectionManager = ConnectionLifecycleManagerFactory.createManagerForFragment(this, ActivityLifecycle.CREATE_DESTROY, 10000);
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        final ViewGroup viewGroup2 = (ViewGroup)onCreateView.findViewById(2131296315);
        layoutInflater.inflate(2131427384, viewGroup2);
        this._chessClockView = (ChessClockView)onCreateView.findViewById(2131296365);
        this._playerLeftView = (TournamentPlayerInfoView)onCreateView.findViewById(2131296369);
        this._playerRightView = (TournamentPlayerInfoView)onCreateView.findViewById(2131296370);
        this._liveNumber = (TextView)viewGroup2.findViewById(2131296367);
        this._liveText = viewGroup2.findViewById(2131296368);
        (this._userMoveButton = onCreateView.findViewById(2131296385)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                TournamentGameViewFragment.this.exitUserMoveModeClicked();
            }
        });
        this._userMoveText = onCreateView.findViewById(2131296386);
        this._liveText.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                TournamentGameViewFragment.this.getGameHolder().enableLiveMode();
            }
        });
        this.getGameHolder().addLiveModeChangeListener((BroadcastGameHolder.LiveModeChangeListener)this);
        this.getGameHolder().addMovesObserver(this);
        return onCreateView;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        this._uiThreadHandler.removeCallbacksAndMessages((Object)null);
        this.initialiseConnection();
        this.updateViewsWithGame();
        final Move currentMove = this.getGameHolder().getCurrentMove();
        if (currentMove != null) {
            this.setUserMoveMode(currentMove.isUserMove());
            this.liveModeChanged(currentMove == this.getGameHolder().getLastLiveGameMove());
            this.updateClock();
        }
    }
    
    @Override
    public void onStop() {
        super.onStop();
        this._uiThreadHandler.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                TournamentGameViewFragment.this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    public void selectedMoveChanged(final Move move) {
        this.userMoveModeChanged(move != null && move.isUserMove());
    }
    
    @Override
    protected void setupMenuBar(final AnalyzeNavigationBarController analyzeNavigationBarController) {
        super.setupMenuBar(analyzeNavigationBarController);
        analyzeNavigationBarController.addAnalyseBarItem(new InfoNavigationBarItem(this.getGameHolder(), this, this.getContentPresenter()));
        analyzeNavigationBarController.addAnalyseBarItem(new ResetToDeleteUserMovesNavigationBarItem(this.getGameHolder()));
        this.getMoveListController().setUseUserMoves(true);
        this._shareActionController = new ShareAnalyzeNavigationBarItem(new Runnable() {
            @Override
            public void run() {
                final Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", TournamentGameViewFragment.this._shareLink);
                intent.putExtra("android.intent.extra.SUBJECT", TournamentGameViewFragment.this.getResources().getString(2131690296));
                intent.setType("text/plain");
                TournamentGameViewFragment.this.startActivity(Intent.createChooser(intent, (CharSequence)TournamentGameViewFragment.this.getResources().getString(2131690072)));
                ServiceProvider.getInstance().getTrackingService().trackScreenOpen("TournamentGame: ShareDialog");
            }
        });
        this._shareActionController.getMenuItem((Context)this.getActivity()).setEnabled(false);
        analyzeNavigationBarController.addAnalyseBarItem(this._shareActionController);
    }
    
    @Override
    public void tournamentGameLoaded(final Game game) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
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
    public void tournamentGameLoadingFailed(final APIStatusCode apiStatusCode) {
        this.runOnUiThreadBetweenStartAndDestroy(new Runnable() {
            @Override
            public void run() {
                TournamentGameViewFragment.this.hideDialogWaiting();
                TournamentGameViewFragment.this.showViewForErrorCode(apiStatusCode, new IErrorPresenter.IReloadAction() {
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
