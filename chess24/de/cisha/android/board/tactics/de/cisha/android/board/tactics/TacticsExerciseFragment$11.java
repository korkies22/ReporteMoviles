/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package de.cisha.android.board.tactics;

import android.view.View;
import de.cisha.android.board.tactics.view.TranslateViewGroup;

class TacticsExerciseFragment
implements Runnable {
    TacticsExerciseFragment() {
    }

    @Override
    public void run() {
        TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth());
    }
}
