// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.command;

public abstract class TimedCommand implements VideoCommand
{
    private long _time;
    
    public TimedCommand(final long time) {
        this._time = time;
    }
    
    @Override
    public long getExecutionTime() {
        return this._time;
    }
}
