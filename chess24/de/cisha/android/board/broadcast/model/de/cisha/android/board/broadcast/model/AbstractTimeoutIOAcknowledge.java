/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import io.socket.IOAcknowledge;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractTimeoutIOAcknowledge
implements IOAcknowledge {
    private static final int TIMEOUT_MILLIS_DEFAULT = 20000;
    private static Timer _timer;
    private TimerTask _timerTask;

    public AbstractTimeoutIOAcknowledge() {
        this(20000);
    }

    public AbstractTimeoutIOAcknowledge(int n) {
        if (_timer == null) {
            _timer = new Timer();
        }
        this._timerTask = new TimerTask(){

            @Override
            public void run() {
                AbstractTimeoutIOAcknowledge.this.onTimeout();
            }
        };
        _timer.schedule(this._timerTask, n);
    }

    @Override
    public /* varargs */ void ack(Object ... arrobject) {
        if (this._timerTask != null && this._timerTask.cancel()) {
            this.onAck(arrobject);
        }
    }

    /* varargs */ abstract void onAck(Object ... var1);

    abstract void onTimeout();

}
