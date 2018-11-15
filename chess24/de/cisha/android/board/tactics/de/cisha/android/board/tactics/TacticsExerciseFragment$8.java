/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package de.cisha.android.board.tactics;

import android.view.ViewGroup;
import de.cisha.android.board.view.FieldView;

class TacticsExerciseFragment
implements Runnable {
    TacticsExerciseFragment() {
    }

    @Override
    public void run() {
        int n;
        de.cisha.android.board.tactics.TacticsExerciseFragment.access$1200((de.cisha.android.board.tactics.TacticsExerciseFragment)TacticsExerciseFragment.this).getLayoutParams().width = n = TacticsExerciseFragment.this._fieldView.getMeasuredHeight();
        de.cisha.android.board.tactics.TacticsExerciseFragment.access$1200((de.cisha.android.board.tactics.TacticsExerciseFragment)TacticsExerciseFragment.this).getLayoutParams().height = n;
    }
}
