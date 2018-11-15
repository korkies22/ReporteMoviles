/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.view.ExerciseHistoryView;
import de.cisha.android.board.tactics.view.SolvedIndicatorView;
import de.cisha.chess.model.exercise.TacticsExerciseSession;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;
import java.util.List;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ boolean val$correct;
    final /* synthetic */ TacticsExerciseUserSolution val$solution;

    TacticsExerciseFragment(boolean bl, TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this.val$correct = bl;
        this.val$solution = tacticsExerciseUserSolution;
    }

    @Override
    public void run() {
        TacticsExerciseFragment.this.stopExerciseTimer();
        ExerciseHistoryView exerciseHistoryView = TacticsExerciseFragment.this._historyBar;
        SolvedIndicatorView.SolveType solveType = this.val$correct ? SolvedIndicatorView.SolveType.CORRECT : SolvedIndicatorView.SolveType.INCORRECT;
        exerciseHistoryView.addSolvedExcercise(solveType);
        TacticsExerciseFragment.this.setExerciceNo(TacticsExerciseFragment.this._session.getExercises().size());
        TacticsExerciseFragment.this.showExerciseResult(this.val$solution.isCorrect());
    }
}
