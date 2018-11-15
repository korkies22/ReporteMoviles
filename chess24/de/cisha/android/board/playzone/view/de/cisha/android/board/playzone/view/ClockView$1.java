/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package de.cisha.android.board.playzone.view;

import android.widget.TextView;

class ClockView
implements Runnable {
    ClockView() {
    }

    @Override
    public void run() {
        ClockView.this._divider.setVisibility(4);
    }
}
