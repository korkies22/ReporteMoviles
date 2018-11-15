/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import de.cisha.android.board.tactics.view.ExerciseHistoryView;

class TacticsStopFragment
implements Runnable {
    TacticsStopFragment() {
    }

    @Override
    public void run() {
        if (TacticsStopFragment.this._exerciseHistoryBar != null) {
            TacticsStopFragment.this._exerciseHistoryBar.scrollToSelectedExercise();
        }
    }
}
