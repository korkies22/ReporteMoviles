/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.tactics;

import android.widget.TextView;
import de.cisha.android.board.tactics.TacticsExerciseFragment;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ int val$rating;

    TacticsExerciseFragment(int n) {
        this.val$rating = n;
    }

    @Override
    public void run() {
        TextView textView = 10.this.this$0._ratingRunning;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.val$rating);
        stringBuilder.append("");
        textView.setText((CharSequence)stringBuilder.toString());
    }
}
