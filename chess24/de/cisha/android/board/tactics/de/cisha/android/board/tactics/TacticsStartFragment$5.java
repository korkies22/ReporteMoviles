/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

import java.util.Timer;
import java.util.TimerTask;

class TacticsStartFragment
extends TimerTask {
    TacticsStartFragment() {
    }

    @Override
    public void run() {
        TacticsStartFragment.this._timer.cancel();
        TacticsStartFragment.this.loadDailyPuzzlesInfo();
    }
}
