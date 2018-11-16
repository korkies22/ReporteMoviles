// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.engine;

import android.widget.Toast;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import android.os.Bundle;
import de.cisha.android.board.mainmenu.view.MenuItem;
import android.content.res.Resources;
import android.widget.LinearLayout;
import de.cisha.android.board.playzone.engine.view.AfterGameViewEngine;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import android.view.View;
import de.cisha.android.board.service.ILocalGameService;
import de.cisha.android.board.playzone.view.ChessClockView;
import android.os.Handler;
import android.os.Looper;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import android.content.Context;
import de.cisha.android.board.playzone.engine.model.EngineGameAdapter;
import de.cisha.chess.model.Opponent;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.view.RookieResumeGameDialogFragment;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.IContentPresenter;
import de.cisha.chess.model.Game;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.AbstractPlayzoneFragment;

public class PlayzoneEngineFragment extends AbstractPlayzoneFragment
{
    private long _currentTimeOnStop;
    private String _engineName;
    private GameEndInformation _gameEndInfo;
    private StoredGameInformation _gameInformation;
    private boolean _initialStart;
    private int _selectedElo;
    private TimeControl _selectedTimeControl;
    private boolean _selectedWhite;
    private long _timeStampOnLastMove;
    
    public PlayzoneEngineFragment() {
        this._initialStart = true;
    }
    
    public static PlayzoneEngineFragment createFragment(final TimeControl selectedTimeControl, final int selectedElo, final String engineName, final boolean selectedWhite) {
        final PlayzoneEngineFragment playzoneEngineFragment = new PlayzoneEngineFragment();
        playzoneEngineFragment._selectedTimeControl = selectedTimeControl;
        playzoneEngineFragment._selectedElo = selectedElo;
        playzoneEngineFragment._engineName = engineName;
        playzoneEngineFragment._selectedWhite = selectedWhite;
        return playzoneEngineFragment;
    }
    
    private StoredGameInformation loadRunningGame() {
        final StoredGameInformation loadGame = ServiceProvider.getInstance().getLocalGameService().loadGame();
        if (loadGame != null) {
            final Game game = loadGame.getGame();
            Opponent opponent;
            if (loadGame.isPlayerWhite()) {
                opponent = game.getBlackPlayer();
            }
            else {
                opponent = game.getWhitePlayer();
            }
            this._engineName = opponent.getName();
            this._selectedElo = opponent.getRating().getRating();
            this._selectedTimeControl = new TimeControl(game.getClockSetting().getBase(true) / 60000, game.getClockSetting().getIncrementPerMove(true) / 1000);
            if (this.getGameAdapter() == null || !this.getGameAdapter().isGameActive()) {
                final RookieResumeGameDialogFragment rookieResumeGameDialogFragment = new RookieResumeGameDialogFragment();
                final StringBuilder sb = new StringBuilder();
                sb.append(this.getActivity().getString(2131690127));
                sb.append(" ");
                sb.append(ChessClock.formatTime(loadGame.getRemainingMillisFrom(loadGame.isPlayerWhite()), true));
                sb.append(" : ");
                sb.append(ChessClock.formatTime(loadGame.getRemainingMillisFrom(loadGame.isPlayerWhite() ^ true), true));
                sb.append(" ");
                sb.append(loadGame.getOpponentsName());
                rookieResumeGameDialogFragment.setText(sb.toString());
                rookieResumeGameDialogFragment.setCancelable(false);
                rookieResumeGameDialogFragment.show(this.getChildFragmentManager(), null);
                rookieResumeGameDialogFragment.setResumeGameDialogListener((RookieResumeGameDialogFragment.ResumeGameDialogListener)new RookieResumeGameDialogFragment.ResumeGameDialogListener() {
                    @Override
                    public void onDiscardClicked() {
                        if (PlayzoneEngineFragment.this.getGameAdapter() != null) {
                            if (PlayzoneEngineFragment.this.getGameAdapter().isAbortable()) {
                                PlayzoneEngineFragment.this.getGameAdapter().requestAbort();
                            }
                            else {
                                PlayzoneEngineFragment.this.getGameAdapter().resignGame();
                            }
                        }
                        ServiceProvider.getInstance().getLocalGameService().clearGameStorage();
                    }
                    
                    @Override
                    public void onResumeClicked() {
                        PlayzoneEngineFragment.this.resumeGame(game, loadGame.isPlayerWhite());
                    }
                });
            }
        }
        return loadGame;
    }
    
    private void resumeGame(final Game game, final boolean b) {
        Opponent opponent;
        if (b) {
            opponent = game.getBlackPlayer();
        }
        else {
            opponent = game.getWhitePlayer();
        }
        this.startGame(this._selectedTimeControl, this._selectedElo, b, opponent.getName(), game);
        this._timeStampOnLastMove = System.currentTimeMillis() - game.getClockSetting().getTimeGoneSinceLastMove();
    }
    
    private void startGame(final TimeControl timeControl, final int n, final boolean color, final String name, final Game game) {
        ServiceProvider.getInstance().getLocalGameService().clearGameStorage();
        final EngineGameAdapter.Builder setName = new EngineGameAdapter.Builder(n, timeControl, (Context)this.getActivity(), this).setColor(color).setName(name);
        if (game != null) {
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game resumed", null);
            setName.setGame(game);
        }
        else {
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", name);
        }
        if (timeControl != null) {
            final ChessClockView chessClockView = this.getChessClockView();
            if (chessClockView != null) {
                chessClockView.setShowMilliseconds(timeControl.hasTimeControl());
            }
        }
        this.showDialogWaiting(true, false, "loading engine...", new RookieDialogLoading.OnCancelListener() {
            @Override
            public void onCancel() {
                PlayzoneEngineFragment.this.showMenusForPlayzoneState(PlayzoneState.CHOOSING_BEFORE);
            }
        });
        new Handler(Looper.getMainLooper()).post((Runnable)new EngineRunnable(setName));
    }
    
    private void storeGame(final EngineGameAdapter engineGameAdapter) {
        final ILocalGameService localGameService = ServiceProvider.getInstance().getLocalGameService();
        final Game gameClone = engineGameAdapter.getGameClone();
        final boolean playerIsWhite = engineGameAdapter.playerIsWhite();
        long n;
        if (engineGameAdapter.getGame().getAllMainLineMoves().size() > 1) {
            n = this._currentTimeOnStop - this._timeStampOnLastMove;
        }
        else {
            n = 0L;
        }
        localGameService.storeGameLocally(gameClone, playerIsWhite, n);
    }
    
    @Override
    protected View getAfterGameView() {
        final ConfirmCallback confirmCallback = new ConfirmCallback() {
            @Override
            public void canceled() {
            }
            
            @Override
            public void confirmed() {
                PlayzoneEngineFragment.this.startGame(PlayzoneEngineFragment.this._selectedTimeControl, PlayzoneEngineFragment.this._selectedElo, PlayzoneEngineFragment.this.getGameAdapter().playerIsWhite() ^ true, PlayzoneEngineFragment.this._engineName, null);
            }
        };
        final ConfirmCallback confirmCallback2 = new ConfirmCallback() {
            @Override
            public void canceled() {
            }
            
            @Override
            public void confirmed() {
                PlayzoneEngineFragment.this.startAnalyzeGame(PlayzoneEngineFragment.this.getGameAdapter().getGame());
            }
        };
        final ConfirmCallback confirmCallback3 = new ConfirmCallback() {
            @Override
            public void canceled() {
            }
            
            @Override
            public void confirmed() {
                PlayzoneEngineFragment.this.getContentPresenter().popCurrentFragment();
            }
        };
        final boolean playerIsWhite = this.getGameAdapter().playerIsWhite();
        return (View)new AfterGameViewEngine((Context)this.getActivity(), new ModelHolder<AfterGameInformation>(new AfterGameInformation(this._gameEndInfo, playerIsWhite, this.getGameAdapter().getPlayerInfo(playerIsWhite), this.getGameAdapter().getPlayerInfo(playerIsWhite ^ true))), confirmCallback, confirmCallback2, confirmCallback3, this.getChildFragmentManager(), this.onSavenInstanceStateCalled() ^ true);
    }
    
    @Override
    protected View getBeforeGameView() {
        return (View)new LinearLayout((Context)this.getActivity());
    }
    
    @Override
    public String getHeaderText(final Resources resources) {
        return resources.getString(2131690113);
    }
    
    @Override
    public MenuItem getHighlightedMenuItem() {
        return MenuItem.PLAYVSCOMPUTER;
    }
    
    @Override
    public String getTrackingName() {
        return "PlayzoneEngine";
    }
    
    @Override
    protected boolean needAuthToken() {
        return false;
    }
    
    @Override
    protected boolean needNetwork() {
        return false;
    }
    
    @Override
    protected boolean needRegisteredUser() {
        return false;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        this._gameEndInfo = new GameEndInformation(new GameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON), true);
        this._initialStart = true;
        if (this._selectedElo > 0 && this._selectedTimeControl != null && this._engineName != null) {
            final StoredGameInformation loadGame = ServiceProvider.getInstance().getLocalGameService().loadGame();
            if (loadGame != null && this._engineName.equals(loadGame.getOpponentsName())) {
                final Game game = loadGame.getGame();
                this._selectedElo = loadGame.getOpponentsRating().getRating();
                this._selectedTimeControl = new TimeControl(loadGame.getBaseTimeMinutes(), loadGame.getIncrementSeconds());
                this.resumeGame(game, loadGame.isPlayerWhite());
            }
            else {
                this.startGame(this._selectedTimeControl, this._selectedElo, this._selectedWhite, this._engineName, null);
            }
        }
        else {
            this._gameInformation = this.loadRunningGame();
        }
        super.onCreate(bundle);
    }
    
    @Override
    public void onGameEnd(final GameEndInformation gameEndInfo) {
        String name;
        if (this.getGameAdapter() != null && this.getGameAdapter().getOpponent() != null) {
            name = this.getGameAdapter().getOpponent().getName();
        }
        else {
            name = null;
        }
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game ended", name);
        this._gameEndInfo = gameEndInfo;
        ServiceProvider.getInstance().getLocalGameService().clearGameStorage();
        this.onGameSessionEnded(gameEndInfo.getStatus());
        super.onGameEnd(gameEndInfo);
    }
    
    @Override
    public void onMove(final Move move) {
        this._timeStampOnLastMove = System.currentTimeMillis();
        super.onMove(move);
    }
    
    @Override
    public void onOpponentDeclinedRematch() {
    }
    
    @Override
    public void onPause() {
        this._currentTimeOnStop = System.currentTimeMillis();
        final AbstractGameModel gameAdapter = this.getGameAdapter();
        if (gameAdapter != null) {
            if (gameAdapter.isGameActive()) {
                this.storeGame((EngineGameAdapter)gameAdapter);
            }
            gameAdapter.destroy();
        }
        this._gameInformation = null;
        super.onPause();
    }
    
    @Override
    public void onReceivedRematchOffer() {
    }
    
    @Override
    public void onResume() {
        if (!this._initialStart) {
            if (this._gameInformation == null) {
                this._gameInformation = this.loadRunningGame();
            }
            if (this._gameEndInfo != null && this._gameEndInfo.getStatus().isFinished()) {
                this.showMenusForPlayzoneState(PlayzoneState.AFTER);
            }
        }
        this._initialStart = false;
        super.onResume();
    }
    
    @Override
    public void onStartGameFailed(final int n) {
        Toast.makeText((Context)this.getActivity(), (CharSequence)"starting game failed", 1).show();
        this.showMenusForPlayzoneState(PlayzoneState.CHOOSING_BEFORE);
    }
    
    @Override
    public boolean showMenu() {
        return false;
    }
    
    class EngineRunnable implements Runnable
    {
        private EngineGameAdapter.Builder builder;
        
        public EngineRunnable(final EngineGameAdapter.Builder builder) {
            this.builder = builder;
        }
        
        @Override
        public void run() {
            final EngineGameAdapter create = this.builder.create();
            PlayzoneEngineFragment.this.showMenusForPlayzoneState(PlayzoneState.PLAYING);
            PlayzoneEngineFragment.this.hideDialogWaiting();
            PlayzoneEngineFragment.this.gameChosen(create);
        }
    }
}
