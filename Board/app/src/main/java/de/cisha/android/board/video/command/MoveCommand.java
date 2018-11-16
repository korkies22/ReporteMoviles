// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.chess.model.SEP;

public class MoveCommand extends TimedCommand
{
    private int _moveID;
    private SEP _sep;
    
    public MoveCommand(final long n, final SEP sep, final int moveID) {
        super(n);
        this._sep = sep;
        this._moveID = moveID;
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        return videoCommandDelegate.addVideoMove(this._sep, this._moveID);
    }
}
