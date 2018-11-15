/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.TacticsStopFragment;
import de.cisha.chess.model.exercise.TacticsExerciseSession;

class TacticsStopFragment
implements Runnable {
    final /* synthetic */ TacticsExerciseSession val$result;

    TacticsStopFragment(TacticsExerciseSession tacticsExerciseSession) {
        this.val$result = tacticsExerciseSession;
    }

    @Override
    public void run() {
        2.this.this$0.sessionLoaded(this.val$result);
        2.this.this$0.hideDialogWaiting();
    }
}
