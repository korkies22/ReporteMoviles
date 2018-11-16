// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote;

public interface PlayzoneConnectionListener
{
    void onMyConnectionStateChanged(final boolean p0);
    
    void onOpponentsConnectionStateChanged(final boolean p0, final int p1, final boolean p2);
}
