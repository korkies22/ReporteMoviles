/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.chess.model.exercise.TacticsExerciseUserSolution;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ TacticsExerciseUserSolution val$result;

    TacticsExerciseFragment(TacticsExerciseUserSolution tacticsExerciseUserSolution) {
        this.val$result = tacticsExerciseUserSolution;
    }

    @Override
    public void run() {
        16.this.this$0.hideExerciseResultAndStartNextExercise(this.val$result);
    }
}
