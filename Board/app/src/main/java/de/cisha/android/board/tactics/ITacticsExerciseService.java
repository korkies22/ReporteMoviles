// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics;

import de.cisha.chess.model.Square;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.Rating;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;

public interface ITacticsExerciseService
{
    void exerciseSolved(final TacticsExerciseUserSolution p0, final LoadCommandCallback<Rating> p1);
    
    void exerciseSolved(final TacticsExerciseUserSolution p0, final LoadCommandCallback<Rating> p1, final boolean p2);
    
    void getCurrentSession(final LoadCommandCallback<TacticsExerciseSession> p0);
    
    void getNextExercise(final LoadCommandCallback<TacticsExerciseUserSolution> p0);
    
    void getTacticsTrainerInfo(final LoadCommandCallback<TacticsTrainerInfo> p0);
    
    void getUserExerciseRating(final LoadCommandCallback<Rating> p0);
    
    Square showHintForPuzzle(final TacticsExerciseUserSolution p0);
    
    TacticsExerciseSession startNewSession();
    
    public static class TacticsTrainerInfo
    {
        private boolean _hasLimit;
        private int _numberOfPuzzlesLeft;
        private long _timeLeft;
        
        public TacticsTrainerInfo(final boolean hasLimit, final int numberOfPuzzlesLeft, final long timeLeft) {
            this._hasLimit = hasLimit;
            this._numberOfPuzzlesLeft = numberOfPuzzlesLeft;
            this._timeLeft = timeLeft;
        }
        
        public int getNumberOfPuzzlesLeft() {
            return this._numberOfPuzzlesLeft;
        }
        
        public long getTimeLeft() {
            return this._timeLeft;
        }
        
        public boolean isHasLimit() {
            return this._hasLimit;
        }
    }
}
