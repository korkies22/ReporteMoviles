/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Square;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;

public interface ITacticsExerciseService {
    public void exerciseSolved(TacticsExerciseUserSolution var1, LoadCommandCallback<Rating> var2);

    public void exerciseSolved(TacticsExerciseUserSolution var1, LoadCommandCallback<Rating> var2, boolean var3);

    public void getCurrentSession(LoadCommandCallback<TacticsExerciseSession> var1);

    public void getNextExercise(LoadCommandCallback<TacticsExerciseUserSolution> var1);

    public void getTacticsTrainerInfo(LoadCommandCallback<TacticsTrainerInfo> var1);

    public void getUserExerciseRating(LoadCommandCallback<Rating> var1);

    public Square showHintForPuzzle(TacticsExerciseUserSolution var1);

    public TacticsExerciseSession startNewSession();

    public static class TacticsTrainerInfo {
        private boolean _hasLimit;
        private int _numberOfPuzzlesLeft;
        private long _timeLeft;

        public TacticsTrainerInfo(boolean bl, int n, long l) {
            this._hasLimit = bl;
            this._numberOfPuzzlesLeft = n;
            this._timeLeft = l;
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
