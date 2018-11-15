/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.tactics.view;

import android.view.View;
import de.cisha.android.board.tactics.view.ExerciseHistoryView;

class ExerciseHistoryView
implements View.OnClickListener {
    final /* synthetic */ int val$number;

    ExerciseHistoryView(int n) {
        this.val$number = n;
    }

    public void onClick(View view) {
        if (ExerciseHistoryView.this._selectionListener != null) {
            ExerciseHistoryView.this._selectionListener.onHistoryNumberClicked(this.val$number);
        }
        ExerciseHistoryView.this.selectView(view);
    }
}
