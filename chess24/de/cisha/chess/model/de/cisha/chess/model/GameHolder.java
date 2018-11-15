/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model;

import de.cisha.chess.model.BaseObject;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStateObservable;
import de.cisha.chess.model.GameStateObserver;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MoveExecutor;
import de.cisha.chess.model.MovesEditor;
import de.cisha.chess.model.MovesObservable;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.position.MutablePosition;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;
import de.cisha.chess.model.position.PositionObserver;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

public class GameHolder
implements MovesObservable,
MovesEditor,
PositionObservable,
MoveExecutor,
GameStateObservable {
    private Move _currentMove;
    private Position _currentPosition;
    private Game _game;
    private Set<GameStateObserver> _gameStateObservers = Collections.newSetFromMap(new WeakHashMap());
    private Set<MovesObserver> _moveObservers = Collections.newSetFromMap(new WeakHashMap());
    private Set<PositionObserver> _positionObservers = Collections.newSetFromMap(new WeakHashMap());
    private Square _premoveEnd;
    private Square _premoveStart;
    private boolean _updateObservers = true;

    public GameHolder(Game game) {
        this.setNewGame(game);
    }

    private void notifyGameStateObservers(GameStatus gameStatus) {
        if (this._updateObservers) {
            Iterator<GameStateObserver> iterator = this._gameStateObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().gameStateChanged(gameStatus);
            }
        }
    }

    @Override
    public void addGameStateObserver(GameStateObserver gameStateObserver) {
        this._gameStateObservers.add(gameStateObserver);
    }

    public boolean addMove(MoveContainer moveContainer, Move move) {
        if (moveContainer.hasChild(move.getSEP())) {
            return false;
        }
        moveContainer.addMove(move);
        this.notifyObserversNewMove(move);
        return true;
    }

    @Override
    public void addMovesObserver(MovesObserver movesObserver) {
        this._moveObservers.add(movesObserver);
    }

    @Override
    public void addPositionObserver(PositionObserver positionObserver) {
        this._positionObservers.add(positionObserver);
    }

    @Override
    public boolean advanceOneMoveInCurrentLine() {
        Move move = this._currentMove == null ? this._game.getNextMove() : this._currentMove.getNextMove();
        if (move != null) {
            this._currentMove = move;
            this._currentPosition = move.getResultingPosition();
            this.notifyPositionObservers();
            this.notifyMoveChanged();
            return true;
        }
        return false;
    }

    public void clearObservers() {
        this._positionObservers.clear();
        this._moveObservers.clear();
        this._gameStateObservers.clear();
    }

    @Override
    public void deleteCurrentMove() {
        if (this._currentMove != null) {
            Move move = this._currentMove;
            MoveContainer moveContainer = this._currentMove.getParent();
            this.goBackOneMove();
            moveContainer.removeMove(move);
            this.notifyAllMovesChanged();
            this.notifyMoveChanged();
        }
    }

    @Override
    public void deleteUserMoves() {
        for (Move move : this._game.getChildrenOfAllLevels()) {
            if (!move.isUserMove()) continue;
            move.removeFromParent();
        }
        this._currentMove = this._game.getLastMoveinMainLine();
        Position position = this._currentMove == null ? this._game.getStartingPosition() : this._currentMove.getResultingPosition();
        this._currentPosition = position;
        this.notifyAllMovesChanged();
        this.notifyPositionObservers();
        this.notifyMoveChanged();
    }

    @Override
    public Move doMoveInCurrentPosition(SEP sEP) {
        return this.doMoveInCurrentPosition(sEP, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Move doMoveInCurrentPosition(SEP serializable, boolean bl) {
        synchronized (this) {
            MutablePosition mutablePosition = this._currentPosition.getMutablePosition();
            Move move = mutablePosition.createMoveFrom((SEP)serializable);
            if (move != null) {
                move.setUserGenerated(bl);
                mutablePosition.doMove(move);
                this._currentPosition = mutablePosition.getSafePosition();
                serializable = this._currentMove == null ? this._game : this._currentMove;
                if (serializable.hasChild(move.getSEP())) {
                    this._currentMove = serializable.getChild(move.getSEP());
                    this.notifyMoveChanged();
                } else {
                    serializable.addMove(move);
                    this.notifyObserversNewMove(move);
                    this._currentMove = move;
                    this.notifyMoveChanged();
                }
                this.notifyPositionObservers();
                if (this._premoveStart != null && this._premoveEnd != null) {
                    if (this._currentPosition.isMovePossible(this._premoveStart, this._premoveEnd) && (serializable = new SEP(this._premoveStart, this._premoveEnd, 3)) != null) {
                        this.doMoveInCurrentPosition((SEP)serializable, true);
                    }
                    this._premoveStart = null;
                    this._premoveEnd = null;
                }
            }
            return move;
        }
    }

    @Override
    public Move getCurrentMove() {
        return this._currentMove;
    }

    @Override
    public FEN getFEN() {
        return this._currentPosition.getFEN();
    }

    @Override
    public GameStatus getGameStatus() {
        if (this._game != null) {
            return this._game.getResult();
        }
        return null;
    }

    public Move getMoveById(int n) {
        if (this._game != null) {
            for (Move move : this._game.getChildrenOfAllLevels()) {
                if (move.getMoveId() != n) continue;
                return move;
            }
        }
        return null;
    }

    @Override
    public Position getPosition() {
        return this._currentPosition;
    }

    @Override
    public MoveContainer getRootMoveContainer() {
        return this._game;
    }

    @Override
    public boolean goBackOneMove() {
        if (this._currentMove != null) {
            MoveContainer moveContainer = this._currentMove.getParent();
            if (moveContainer != null && moveContainer instanceof Move) {
                this._currentMove = (Move)moveContainer;
                this._currentPosition = this._currentMove.getResultingPosition();
            } else {
                this._currentMove = null;
                this._currentPosition = this._game.getStartingPosition();
            }
            this.notifyPositionObservers();
            this.notifyMoveChanged();
            return true;
        }
        return false;
    }

    @Override
    public void gotoEndingPosition() {
        BaseObject baseObject = this._currentMove != null ? (this._currentMove.hasChildren() ? this._currentMove.getLastMoveinMainLine() : this._currentMove) : this._game.getLastMoveinMainLine();
        this._currentMove = baseObject;
        baseObject = this._currentMove == null ? this._game.getStartingPosition() : this._currentMove.getResultingPosition();
        this._currentPosition = baseObject;
        this.notifyPositionObservers();
        this.notifyMoveChanged();
    }

    @Override
    public boolean gotoStartingPosition() {
        this._currentMove = null;
        this._currentPosition = this._game.getStartingPosition();
        this.notifyPositionObservers();
        this.notifyMoveChanged();
        return true;
    }

    protected void notifyAllMovesChanged() {
        for (MovesObserver movesObserver : this._moveObservers) {
            if (!this._updateObservers && movesObserver.canSkipMoves()) continue;
            movesObserver.allMovesChanged(this._game);
        }
    }

    protected void notifyMoveChanged() {
        if (this._updateObservers) {
            Iterator<MovesObserver> iterator = this._moveObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().selectedMoveChanged(this._currentMove);
            }
        }
    }

    protected void notifyObserversNewMove(Move move) {
        if (this._updateObservers) {
            Iterator<MovesObserver> iterator = this._moveObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().newMove(move);
            }
        }
    }

    protected void notifyPositionObservers() {
        if (this._updateObservers) {
            Iterator<PositionObserver> iterator = this._positionObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().positionChanged(this._currentPosition, this._currentMove);
            }
        }
    }

    @Override
    public void promoteCurrentMove() {
        if (this._currentMove != null) {
            this._currentMove.getParent().promoteMove(this._currentMove);
            this.notifyAllMovesChanged();
            this.notifyMoveChanged();
        }
    }

    @Override
    public void registerPremove(Square square, Square square2) {
        this._premoveStart = square;
        this._premoveEnd = square2;
    }

    @Override
    public void removeGameStateObserver(GameStateObserver gameStateObserver) {
        this._gameStateObservers.remove(gameStateObserver);
    }

    @Override
    public void removeMovesObserver(MovesObserver movesObserver) {
        this._moveObservers.remove(movesObserver);
    }

    @Override
    public void removePositionObserver(PositionObserver positionObserver) {
        this._positionObservers.remove(positionObserver);
    }

    @Override
    public boolean selectMove(Move move) {
        this._currentMove = move;
        this._currentPosition = this._currentMove != null ? move.getResultingPosition() : this._game.getStartingPosition();
        this.notifyMoveChanged();
        this.notifyPositionObservers();
        return true;
    }

    public boolean selectMoveWithId(int n) {
        Move move = this.getMoveById(n);
        if (move != null) {
            return this.selectMove(move);
        }
        return false;
    }

    public void setGameStatus(GameStatus gameStatus) {
        if (this._game != null) {
            this._game.setResult(gameStatus);
        }
        this.notifyGameStateObservers(gameStatus);
    }

    public void setNewGame(Game game) {
        if (this._game == null || !this._game.equals(game)) {
            Game game2 = game;
            if (game == null) {
                game2 = new Game();
            }
            this._game = game2;
            this._currentPosition = this._game.getStartingPosition();
            this._currentMove = null;
            this._premoveEnd = null;
            this._premoveStart = null;
            this.notifyAllMovesChanged();
            this.notifyPositionObservers();
            this.notifyGameStateObservers(this._game.getResult());
        }
    }

    public void setUpdateObservers(boolean bl) {
        boolean bl2 = bl && !this._updateObservers;
        this._updateObservers = bl;
        if (bl2) {
            this.notifyAllMovesChanged();
            this.notifyMoveChanged();
            this.notifyPositionObservers();
            this.notifyGameStateObservers(this._game.getResult());
        }
    }
}
