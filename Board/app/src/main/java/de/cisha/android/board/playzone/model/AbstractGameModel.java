// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.Move;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.GameEndChecker;
import de.cisha.chess.model.Game;

public abstract class AbstractGameModel implements ClockListener, IGameDelegate
{
    protected ChessClock _clock;
    protected IGameModelDelegate _delegate;
    protected Game _game;
    private GameEndChecker _gameEndChecker;
    protected PlayzoneGameHolder _gameHolder;
    private MyMovesObserver _myMovesObserver;
    protected boolean _playerIsWhite;
    
    protected AbstractGameModel(final IGameModelDelegate gameModelDelegate, final boolean playerIsWhite) {
        this._playerIsWhite = true;
        this._delegate = gameModelDelegate;
        this._playerIsWhite = playerIsWhite;
        this._game = new Game();
        this._gameHolder = new PlayzoneGameHolder(this._game, this.playerIsWhite());
        this.setDelegate(gameModelDelegate);
        this._gameEndChecker = new GameEndChecker(null);
        this._myMovesObserver = new MyMovesObserver();
        this._gameHolder.addMovesObserver(this._myMovesObserver);
    }
    
    protected void addClockDelegate() {
        final ChessClock clock = this.getClock();
        if (clock != null) {
            clock.addOnClockListener(this);
            clock.addOnClockListener(this._delegate);
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
    
    public Opponent getPlayerInfo(final boolean b) {
        Opponent opponent;
        if (this._game != null) {
            if (b) {
                opponent = this._game.getWhitePlayer();
            }
            else {
                opponent = this._game.getBlackPlayer();
            }
        }
        else {
            opponent = null;
        }
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
        return this.getPosition().getHalfMoveNumber() < 2;
    }
    
    public boolean isGameActive() {
        return this._gameHolder != null && this._gameHolder.isGameActive();
    }
    
    public abstract boolean isRemoteGame();
    
    public abstract void offerOrAcceptDraw();
    
    @Override
    public void onClockChanged(final long n, final boolean b) {
    }
    
    @Override
    public void onClockFlag(final boolean b) {
        GameResult gameResult;
        if (b) {
            gameResult = GameResult.BLACK_WINS;
        }
        else {
            gameResult = GameResult.WHITE_WINS;
        }
        this.onGameEndDetected(new GameEndInformation(new GameStatus(gameResult, GameEndReason.CLOCK_FLAG), this.getPosition().getActiveColor()));
    }
    
    @Override
    public void onClockStarted() {
    }
    
    @Override
    public void onClockStopped() {
    }
    
    @Override
    public void onGameStart(final Game game, final int n, final int n2) {
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
        return this._gameHolder != null && this._gameHolder.opponentsDrawOfferPending();
    }
    
    public abstract boolean playWithChessClocks();
    
    public boolean playerIsWhite() {
        return this._playerIsWhite;
    }
    
    public boolean playersDrawOfferPending() {
        return this._gameHolder != null && this._gameHolder.playersDrawOfferPending();
    }
    
    public void removeDelegate() {
        this.getClock().removeClockListener(this._delegate);
        this._delegate = new IGameModelDelegate() {
            @Override
            public void onClockChanged(final long n, final boolean b) {
            }
            
            @Override
            public void onClockFlag(final boolean b) {
            }
            
            @Override
            public void onClockStarted() {
            }
            
            @Override
            public void onClockStopped() {
            }
            
            @Override
            public void onGameEnd(final GameEndInformation gameEndInformation) {
            }
            
            @Override
            public void onGameSessionEnded(final GameStatus gameStatus) {
            }
            
            @Override
            public void onGameSessionOver(final Game game, final GameEndInformation gameEndInformation) {
            }
            
            @Override
            public void onGameStart() {
            }
            
            @Override
            public void onMove(final Move move) {
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
            public void onStartGameFailed(final int n) {
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
    
    public void setDelegate(final IGameModelDelegate delegate) {
        this._delegate = delegate;
        this.addClockDelegate();
    }
    
    public void startUp() {
        this.addClockDelegate();
        this._gameHolder.gotoEndingPosition();
    }
    
    private final class MyMovesObserver implements MovesObserver
    {
        @Override
        public void allMovesChanged(final MoveContainer moveContainer) {
        }
        
        @Override
        public boolean canSkipMoves() {
            return false;
        }
        
        @Override
        public void newMove(final Move move) {
            final long timeSinceLastMove = AbstractGameModel.this.getClock().getTimeSinceLastMove();
            final boolean white = move.getPiece().isWhite();
            move.setMoveTimeInMills((int)timeSinceLastMove);
            final GameStatus checkForAutomaticGameEnd = AbstractGameModel.this._gameEndChecker.checkForAutomaticGameEnd(move);
            if (!GameStatus.GAME_RUNNING.equals(checkForAutomaticGameEnd)) {
                AbstractGameModel.this.onGameEndDetected(new GameEndInformation(checkForAutomaticGameEnd, AbstractGameModel.this.getPosition().getActiveColor()));
            }
            if (AbstractGameModel.this.playerIsWhite() == white) {
                AbstractGameModel.this.getConnection().doMove(move);
            }
            AbstractGameModel.this._delegate.onMove(move);
        }
        
        @Override
        public void selectedMoveChanged(final Move move) {
        }
    }
}
