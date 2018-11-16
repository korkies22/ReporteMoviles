// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import de.cisha.chess.model.SEP;

public interface VideoCommandDelegate
{
    boolean addVideoMove(final SEP p0, final int p1);
    
    boolean selectGame(final int p0);
    
    boolean selectMove(final int p0);
}
