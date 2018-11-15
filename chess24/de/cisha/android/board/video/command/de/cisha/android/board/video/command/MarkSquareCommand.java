/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.chess.model.Square;

public class MarkSquareCommand
extends TimedCommand {
    private int _color;
    private Square _square;

    public MarkSquareCommand(Square square, int n, long l) {
        super(l);
        this._square = square;
        this._color = n;
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.markSquare(this._square, this._color);
        return true;
    }
}
