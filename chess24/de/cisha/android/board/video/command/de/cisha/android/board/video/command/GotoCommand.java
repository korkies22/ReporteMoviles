/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;

public class GotoCommand
extends TimedCommand {
    private int _moveID;

    public GotoCommand(int n, long l) {
        super(l);
        this._moveID = n;
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        return videoCommandDelegate.selectMove(this._moveID);
    }
}
