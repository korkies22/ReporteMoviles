/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package de.cisha.android.board.tactics;

import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.tactics.TacticsExerciseFragment;
import de.cisha.android.board.tactics.view.TranslateViewGroup;
import java.text.SimpleDateFormat;
import java.util.Date;

class TacticsExerciseFragment
implements Runnable {
    final /* synthetic */ int val$millisGone;
    final /* synthetic */ int val$millisLeft;

    TacticsExerciseFragment(int n, int n2) {
        this.val$millisLeft = n;
        this.val$millisGone = n2;
    }

    @Override
    public void run() {
        if (this.val$millisLeft >= 0) {
            9.this.this$0._timeRunning.setText((CharSequence)9.this.val$dateFormat.format(new Date(this.val$millisLeft)));
        } else {
            9.this.this$0._timeRunning.setText((CharSequence)9.this.val$dateFormat.format(new Date(0L)));
        }
        if (this.val$millisGone >= 10000 && !9.this.this$0._calledTranlateViewOnce) {
            float f = (float)(this.val$millisGone - 10000) / (float)(9.this.val$solvingTimeMillis - 10000);
            int n = (int)((float)(9.this.this$0._translateViewForTimer.getWidth() - 9.this.this$0._translateViewForTimer.getChildAt(0).getWidth()) * (1.0f - f));
            9.this.this$0._translateViewForTimer.translateViewTo(n);
            9.this.this$0._translateViewForTimer.translateViewTo(9.this.val$positionRatingNow, 9.this.val$solvingTimeMillis - this.val$millisGone);
            9.this.this$0._calledTranlateViewOnce = true;
            return;
        }
        if (this.val$millisGone >= 0 && !9.this.this$0._calledTranlateViewOnce) {
            9.this.this$0._translateViewForTimer.translateViewTo(9.this.this$0._translateViewForTimer.getWidth() - 9.this.this$0._translateViewForTimer.getChildAt(0).getWidth());
        }
    }
}
