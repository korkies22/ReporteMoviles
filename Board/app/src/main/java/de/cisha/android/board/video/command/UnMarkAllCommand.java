// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;

public class UnMarkAllCommand extends TimedCommand
{
    public UnMarkAllCommand(final long n) {
        super(n);
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.unmarkField();
        return true;
    }
}
