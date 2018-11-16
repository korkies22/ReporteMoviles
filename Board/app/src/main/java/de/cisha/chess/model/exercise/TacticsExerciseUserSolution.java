// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.model.exercise;

import de.cisha.chess.model.Square;
import java.util.Iterator;
import java.util.List;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Game;

public class TacticsExerciseUserSolution
{
    private TacticsExercise _exercise;
    private Game _game;
    private TacticsExerciseSolutionInfo _info;
    private MoveContainer _lastMove;
    private TacticsExerciseUserState _state;
    private int _timeUsed;
    
    public TacticsExerciseUserSolution(final TacticsExercise exercise) {
        this._state = TacticsExerciseUserState.PARTIALLY_SOLVED_CORRECT_GET_USERMOVE;
        this._timeUsed = 0;
        this._exercise = exercise;
        (this._game = new Game()).setEvent("Tactics");
        this._game.setStartingPosition(this._exercise.getStartingPosition());
        this._state = this._exercise.isSolution(this._game);
        this._lastMove = this._game;
        this._timeUsed = 0;
    }
    
    public TacticsExerciseUserState addSolutionMove(final SEP sep) {
        final Move move = this._lastMove.getResultingPosition().createMoveFrom(sep);
        if (move != null) {
            this._lastMove.addMove(move);
            this._state = this._exercise.isSolution(this._game);
            this._lastMove = move;
        }
        else {
            this._state = TacticsExerciseUserState.SOLVED_INCORRECT;
        }
        return this._state;
    }
    
    public SEP getCurrentComputerMove() {
        final Move computerMove = this._exercise.getComputerMove(this.getGame());
        if (computerMove != null) {
            return computerMove.getSEP();
        }
        return null;
    }
    
    public TacticsExercise getExercise() {
        return this._exercise;
    }
    
    public Game getExerciseGameIncludingUserMoves() {
        final Game copy = this._exercise.getGame().copy();
        Move move = this.getGame().getFirstMove();
        Game game = copy;
        while (move != null) {
            final SEP sep = move.getSEP();
            final Move child = game.getChild(sep);
            if (child == null) {
                final Move move2 = game.getResultingPosition().createMoveFrom(sep);
                move2.setUserGenerated(true);
                game.addMove(move2);
                game = (Game)move2;
            }
            else {
                game = (Game)child;
            }
            move = move.getNextMove();
        }
        return copy;
    }
    
    public Move getFailingMove() {
        final boolean correct = this.isCorrect();
        final Move move = null;
        if (correct) {
            return null;
        }
        final List<Move> childrenOfAllLevels = this.getExerciseGameIncludingUserMoves().getChildrenOfAllLevels();
        Move move2 = move;
        if (childrenOfAllLevels != null) {
            final Iterator<Move> iterator = childrenOfAllLevels.iterator();
            do {
                move2 = move;
                if (!iterator.hasNext()) {
                    break;
                }
                move2 = iterator.next();
            } while (!move2.isUserMove());
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
        return this._state == TacticsExerciseUserState.SOLVED_CORRECT || this._state == TacticsExerciseUserState.SOLVED_INCORRECT;
    }
    
    public boolean isCorrect() {
        return this._state == TacticsExerciseUserState.SOLVED_CORRECT;
    }
    
    public void setInfo(final TacticsExerciseSolutionInfo info) {
        this._info = info;
    }
    
    public void setTimeUsed(final int timeUsed) {
        this._timeUsed = timeUsed;
    }
}
