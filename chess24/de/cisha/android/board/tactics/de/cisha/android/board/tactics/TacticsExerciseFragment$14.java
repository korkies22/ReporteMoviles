/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.tactics;

import android.view.View;

class TacticsExerciseFragment
implements View.OnClickListener {
    TacticsExerciseFragment() {
    }

    public void onClick(View view) {
        TacticsExerciseFragment.this.actionHint();
        TacticsExerciseFragment.this._strapMenuActionHint.setSelected(TacticsExerciseFragment.this._hintActive);
    }
}
