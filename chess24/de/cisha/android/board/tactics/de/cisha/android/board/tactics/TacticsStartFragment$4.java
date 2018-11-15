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
import java.util.TimerTask;

class TacticsStartFragment
extends TimerTask {
    TacticsStartFragment() {
    }

    @Override
    public void run() {
        TacticsStartFragment.this._timeOutTextView.post(new Runnable(){

            @Override
            public void run() {
                TacticsStartFragment.this._timeOutTextView.setText((CharSequence)ChessClock.formatTime(TacticsStartFragment.this._dailyPuzzlesInfo.getTimeLeft() - (System.currentTimeMillis() - TacticsStartFragment.this._onStartTimeMillis), true));
            }
        });
    }

}
