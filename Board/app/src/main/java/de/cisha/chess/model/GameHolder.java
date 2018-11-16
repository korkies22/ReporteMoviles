// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model;

import java.io.Serializable;
import de.cisha.chess.model.position.MutablePosition;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import de.cisha.chess.model.position.PositionObserver;
import java.util.Set;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.position.PositionObservable;

public class GameHolder implements MovesObservable, MovesEditor, PositionObservable, MoveExecutor, GameStateObservable
{
    private Move _currentMove;
    private Position _currentPosition;
    private Game _game;
    private Set<GameStateObserver> _gameStateObservers;
    private Set<MovesObserver> _moveObservers;
    private Set<PositionObserver> _positionObservers;
    private Square _premoveEnd;
    private Square _premoveStart;
    private boolean _updateObservers;
    
    public GameHolder(final Game newGame) {
        this._updateObservers = true;
        this._positionObservers = Collections.newSetFromMap(new WeakHashMap<PositionObserver, Boolean>());
        this._moveObservers = Collections.newSetFromMap(new WeakHashMap<MovesObserver, Boolean>());
        this._gameStateObservers = Collections.newSetFromMap(new WeakHashMap<GameStateObserver, Boolean>());
        this.setNewGame(newGame);
    }
    
    private void notifyGameStateObservers(final GameStatus gameStatus) {
        if (this._updateObservers) {
            final Iterator<GameStateObserver> iterator = this._gameStateObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().gameStateChanged(gameStatus);
            }
        }
    }
    
    @Override
    public void addGameStateObserver(final GameStateObserver gameStateObserver) {
        this._gameStateObservers.add(gameStateObserver);
    }
    
    public boolean addMove(final MoveContainer moveContainer, final Move move) {
        if (moveContainer.hasChild(move.getSEP())) {
            return false;
        }
        moveContainer.addMove(move);
        this.notifyObserversNewMove(move);
        return true;
    }
    
    @Override
    public void addMovesObserver(final MovesObserver movesObserver) {
        this._moveObservers.add(movesObserver);
    }
    
    @Override
    public void addPositionObserver(final PositionObserver positionObserver) {
        this._positionObservers.add(positionObserver);
    }
    
    @Override
    public boolean advanceOneMoveInCurrentLine() {
        Move currentMove;
        if (this._currentMove == null) {
            currentMove = this._game.getNextMove();
        }
        else {
            currentMove = this._currentMove.getNextMove();
        }
        if (currentMove != null) {
            this._currentMove = currentMove;
            this._currentPosition = currentMove.getResultingPosition();
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
            final Move currentMove = this._currentMove;
            final MoveContainer parent = this._currentMove.getParent();
            this.goBackOneMove();
            parent.removeMove(currentMove);
            this.notifyAllMovesChanged();
            this.notifyMoveChanged();
        }
    }
    
    @Override
    public void deleteUserMoves() {
        for (final Move move : this._game.getChildrenOfAllLevels()) {
            if (move.isUserMove()) {
                move.removeFromParent();
            }
        }
        this._currentMove = this._game.getLastMoveinMainLine();
        Position currentPosition;
        if (this._currentMove == null) {
            currentPosition = this._game.getStartingPosition();
        }
        else {
            currentPosition = this._currentMove.getResultingPosition();
        }
        this._currentPosition = currentPosition;
        this.notifyAllMovesChanged();
        this.notifyPositionObservers();
        this.notifyMoveChanged();
    }
    
    @Override
    public Move doMoveInCurrentPosition(final SEP sep) {
        return this.doMoveInCurrentPosition(sep, false);
    }
    
    protected Move doMoveInCurrentPosition(final SEP sep, final boolean userGenerated) {
        synchronized (this) {
            final MutablePosition mutablePosition = this._currentPosition.getMutablePosition();
            final Move move = mutablePosition.createMoveFrom(sep);
            if (move != null) {
                move.setUserGenerated(userGenerated);
                mutablePosition.doMove(move);
                this._currentPosition = mutablePosition.getSafePosition();
                Serializable s;
                if (this._currentMove == null) {
                    s = this._game;
                }
                else {
                    s = this._currentMove;
                }
                if (((MoveContainer)s).hasChild(move.getSEP())) {
                    this._currentMove = ((MoveContainer)s).getChild(move.getSEP());
                    this.notifyMoveChanged();
                }
                else {
                    ((MoveContainer)s).addMove(move);
                    this.notifyObserversNewMove(move);
                    this._currentMove = move;
                    this.notifyMoveChanged();
                }
                this.notifyPositionObservers();
                if (this._premoveStart != null && this._premoveEnd != null) {
                    if (this._currentPosition.isMovePossible(this._premoveStart, this._premoveEnd)) {
                        final SEP sep2 = new SEP(this._premoveStart, this._premoveEnd, 3);
                        if (sep2 != null) {
                            this.doMoveInCurrentPosition(sep2, true);
                        }
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
    
    public Move getMoveById(final int n) {
        if (this._game != null) {
            for (final Move move : this._game.getChildrenOfAllLevels()) {
                if (move.getMoveId() == n) {
                    return move;
                }
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
            final MoveContainer parent = this._currentMove.getParent();
            if (parent != null && parent instanceof Move) {
                this._currentMove = (Move)parent;
                this._currentPosition = this._currentMove.getResultingPosition();
            }
            else {
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
        Move currentMove;
        if (this._currentMove != null) {
            if (this._currentMove.hasChildren()) {
                currentMove = this._currentMove.getLastMoveinMainLine();
            }
            else {
                currentMove = this._currentMove;
            }
        }
        else {
            currentMove = this._game.getLastMoveinMainLine();
        }
        this._currentMove = currentMove;
        Position currentPosition;
        if (this._currentMove == null) {
            currentPosition = this._game.getStartingPosition();
        }
        else {
            currentPosition = this._currentMove.getResultingPosition();
        }
        this._currentPosition = currentPosition;
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
        for (final MovesObserver movesObserver : this._moveObservers) {
            if (this._updateObservers || !movesObserver.canSkipMoves()) {
                movesObserver.allMovesChanged(this._game);
            }
        }
    }
    
    protected void notifyMoveChanged() {
        if (this._updateObservers) {
            final Iterator<MovesObserver> iterator = this._moveObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().selectedMoveChanged(this._currentMove);
            }
        }
    }
    
    protected void notifyObserversNewMove(final Move move) {
        if (this._updateObservers) {
            final Iterator<MovesObserver> iterator = this._moveObservers.iterator();
            while (iterator.hasNext()) {
                iterator.next().newMove(move);
            }
        }
    }
    
    protected void notifyPositionObservers() {
        if (this._updateObservers) {
            final Iterator<PositionObserver> iterator = this._positionObservers.iterator();
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
    public void registerPremove(final Square premoveStart, final Square premoveEnd) {
        this._premoveStart = premoveStart;
        this._premoveEnd = premoveEnd;
    }
    
    @Override
    public void removeGameStateObserver(final GameStateObserver gameStateObserver) {
        this._gameStateObservers.remove(gameStateObserver);
    }
    
    @Override
    public void removeMovesObserver(final MovesObserver movesObserver) {
        this._moveObservers.remove(movesObserver);
    }
    
    @Override
    public void removePositionObserver(final PositionObserver positionObserver) {
        this._positionObservers.remove(positionObserver);
    }
    
    @Override
    public boolean selectMove(final Move currentMove) {
        this._currentMove = currentMove;
        if (this._currentMove != null) {
            this._currentPosition = currentMove.getResultingPosition();
        }
        else {
            this._currentPosition = this._game.getStartingPosition();
        }
        this.notifyMoveChanged();
        this.notifyPositionObservers();
        return true;
    }
    
    public boolean selectMoveWithId(final int n) {
        final Move moveById = this.getMoveById(n);
        return moveById != null && this.selectMove(moveById);
    }
    
    public void setGameStatus(final GameStatus result) {
        if (this._game != null) {
            this._game.setResult(result);
        }
        this.notifyGameStateObservers(result);
    }
    
    public void setNewGame(final Game game) {
        if (this._game == null || !this._game.equals(game)) {
            Game game2;
            if ((game2 = game) == null) {
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
    
    public void setUpdateObservers(final boolean updateObservers) {
        final boolean b = updateObservers && !this._updateObservers;
        this._updateObservers = updateObservers;
        if (b) {
            this.notifyAllMovesChanged();
            this.notifyMoveChanged();
            this.notifyPositionObservers();
            this.notifyGameStateObservers(this._game.getResult());
        }
    }
}
