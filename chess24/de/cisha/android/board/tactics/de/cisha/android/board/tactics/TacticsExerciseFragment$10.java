/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.tactics;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import java.util.TimerTask;

class TacticsExerciseFragment
extends TimerTask {
    final /* synthetic */ int val$maxRating;
    final /* synthetic */ int val$solvingTimeMillis;
    final /* synthetic */ int val$userRatingNow;

    TacticsExerciseFragment(int n, int n2, int n3) {
        this.val$solvingTimeMillis = n;
        this.val$maxRating = n2;
        this.val$userRatingNow = n3;
    }

    @Override
    public void run() {
        if (!TacticsExerciseFragment.this.isResumed()) {
            return;
        }
        long l = TacticsExerciseFragment.this._startExerciseTime;
        l = System.currentTimeMillis() - (l + 10000L);
        if (l > 0L) {
            double d;
            double d2 = d = (double)l / (double)(this.val$solvingTimeMillis - 10000);
            if (d > 1.0) {
                d2 = 1.0;
            }
            int n = this.val$maxRating;
            int n2 = (int)(d2 * (double)(this.val$maxRating - this.val$userRatingNow));
            if (TacticsExerciseFragment.this.isAdded() && !TacticsExerciseFragment.this.isRemoving()) {
                TacticsExerciseFragment.this.getActivity().runOnUiThread(new Runnable(n - n2){
                    final /* synthetic */ int val$rating;
                    {
                        this.val$rating = n;
                    }

                    @Override
                    public void run() {
                        TextView textView = TacticsExerciseFragment.this._ratingRunning;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(this.val$rating);
                        stringBuilder.append("");
                        textView.setText((CharSequence)stringBuilder.toString());
                    }
                });
            }
        }
    }

}
