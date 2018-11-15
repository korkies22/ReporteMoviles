/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.view;

import java.util.TimerTask;

class ClockView
extends TimerTask {
    ClockView() {
    }

    @Override
    public void run() {
        ClockView.this.post(ClockView.this._showDivider);
    }
}
