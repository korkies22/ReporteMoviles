/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.model.ClockListener;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IChessGameConnection;
import de.cisha.android.board.playzone.model.IGameDelegate;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayZoneChessClock;
import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndChecker;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;

public abstract class AbstractGameModel
implements ClockListener,
IGameDelegate {
    protected ChessClock _clock;
    protected IGameModelDelegate _delegate;
    protected Game _game;
    private GameEndChecker _gameEndChecker;
    protected PlayzoneGameHolder _gameHolder;
    private MyMovesObserver _myMovesObserver;
    protected boolean _playerIsWhite = true;

    protected AbstractGameModel(IGameModelDelegate iGameModelDelegate, boolean bl) {
        this._delegate = iGameModelDelegate;
        this._playerIsWhite = bl;
        this._game = new Game();
        this._gameHolder = new PlayzoneGameHolder(this._game, this.playerIsWhite());
        this.setDelegate(iGameModelDelegate);
        this._gameEndChecker = new GameEndChecker(null);
        this._myMovesObserver = new MyMovesObserver();
        this._gameHolder.addMovesObserver(this._myMovesObserver);
    }

    protected void addClockDelegate() {
        ChessClock chessClock = this.getClock();
        if (chessClock != null) {
            chessClock.addOnClockListener(this);
            chessClock.addOnClockListener(this._delegate);
        }
    }

    public void destroy() {
        if (this._gameHolder != null) {
            this._gameHolder.setGameActive(false);
            this.removeDelegate();
            this.getClock().stop();
            this.getClock().removeClockListener(this);
        }
        if (this.getConnection() != null) {
            this.getConnection().disconnect();
        }
    }

    public PlayZoneChessClock getChessClock() {
        if (this._gameHolder != null) {
            return this._gameHolder.getClock();
        }
        return null;
    }

    protected ChessClock getClock() {
        return this._clock;
    }

    protected abstract IChessGameConnection getConnection();

    public Game getGame() {
        return this._game;
    }

    public Opponent getLocalPlayer() {
        return this.getPlayerInfo(this._playerIsWhite);
    }

    public MoveExecutor getMoveExecutor() {
        return this._gameHolder;
    }

    public MovesObservable getMovesObservable() {
        return this._gameHolder;
    }

    public Opponent getOpponent() {
        return this.getPlayerInfo(this._playerIsWhite ^ true);
    }

    public Opponent getPlayerInfo(boolean bl) {
        Opponent opponent = this._game != null ? (bl ? this._game.getWhitePlayer() : this._game.getBlackPlayer()) : null;
        Opponent opponent2 = opponent;
        if (opponent == null) {
            opponent2 = new Opponent();
        }
        return opponent2;
    }

    public Position getPosition() {
        return this._gameHolder.getPosition();
    }

    public PositionObservable getPositionObservable() {
        return this._gameHolder;
    }

    public MoveContainer getRootMoveContainer() {
        if (this._gameHolder != null) {
            return this._gameHolder.getRootMoveContainer();
        }
        return null;
    }

    public boolean isAbortable() {
        if (this.getPosition().getHalfMoveNumber() < 2) {
            return true;
        }
        return false;
    }

    public boolean isGameActive() {
        if (this._gameHolder != null && this._gameHolder.isGameActive()) {
            return true;
        }
        return false;
    }

    public abstract boolean isRemoteGame();

    public abstract void offerOrAcceptDraw();

    @Override
    public void onClockChanged(long l, boolean bl) {
    }

    @Override
    public void onClockFlag(boolean bl) {
        GameResult gameResult = bl ? GameResult.BLACK_WINS : GameResult.WHITE_WINS;
        this.onGameEndDetected(new GameEndInformation(new GameStatus(gameResult, GameEndReason.CLOCK_FLAG), this.getPosition().getActiveColor()));
    }

    @Override
    public void onClockStarted() {
    }

    @Override
    public void onClockStopped() {
    }

    @Override
    public void onGameStart(Game game, int n, int n2) {
        this._game = game;
        this._gameHolder.setNewGame(game);
        if (this._clock != null) {
            this._clock.removeAllClockListeners();
            this._clock.stop();
        }
        this._clock = this._gameHolder.getClock();
        this.addClockDelegate();
        this._gameHolder.gotoEndingPosition();
        this._gameHolder.setGameActive(true);
        this._delegate.onGameStart();
    }

    public boolean opponentsDrawOfferPending() {
        if (this._gameHolder != null && this._gameHolder.opponentsDrawOfferPending()) {
            return true;
        }
        return false;
    }

    public abstract boolean playWithChessClocks();

    public boolean playerIsWhite() {
        return this._playerIsWhite;
    }

    public boolean playersDrawOfferPending() {
        if (this._gameHolder != null && this._gameHolder.playersDrawOfferPending()) {
            return true;
        }
        return false;
    }

    public void removeDelegate() {
        this.getClock().removeClockListener(this._delegate);
        this._delegate = new IGameModelDelegate(){

            @Override
            public void onClockChanged(long l, boolean bl) {
            }

            @Override
            public void onClockFlag(boolean bl) {
            }

            @Override
            public void onClockStarted() {
            }

            @Override
            public void onClockStopped() {
            }

            @Override
            public void onGameEnd(GameEndInformation gameEndInformation) {
            }

            @Override
            public void onGameSessionEnded(GameStatus gameStatus) {
            }

            @Override
            public void onGameSessionOver(Game game, GameEndInformation gameEndInformation) {
            }

            @Override
            public void onGameStart() {
            }

            @Override
            public void onMove(Move move) {
            }

            @Override
            public void onOpponentDeclinedRematch() {
            }

            @Override
            public void onReceiveDrawOffer() {
            }

            @Override
            public void onReceivedRematchOffer() {
            }

            @Override
            public void onStartGameFailed(int n) {
            }
        };
        this.getChessClock().removeClockListener(this);
    }

    public void requestAbort() {
        this.getConnection().requestAbort();
    }

    public abstract void requestRematch();

    public void resignGame() {
        this.getConnection().resign();
    }

    public void setDelegate(IGameModelDelegate iGameModelDelegate) {
        this._delegate = iGameModelDelegate;
        this.addClockDelegate();
    }

    public void startUp() {
        this.addClockDelegate();
        this._gameHolder.gotoEndingPosition();
    }

    private final class MyMovesObserver
    implements MovesObserver {
        private MyMovesObserver() {
        }

        @Override
        public void allMovesChanged(MoveContainer moveContainer) {
        }

        @Override
        public boolean canSkipMoves() {
            return false;
        }

        @Override
        public void newMove(Move move) {
            long l = AbstractGameModel.this.getClock().getTimeSinceLastMove();
            boolean bl = move.getPiece().isWhite();
            move.setMoveTimeInMills((int)l);
            Object object = AbstractGameModel.this._gameEndChecker.checkForAutomaticGameEnd(move);
            if (!GameStatus.GAME_RUNNING.equals(object)) {
                object = new GameEndInformation((GameStatus)object, AbstractGameModel.this.getPosition().getActiveColor());
                AbstractGameModel.this.onGameEndDetected((GameEndInformation)object);
            }
            if (AbstractGameModel.this.playerIsWhite() == bl) {
                AbstractGameModel.this.getConnection().doMove(move);
            }
            AbstractGameModel.this._delegate.onMove(move);
        }

        @Override
        public void selectedMoveChanged(Move move) {
        }
    }

}
