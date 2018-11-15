/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;

class TacticsExerciseFragment
implements IErrorPresenter.IReloadAction {
    TacticsExerciseFragment() {
    }

    @Override
    public void performReload() {
        2.this.this$1.this$0.showExerciseResult(2.this.this$1.val$solution.isCorrect());
        2.this.this$1.this$0.loadExerciseSolved(2.this.this$1.val$solution);
    }
}
