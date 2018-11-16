// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.TimerTask;
import java.util.Timer;
import io.socket.IOAcknowledge;

public abstract class AbstractTimeoutIOAcknowledge implements IOAcknowledge
{
    private static final int TIMEOUT_MILLIS_DEFAULT = 20000;
    private static Timer _timer;
    private TimerTask _timerTask;
    
    public AbstractTimeoutIOAcknowledge() {
        this(20000);
    }
    
    public AbstractTimeoutIOAcknowledge(final int n) {
        if (AbstractTimeoutIOAcknowledge._timer == null) {
            AbstractTimeoutIOAcknowledge._timer = new Timer();
        }
        this._timerTask = new TimerTask() {
            @Override
            public void run() {
                AbstractTimeoutIOAcknowledge.this.onTimeout();
            }
        };
        AbstractTimeoutIOAcknowledge._timer.schedule(this._timerTask, n);
    }
    
    @Override
    public void ack(final Object... array) {
        if (this._timerTask != null && this._timerTask.cancel()) {
            this.onAck(array);
        }
    }
    
    abstract void onAck(final Object... p0);
    
    abstract void onTimeout();
}
