// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.chess.model.Square;

public class UnMarkSquareCommand extends TimedCommand
{
    private Square _square;
    
    public UnMarkSquareCommand(final Square square, final long n) {
        super(n);
        this._square = square;
    }
    
    @Override
    public boolean executeOn(final VideoCommandDelegate videoCommandDelegate, final BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.unmarkSquare(this._square);
        return true;
    }
}
