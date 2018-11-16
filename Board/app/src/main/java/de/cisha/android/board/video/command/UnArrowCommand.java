// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.chess.model.SEP;

public class UnArrowCommand extends TimedCommand
{
    private SEP _sep;
    
    public UnArrowCommand(final SEP sep, final long n) {
        super(n);
        this._sep = sep;
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.removeArrow(this._sep.getStartSquare(), this._sep.getEndSquare());
        return true;
    }
}
