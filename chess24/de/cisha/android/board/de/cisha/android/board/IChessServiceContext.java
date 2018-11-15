/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Messenger
 */
package de.cisha.android.board;

import android.os.Messenger;

public interface IChessServiceContext {
    public void bindToChessService();

    public Messenger getChessServiceConnection();

    public boolean sendChessCommand(String var1, Messenger var2);

    public void unbindChessService();
}
