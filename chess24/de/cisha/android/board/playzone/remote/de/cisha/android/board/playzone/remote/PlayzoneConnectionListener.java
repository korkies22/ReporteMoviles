/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote;

public interface PlayzoneConnectionListener {
    public void onMyConnectionStateChanged(boolean var1);

    public void onOpponentsConnectionStateChanged(boolean var1, int var2, boolean var3);
}
