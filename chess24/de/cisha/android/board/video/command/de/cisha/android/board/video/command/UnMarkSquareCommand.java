/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.chess.model.Square;

public class UnMarkSquareCommand
extends TimedCommand {
    private Square _square;

    public UnMarkSquareCommand(Square square, long l) {
        super(l);
        this._square = square;
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.unmarkSquare(this._square);
        return true;
    }
}
