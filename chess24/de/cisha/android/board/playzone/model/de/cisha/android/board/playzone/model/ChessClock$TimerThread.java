/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.ChessClock;

private class ChessClock.TimerThread
extends Thread {
    public boolean running = false;

    protected ChessClock.TimerThread() {
        this.setName("clockthread");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        this.running = true;
        while (this.running) {
            long l;
            long l2 = ChessClock.this.getTimeMillis(ChessClock.this._activeColor);
            long l3 = System.currentTimeMillis();
            long l4 = l = l3 - ChessClock.this._lastTimeMeasure;
            if (ChessClock.this._countUpwards) {
                l4 = - l;
            }
            ChessClock.this._lastTimeMeasure = l3;
            ChessClock.this.updateClock(ChessClock.this._activeColor, l2 - l4);
            ChessClock chessClock = ChessClock.this;
            boolean bl = ChessClock.this._activeColor;
            l4 = ChessClock.this._activeColor ? ChessClock.this._remainingRunningTimeBlack : ChessClock.this._remainingRunningTimeWhite;
            chessClock.updateClock(bl ^ true, l4);
            l4 = l2 > 20000L ? 490L : 47L;
            try {
                Thread.sleep(l4);
            }
            catch (InterruptedException interruptedException) {
            }
        }
    }
}
