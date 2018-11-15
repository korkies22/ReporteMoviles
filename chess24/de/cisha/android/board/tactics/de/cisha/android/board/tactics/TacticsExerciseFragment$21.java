/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.tactics;

import android.widget.TextView;
import de.cisha.android.board.tactics.view.NumberExerciseViewGroup;
import de.cisha.android.board.tactics.view.TranslateViewGroup;

class TacticsExerciseFragment
implements Runnable {
    TacticsExerciseFragment() {
    }

    @Override
    public void run() {
        if (TacticsExerciseFragment.this._openTranslateView) {
            int n = TacticsExerciseFragment.this._numberExerciseViewGroup.getMeasuredWidth();
            int n2 = TacticsExerciseFragment.this._exerciseNo.getMeasuredWidth();
            TacticsExerciseFragment.this._translateViewTopMenu.translateViewTo(n - n2, 500);
        } else {
            TacticsExerciseFragment.this._translateViewTopMenu.translateViewTo(0, 500);
        }
        TacticsExerciseFragment.this._openTranslateView = TacticsExerciseFragment.this._openTranslateView ^ true;
    }
}
