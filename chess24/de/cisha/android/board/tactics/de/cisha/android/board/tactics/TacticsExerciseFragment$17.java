/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import de.cisha.chess.model.exercise.TacticsGameHolder;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ TacticsExerciseUserSolution val$result;

    TacticsExerciseFragment(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this.val$result = tacticsExerciseUserSolution;
    }

    @Override
    public void run() {
        TacticsExerciseFragment.this.hideResultToast();
        TacticsExerciseFragment.this._gameHolder.setNewTacticsPuzzle(this.val$result);
        TacticsExerciseFragment.this.adjustBoardForCurrentExercise();
        TacticsExerciseFragment.this.startExerciseTimer();
    }
}
