/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.tactics.TacticsExerciseFragment;

class TacticsExerciseFragment
implements IErrorPresenter.ICancelAction {
    TacticsExerciseFragment() {
    }

    @Override
    public void cancelPressed() {
        2.this.this$1.this$0.actionStop();
    }
}
