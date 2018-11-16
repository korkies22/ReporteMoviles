// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;

public class GotoCommand extends TimedCommand
{
    private int _moveID;
    
    public GotoCommand(final int moveID, final long n) {
        super(n);
        this._moveID = moveID;
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        return videoCommandDelegate.selectMove(this._moveID);
    }
}
