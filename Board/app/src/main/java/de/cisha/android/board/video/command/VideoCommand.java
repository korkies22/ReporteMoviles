// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;

public interface VideoCommand
{
    boolean executeOn(final VideoCommandDelegate p0, final BoardMarkingDisplay p1);
    
    long getExecutionTime();
}
