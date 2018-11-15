/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.Square;

public class UnArrowCommand
extends TimedCommand {
    private SEP _sep;

    public UnArrowCommand(SEP sEP, long l) {
        super(l);
        this._sep = sEP;
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        boardMarkingDisplay.removeArrow(this._sep.getStartSquare(), this._sep.getEndSquare());
        return true;
    }
}
