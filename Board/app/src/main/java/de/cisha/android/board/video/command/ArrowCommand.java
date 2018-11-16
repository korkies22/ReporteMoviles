// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.chess.model.SEP;

public class ArrowCommand extends TimedCommand
{
    private int _color;
    private SEP _sep;
    
    public ArrowCommand(final SEP sep, final int color, final long n) {
        super(n);
        this._sep = sep;
        this._color = color;
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.addArrowSquare(this._sep.getStartSquare(), this._sep.getEndSquare(), this._color);
        return true;
    }
}
