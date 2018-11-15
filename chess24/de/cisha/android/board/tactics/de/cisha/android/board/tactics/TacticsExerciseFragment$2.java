/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.view.ExerciseHistoryView;
import de.cisha.android.board.tactics.view.SolvedIndicatorView;

class TacticsExerciseFragment
implements Runnable {
    TacticsExerciseFragment() {
    }

    @Override
    public void run() {
        TacticsExerciseFragment.this.setExerciceNo(0);
        TacticsExerciseFragment.this._historyBar.reset();
        TacticsExerciseFragment.this._historyBar.addSolvedExcercise(SolvedIndicatorView.SolveType.RUNNING);
        TacticsExerciseFragment.this.loadFirstExercise();
    }
}
