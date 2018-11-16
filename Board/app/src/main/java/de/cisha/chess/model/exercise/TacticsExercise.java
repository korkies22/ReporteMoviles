// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.exercise;

import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.SEP;
import java.io.Serializable;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Rating;

public class TacticsExercise
{
    private int _averageTimeInMillis;
    private int _exerciseId;
    private Rating _exerciseRating;
    private Game _game;
    private int _solvingTimeMillis;
    private boolean _userColor;
    
    public TacticsExercise(final Game game, final boolean userColor, final Rating exerciseRating, final int averageTimeInMillis) {
        this._solvingTimeMillis = 60000;
        this._game = game;
        this._userColor = userColor;
        this._exerciseRating = exerciseRating;
        this._averageTimeInMillis = averageTimeInMillis;
    }
    
    private Move getNextMove(final MoveContainer moveContainer, final boolean b) {
        Serializable s = this._game;
        Move move = moveContainer.getFirstMove();
        while (((MoveContainer)s).hasChildren()) {
            if (move == null) {
                if (((MoveContainer)s).getNextMove().getPiece().getColor() != b) {
                    return null;
                }
                return ((MoveContainer)s).getNextMove();
            }
            else {
                final SEP sep = move.getSEP();
                if (!((MoveContainer)s).hasChild(sep)) {
                    return null;
                }
                s = ((MoveContainer)s).getChild(sep);
                move = move.getNextMove();
            }
        }
        return null;
    }
    
    public int getAverageTimeInMillis() {
        return this._averageTimeInMillis;
    }
    
    public Move getComputerMove(final MoveContainer moveContainer) {
        return this.getNextMove(moveContainer, this._userColor ^ true);
    }
    
    public int getExerciseId() {
        return this._exerciseId;
    }
    
    public Rating getExerciseRating() {
        return this._exerciseRating;
    }
    
    public Game getGame() {
        return this._game;
    }
    
    public Square getHint(final MoveContainer moveContainer) {
        final Move nextMove = this.getNextMove(moveContainer, this._userColor);
        if (nextMove != null) {
            return nextMove.getSquareFrom();
        }
        return null;
    }
    
    public int getSolvingTimeMillis() {
        return this._solvingTimeMillis;
    }
    
    public Position getStartingPosition() {
        return this._game.getStartingPosition();
    }
    
    public boolean getUserColor() {
        return this._userColor;
    }
    
    public TacticsExerciseUserState isSolution(final MoveContainer moveContainer) {
        Serializable s = this._game;
        Move move = moveContainer.getFirstMove();
        while (((MoveContainer)s).hasChildren()) {
            if (move == null) {
                if (((MoveContainer)s).getNextMove().getPiece().getColor() == this._userColor) {
                    return TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_GET_USERMOVE;
                }
                return TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_DO_COMPUTERMOVE;
            }
            else {
                final SEP sep = move.getSEP();
                if (!((MoveContainer)s).hasChild(sep)) {
                    return TacticsExerciseUserState.SOLVED_INCORRECT;
                }
                s = ((MoveContainer)s).getChild(sep);
                move = move.getNextMove();
            }
        }
        return TacticsExerciseUserState.SOLVED_CORRECT;
    }
    
    public void setExerciseId(final int exerciseId) {
        this._exerciseId = exerciseId;
    }
    
    public void setSolvingTimeMillis(final int solvingTimeMillis) {
        this._solvingTimeMillis = solvingTimeMillis;
    }
}
