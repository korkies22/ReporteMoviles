/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.chess.model.exercise.TacticsExerciseSession;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ TacticsExerciseSession val$result;

    TacticsExerciseFragment(TacticsExerciseSession tacticsExerciseSession) {
        this.val$result = tacticsExerciseSession;
    }

    @Override
    public void run() {
        4.this.this$0.hideDialogWaiting();
        4.this.this$0.loadFirstExercise();
        4.this.this$0._session = this.val$result;
        4.this.this$0.initHistoryBarWithSession();
    }
}
