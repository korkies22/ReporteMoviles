// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

public interface ClockListener
{
    void onClockChanged(final long p0, final boolean p1);
    
    void onClockFlag(final boolean p0);
    
    void onClockStarted();
    
    void onClockStopped();
}
