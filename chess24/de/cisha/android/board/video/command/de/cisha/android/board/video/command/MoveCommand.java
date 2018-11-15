/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;
import de.cisha.chess.model.SEP;

public class MoveCommand
extends TimedCommand {
    private int _moveID;
    private SEP _sep;

    public MoveCommand(long l, SEP sEP, int n) {
        super(l);
        this._sep = sEP;
        this._moveID = n;
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        return videoCommandDelegate.addVideoMove(this._sep, this._moveID);
    }
}
