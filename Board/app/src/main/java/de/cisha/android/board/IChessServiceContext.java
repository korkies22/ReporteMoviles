// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board;

import android.os.Messenger;

public interface IChessServiceContext
{
    void bindToChessService();
    
    Messenger getChessServiceConnection();
    
    boolean sendChessCommand(final String p0, final Messenger p1);
    
    void unbindChessService();
}
