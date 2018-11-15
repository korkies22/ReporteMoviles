/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.TacticsExerciseFragment;
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
        12.this.this$0.hideWaitingTimer();
        12.this.this$0._gameHolder = new TacticsGameHolder(this.val$result);
        12.this.this$0.initGameHolderObservers();
        12.this.this$0.adjustBoardForCurrentExercise();
        12.this.this$0.startExerciseTimer();
    }
}
