/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.video.command;

import de.cisha.android.board.video.command.VideoCommand;

public abstract class TimedCommand
implements VideoCommand {
    private long _time;

    public TimedCommand(long l) {
        this._time = l;
    }

    @Override
    public long getExecutionTime() {
        return this._time;
    }
}
