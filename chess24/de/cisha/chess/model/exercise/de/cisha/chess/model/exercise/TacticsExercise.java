/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.exercise;

import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.exercise.TacticsExerciseUserState;
import de.cisha.chess.model.position.Position;

public class TacticsExercise {
    private int _averageTimeInMillis;
    private int _exerciseId;
    private Rating _exerciseRating;
    private Game _game;
    private int _solvingTimeMillis = 60000;
    private boolean _userColor;

    public TacticsExercise(Game game, boolean bl, Rating rating, int n) {
        this._game = game;
        this._userColor = bl;
        this._exerciseRating = rating;
        this._averageTimeInMillis = n;
    }

    private Move getNextMove(MoveContainer moveContainer, boolean bl) {
        AbstractMoveContainer abstractMoveContainer = this._game;
        moveContainer = moveContainer.getFirstMove();
        while (abstractMoveContainer.hasChildren()) {
            if (moveContainer == null) {
                if (abstractMoveContainer.getNextMove().getPiece().getColor() != bl) {
                    return null;
                }
                return abstractMoveContainer.getNextMove();
            }
            SEP sEP = moveContainer.getSEP();
            if (abstractMoveContainer.hasChild(sEP)) {
                abstractMoveContainer = abstractMoveContainer.getChild(sEP);
                moveContainer = ((AbstractMoveContainer)moveContainer).getNextMove();
                continue;
            }
            return null;
        }
        return null;
    }

    public int getAverageTimeInMillis() {
        return this._averageTimeInMillis;
    }

    public Move getComputerMove(MoveContainer moveContainer) {
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

    public Square getHint(MoveContainer moveContainer) {
        if ((moveContainer = this.getNextMove(moveContainer, this._userColor)) != null) {
            return moveContainer.getSquareFrom();
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

    public TacticsExerciseUserState isSolution(MoveContainer moveContainer) {
        AbstractMoveContainer abstractMoveContainer = this._game;
        moveContainer = moveContainer.getFirstMove();
        while (abstractMoveContainer.hasChildren()) {
            if (moveContainer == null) {
                if (abstractMoveContainer.getNextMove().getPiece().getColor() == this._userColor) {
                    return TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_GET_USERMOVE;
                }
                return TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_DO_COMPUTERMOVE;
            }
            SEP sEP = moveContainer.getSEP();
            if (abstractMoveContainer.hasChild(sEP)) {
                abstractMoveContainer = abstractMoveContainer.getChild(sEP);
                moveContainer = ((AbstractMoveContainer)moveContainer).getNextMove();
                continue;
            }
            return TacticsExerciseUserState.SOLVED_INCORRECT;
        }
        return TacticsExerciseUserState.SOLVED_CORRECT;
    }

    public void setExerciseId(int n) {
        this._exerciseId = n;
    }

    public void setSolvingTimeMillis(int n) {
        this._solvingTimeMillis = n;
    }
}
