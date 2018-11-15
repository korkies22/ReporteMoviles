/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;

public class ArrowCommand
extends TimedCommand {
    private int _color;
    private SEP _sep;

    public ArrowCommand(SEP sEP, int n, long l) {
        super(l);
        this._sep = sEP;
        this._color = n;
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.addArrowSquare(this._sep.getStartSquare(), this._sep.getEndSquare(), this._color);
        return true;
    }
}
