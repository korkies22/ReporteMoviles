/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.model.exercise;

import de.cisha.chess.model.AbstractMoveContainer;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.exercise.TacticsExercise;
import de.cisha.chess.model.exercise.TacticsExerciseSolutionInfo;
import de.cisha.chess.model.exercise.TacticsExerciseUserState;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class TacticsExerciseUserSolution {
    private TacticsExercise _exercise;
    private Game _game;
    private TacticsExerciseSolutionInfo _info;
    private MoveContainer _lastMove;
    private TacticsExerciseUserState _state = TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_GET_USERMOVE;
    private int _timeUsed = 0;

    public TacticsExerciseUserSolution(TacticsExercise tacticsExercise) {
        this._exercise = tacticsExercise;
        this._game = new Game();
        this._game.setEvent("Tactics");
        this._game.setStartingPosition(this._exercise.getStartingPosition());
        this._state = this._exercise.isSolution(this._game);
        this._lastMove = this._game;
        this._timeUsed = 0;
    }

    public TacticsExerciseUserState addSolutionMove(SEP serializable) {
        serializable = this._lastMove.getResultingPosition().createMoveFrom((SEP)serializable);
        if (serializable != null) {
            this._lastMove.addMove((Move)serializable);
            this._state = this._exercise.isSolution(this._game);
            this._lastMove = serializable;
        } else {
            this._state = TacticsExerciseUserState.SOLVED_INCORRECT;
        }
        return this._state;
    }

    public SEP getCurrentComputerMove() {
        Move move = this._exercise.getComputerMove(this.getGame());
        if (move != null) {
            return move.getSEP();
        }
        return null;
    }

    public TacticsExercise getExercise() {
        return this._exercise;
    }

    public Game getExerciseGameIncludingUserMoves() {
        AbstractMoveContainer abstractMoveContainer = this._exercise.getGame().copy();
        AbstractMoveContainer abstractMoveContainer2 = abstractMoveContainer;
        for (Move move = this.getGame().getFirstMove(); move != null; move = move.getNextMove()) {
            SEP sEP = move.getSEP();
            Move move2 = abstractMoveContainer2.getChild(sEP);
            if (move2 == null) {
                move2 = abstractMoveContainer2.getResultingPosition().createMoveFrom(sEP);
                move2.setUserGenerated(true);
                abstractMoveContainer2.addMove(move2);
                abstractMoveContainer2 = move2;
                continue;
            }
            abstractMoveContainer2 = move2;
        }
        return abstractMoveContainer;
    }

    public Move getFailingMove() {
        boolean bl = this.isCorrect();
        Move move = null;
        if (bl) {
            return null;
        }
        Object object = this.getExerciseGameIncludingUserMoves().getChildrenOfAllLevels();
        Move move2 = move;
        if (object != null) {
            object = object.iterator();
            do {
                move2 = move;
            } while (object.hasNext() && !(move2 = (Move)object.next()).isUserMove());
        }
        return move2;
    }

    public Game getGame() {
        return this._game;
    }

    @Deprecated
    public Square getHint() {
        return this._exercise.getHint(this._game);
    }

    public TacticsExerciseSolutionInfo getInfo() {
        return this._info;
    }

    public TacticsExerciseUserState getState() {
        return this._state;
    }

    public int getTimeUsed() {
        return this._timeUsed;
    }

    public boolean isCompleted() {
        if (this._state != TacticsExerciseUserState.SOLVED_CORRECT && this._state != TacticsExerciseUserState.SOLVED_INCORRECT) {
            return false;
        }
        return true;
    }

    public boolean isCorrect() {
        if (this._state == TacticsExerciseUserState.SOLVED_CORRECT) {
            return true;
        }
        return false;
    }

    public void setInfo(TacticsExerciseSolutionInfo tacticsExerciseSolutionInfo) {
        this._info = tacticsExerciseSolutionInfo;
    }

    public void setTimeUsed(int n) {
        this._timeUsed = n;
    }
}
