/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.tactics;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.tactics.view.TranslateViewGroup;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

class TacticsExerciseFragment
extends TimerTask {
    final /* synthetic */ SimpleDateFormat val$dateFormat;
    final /* synthetic */ int val$positionRatingNow;
    final /* synthetic */ int val$solvingTimeMillis;

    TacticsExerciseFragment(int n, SimpleDateFormat simpleDateFormat, int n2) {
        this.val$solvingTimeMillis = n;
        this.val$dateFormat = simpleDateFormat;
        this.val$positionRatingNow = n2;
    }

    @Override
    public void run() {
        if (!TacticsExerciseFragment.this.isResumed()) {
            return;
        }
        int n = (int)(System.currentTimeMillis() - TacticsExerciseFragment.this._startExerciseTime);
        int n2 = this.val$solvingTimeMillis;
        if (TacticsExerciseFragment.this.isAdded() && !TacticsExerciseFragment.this.isRemoving()) {
            TacticsExerciseFragment.this.getActivity().runOnUiThread(new Runnable(n2 - n, n){
                final /* synthetic */ int val$millisGone;
                final /* synthetic */ int val$millisLeft;
                {
                    this.val$millisLeft = n;
                    this.val$millisGone = n2;
                }

                @Override
                public void run() {
                    if (this.val$millisLeft >= 0) {
                        TacticsExerciseFragment.this._timeRunning.setText((CharSequence)9.this.val$dateFormat.format(new Date(this.val$millisLeft)));
                    } else {
                        TacticsExerciseFragment.this._timeRunning.setText((CharSequence)9.this.val$dateFormat.format(new Date(0L)));
                    }
                    if (this.val$millisGone >= 10000 && !TacticsExerciseFragment.this._calledTranlateViewOnce) {
                        float f = (float)(this.val$millisGone - 10000) / (float)(9.this.val$solvingTimeMillis - 10000);
                        int n = (int)((float)(TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth()) * (1.0f - f));
                        TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(n);
                        TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(9.this.val$positionRatingNow, 9.this.val$solvingTimeMillis - this.val$millisGone);
                        TacticsExerciseFragment.this._calledTranlateViewOnce = true;
                        return;
                    }
                    if (this.val$millisGone >= 0 && !TacticsExerciseFragment.this._calledTranlateViewOnce) {
                        TacticsExerciseFragment.this._translateViewForTimer.translateViewTo(TacticsExerciseFragment.this._translateViewForTimer.getWidth() - TacticsExerciseFragment.this._translateViewForTimer.getChildAt(0).getWidth());
                    }
                }
            });
        }
    }

}
