/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.AbstractContentFragment;
import de.cisha.android.board.IContentPresenter;
import de.cisha.android.board.tactics.TacticsStopFragment;

class TacticsExerciseFragment
implements Runnable {
    TacticsExerciseFragment() {
    }

    @Override
    public void run() {
        TacticsStopFragment tacticsStopFragment = new TacticsStopFragment();
        IContentPresenter iContentPresenter = TacticsExerciseFragment.this.getContentPresenter();
        if (iContentPresenter != null) {
            iContentPresenter.showFragment(tacticsStopFragment, false, false);
        }
    }
}
