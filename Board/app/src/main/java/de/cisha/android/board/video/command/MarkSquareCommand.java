// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.chess.model.Square;

public class MarkSquareCommand extends TimedCommand
{
    private int _color;
    private Square _square;
    
    public MarkSquareCommand(final Square square, final int color, final long n) {
        super(n);
        this._square = square;
        this._color = color;
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.markSquare(this._square, this._color);
        return true;
    }
}
