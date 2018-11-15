/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.TimedCommand;
import de.cisha.android.board.video.model.VideoCommandDelegate;
import de.cisha.android.board.view.BoardMarkingDisplay;

public class EmptyCommand
extends TimedCommand {
    public EmptyCommand(long l) {
        super(l);
    }

    @Override
    public boolean executeOn(VideoCommandDelegate videoCommandDelegate, BoardMarkingDisplay boardMarkingDisplay) {
        return true;
    }
}
