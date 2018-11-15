/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics;

class TacticsStopFragment
implements Runnable {
    TacticsStopFragment() {
    }

    @Override
    public void run() {
        TacticsStopFragment.this.loadSession();
    }
}
