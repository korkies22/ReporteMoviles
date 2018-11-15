/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RunnableQueue
extends Thread {
    private boolean _destroy = false;
    private LinkedBlockingQueue<Runnable> _internalQueue = new LinkedBlockingQueue();

    public void add(Runnable runnable) {
        this._internalQueue.offer(runnable);
    }

    @Override
    public void destroy() {
        this._destroy = true;
    }

    @Override
    public void run() {
        while (!this._destroy) {
            while (this._internalQueue.size() > 0) {
                Runnable runnable = null;
                try {
                    Runnable runnable2;
                    runnable = runnable2 = this._internalQueue.poll(100L, TimeUnit.MILLISECONDS);
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                runnable.run();
            }
        }
    }
}
