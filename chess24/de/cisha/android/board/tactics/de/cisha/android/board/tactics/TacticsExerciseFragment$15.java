/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.tactics;

import android.view.View;
import de.cisha.android.ui.patterns.text.CustomTextView;

class TacticsExerciseFragment
implements View.OnClickListener {
    final /* synthetic */ CustomTextView val$viewActionStop;

    TacticsExerciseFragment(CustomTextView customTextView) {
        this.val$viewActionStop = customTextView;
    }

    public void onClick(View view) {
        TacticsExerciseFragment.this._stopFlag = TacticsExerciseFragment.this._stopFlag ^ true;
        this.val$viewActionStop.setGlowing(TacticsExerciseFragment.this._stopFlag);
        this.val$viewActionStop.setSelected(TacticsExerciseFragment.this._stopFlag);
    }
}
