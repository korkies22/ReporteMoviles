/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.tactics;

import android.widget.TextView;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.tactics.ITacticsExerciseService;
import de.cisha.android.board.tactics.TacticsStartFragment;

class TacticsStartFragment
implements Runnable {
    TacticsStartFragment() {
    }

    @Override
    public void run() {
        4.this.this$0._timeOutTextView.setText((CharSequence)ChessClock.formatTime(4.this.this$0._dailyPuzzlesInfo.getTimeLeft() - (System.currentTimeMillis() - 4.this.this$0._onStartTimeMillis), true));
    }
}
