/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

public interface ClockListener {
    public void onClockChanged(long var1, boolean var3);

    public void onClockFlag(boolean var1);

    public void onClockStarted();

    public void onClockStopped();
}
