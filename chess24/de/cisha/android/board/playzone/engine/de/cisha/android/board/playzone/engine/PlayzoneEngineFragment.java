/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 *  android.widget.LinearLayout
 *  android.widget.Toast
 */
package de.cisha.android.board.playzone.engine;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.ModelHolder;
import de.cisha.android.board.mainmenu.view.MenuItem;
import de.cisha.android.board.modalfragments.RookieDialogLoading;
import de.cisha.android.board.playzone.AbstractPlayzoneFragment;
import de.cisha.android.board.playzone.engine.model.EngineGameAdapter;
import de.cisha.android.board.playzone.engine.view.AfterGameViewEngine;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.AfterGameInformation;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.playzone.view.ChessClockView;
import de.cisha.android.board.playzone.view.RookieResumeGameDialogFragment;
import de.cisha.android.board.service.ILocalGameService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.StoredGameInformation;
import de.cisha.android.ui.patterns.dialogs.ConfirmCallback;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;
import java.util.List;

public class PlayzoneEngineFragment
extends AbstractPlayzoneFragment {
    private long _currentTimeOnStop;
    private String _engineName;
    private GameEndInformation _gameEndInfo;
    private StoredGameInformation _gameInformation;
    private boolean _initialStart = true;
    private int _selectedElo;
    private TimeControl _selectedTimeControl;
    private boolean _selectedWhite;
    private long _timeStampOnLastMove;

    public static PlayzoneEngineFragment createFragment(TimeControl timeControl, int n, String string, boolean bl) {
        PlayzoneEngineFragment playzoneEngineFragment = new PlayzoneEngineFragment();
        playzoneEngineFragment._selectedTimeControl = timeControl;
        playzoneEngineFragment._selectedElo = n;
        playzoneEngineFragment._engineName = string;
        playzoneEngineFragment._selectedWhite = bl;
        return playzoneEngineFragment;
    }

    private StoredGameInformation loadRunningGame() {
        final StoredGameInformation storedGameInformation = ServiceProvider.getInstance().getLocalGameService().loadGame();
        if (storedGameInformation != null) {
            final Game game = storedGameInformation.getGame();
            Object object = storedGameInformation.isPlayerWhite() ? game.getBlackPlayer() : game.getWhitePlayer();
            this._engineName = object.getName();
            this._selectedElo = object.getRating().getRating();
            this._selectedTimeControl = new TimeControl(game.getClockSetting().getBase(true) / 60000, game.getClockSetting().getIncrementPerMove(true) / 1000);
            if (this.getGameAdapter() == null || !this.getGameAdapter().isGameActive()) {
                object = new RookieResumeGameDialogFragment();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.getActivity().getString(2131690127));
                stringBuilder.append(" ");
                stringBuilder.append(ChessClock.formatTime(storedGameInformation.getRemainingMillisFrom(storedGameInformation.isPlayerWhite()), true));
                stringBuilder.append(" : ");
                stringBuilder.append(ChessClock.formatTime(storedGameInformation.getRemainingMillisFrom(storedGameInformation.isPlayerWhite() ^ true), true));
                stringBuilder.append(" ");
                stringBuilder.append(storedGameInformation.getOpponentsName());
                object.setText(stringBuilder.toString());
                object.setCancelable(false);
                object.show(this.getChildFragmentManager(), null);
                object.setResumeGameDialogListener(new RookieResumeGameDialogFragment.ResumeGameDialogListener(){

                    @Override
                    public void onDiscardClicked() {
                        if (PlayzoneEngineFragment.this.getGameAdapter() != null) {
                            if (PlayzoneEngineFragment.this.getGameAdapter().isAbortable()) {
                                PlayzoneEngineFragment.this.getGameAdapter().requestAbort();
                            } else {
                                PlayzoneEngineFragment.this.getGameAdapter().resignGame();
                            }
                        }
                        ServiceProvider.getInstance().getLocalGameService().clearGameStorage();
                    }

                    @Override
                    public void onResumeClicked() {
                        PlayzoneEngineFragment.this.resumeGame(game, storedGameInformation.isPlayerWhite());
                    }
                });
            }
        }
        return storedGameInformation;
    }

    private void resumeGame(Game game, boolean bl) {
        Opponent opponent = bl ? game.getBlackPlayer() : game.getWhitePlayer();
        this.startGame(this._selectedTimeControl, this._selectedElo, bl, opponent.getName(), game);
        this._timeStampOnLastMove = System.currentTimeMillis() - (long)game.getClockSetting().getTimeGoneSinceLastMove();
    }

    private void startGame(TimeControl timeControl, int n, boolean bl, String object, Game game) {
        ServiceProvider.getInstance().getLocalGameService().clearGameStorage();
        EngineGameAdapter.Builder builder = new EngineGameAdapter.Builder(n, timeControl, (Context)this.getActivity(), this).setColor(bl).setName((String)object);
        if (game != null) {
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game resumed", null);
            builder.setGame(game);
        } else {
            ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game started", (String)object);
        }
        if (timeControl != null && (object = this.getChessClockView()) != null) {
            object.setShowMilliseconds(timeControl.hasTimeControl());
        }
        this.showDialogWaiting(true, false, "loading engine...", new RookieDialogLoading.OnCancelListener(){

            @Override
            public void onCancel() {
                PlayzoneEngineFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.CHOOSING_BEFORE);
            }
        });
        new Handler(Looper.getMainLooper()).post((Runnable)new 1EngineRunnable(builder));
    }

    private void storeGame(EngineGameAdapter engineGameAdapter) {
        ILocalGameService iLocalGameService = ServiceProvider.getInstance().getLocalGameService();
        Game game = engineGameAdapter.getGameClone();
        boolean bl = engineGameAdapter.playerIsWhite();
        long l = engineGameAdapter.getGame().getAllMainLineMoves().size() > 1 ? this._currentTimeOnStop - this._timeStampOnLastMove : 0L;
        iLocalGameService.storeGameLocally(game, bl, l);
    }

    @Override
    protected View getAfterGameView() {
        ConfirmCallback confirmCallback = new ConfirmCallback(){

            @Override
            public void canceled() {
            }

            @Override
            public void confirmed() {
                PlayzoneEngineFragment.this.startGame(PlayzoneEngineFragment.this._selectedTimeControl, PlayzoneEngineFragment.this._selectedElo, PlayzoneEngineFragment.this.getGameAdapter().playerIsWhite() ^ true, PlayzoneEngineFragment.this._engineName, null);
            }
        };
        ConfirmCallback confirmCallback2 = new ConfirmCallback(){

            @Override
            public void canceled() {
            }

            @Override
            public void confirmed() {
                PlayzoneEngineFragment.this.startAnalyzeGame(PlayzoneEngineFragment.this.getGameAdapter().getGame());
            }
        };
        ConfirmCallback confirmCallback3 = new ConfirmCallback(){

            @Override
            public void canceled() {
            }

            @Override
            public void confirmed() {
                PlayzoneEngineFragment.this.getContentPresenter().popCurrentFragment();
            }
        };
        boolean bl = this.getGameAdapter().playerIsWhite();
        ModelHolder<AfterGameInformation> modelHolder = new ModelHolder<AfterGameInformation>(new AfterGameInformation(this._gameEndInfo, bl, this.getGameAdapter().getPlayerInfo(bl), this.getGameAdapter().getPlayerInfo(bl ^ true)));
        return new AfterGameViewEngine((Context)this.getActivity(), modelHolder, confirmCallback, confirmCallback2, confirmCallback3, this.getChildFragmentManager(), this.onSavenInstanceStateCalled() ^ true);
    }

    @Override
    protected View getBeforeGameView() {
        return new LinearLayout((Context)this.getActivity());
    }

    @Override
    public String getHeaderText(Resources resources) {
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
    public void onCreate(Bundle bundle) {
        this._gameEndInfo = new GameEndInformation(new GameStatus(GameResult.NO_RESULT, GameEndReason.NO_REASON), true);
        this._initialStart = true;
        if (this._selectedElo > 0 && this._selectedTimeControl != null && this._engineName != null) {
            StoredGameInformation storedGameInformation = ServiceProvider.getInstance().getLocalGameService().loadGame();
            if (storedGameInformation != null && this._engineName.equals(storedGameInformation.getOpponentsName())) {
                Game game = storedGameInformation.getGame();
                this._selectedElo = storedGameInformation.getOpponentsRating().getRating();
                this._selectedTimeControl = new TimeControl(storedGameInformation.getBaseTimeMinutes(), storedGameInformation.getIncrementSeconds());
                this.resumeGame(game, storedGameInformation.isPlayerWhite());
            } else {
                this.startGame(this._selectedTimeControl, this._selectedElo, this._selectedWhite, this._engineName, null);
            }
        } else {
            this._gameInformation = this.loadRunningGame();
        }
        super.onCreate(bundle);
    }

    @Override
    public void onGameEnd(GameEndInformation gameEndInformation) {
        String string = this.getGameAdapter() != null && this.getGameAdapter().getOpponent() != null ? this.getGameAdapter().getOpponent().getName() : null;
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.PLAYZONE, "engine game ended", string);
        this._gameEndInfo = gameEndInformation;
        ServiceProvider.getInstance().getLocalGameService().clearGameStorage();
        this.onGameSessionEnded(gameEndInformation.getStatus());
        super.onGameEnd(gameEndInformation);
    }

    @Override
    public void onMove(Move move) {
        this._timeStampOnLastMove = System.currentTimeMillis();
        super.onMove(move);
    }

    @Override
    public void onOpponentDeclinedRematch() {
    }

    @Override
    public void onPause() {
        this._currentTimeOnStop = System.currentTimeMillis();
        AbstractGameModel abstractGameModel = this.getGameAdapter();
        if (abstractGameModel != null) {
            if (abstractGameModel.isGameActive()) {
                this.storeGame((EngineGameAdapter)abstractGameModel);
            }
            abstractGameModel.destroy();
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
                this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.AFTER);
            }
        }
        this._initialStart = false;
        super.onResume();
    }

    @Override
    public void onStartGameFailed(int n) {
        Toast.makeText((Context)this.getActivity(), (CharSequence)"starting game failed", (int)1).show();
        this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.CHOOSING_BEFORE);
    }

    @Override
    public boolean showMenu() {
        return false;
    }

    class 1EngineRunnable
    implements Runnable {
        private EngineGameAdapter.Builder builder;

        public 1EngineRunnable(EngineGameAdapter.Builder builder) {
            this.builder = builder;
        }

        @Override
        public void run() {
            EngineGameAdapter engineGameAdapter = this.builder.create();
            PlayzoneEngineFragment.this.showMenusForPlayzoneState(AbstractPlayzoneFragment.PlayzoneState.PLAYING);
            PlayzoneEngineFragment.this.hideDialogWaiting();
            PlayzoneEngineFragment.this.gameChosen(engineGameAdapter);
        }
    }

}
