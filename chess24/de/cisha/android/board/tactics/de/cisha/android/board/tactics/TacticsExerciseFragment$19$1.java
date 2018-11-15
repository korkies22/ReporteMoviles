/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.chess.model.Rating;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ Rating val$result;

    TacticsExerciseFragment(Rating rating) {
        this.val$result = rating;
    }

    @Override
    public void run() {
        19.this.this$0.finishedLoadingNewRating(this.val$result);
        19.this.this$0.loadNextExercise();
    }
}
