// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

public class RunnableQueue extends Thread
{
    private boolean _destroy;
    private LinkedBlockingQueue<Runnable> _internalQueue;
    
    public RunnableQueue() {
        this._destroy = false;
        this._internalQueue = new LinkedBlockingQueue<Runnable>();
        this._destroy = false;
    }
    
    public void add(final Runnable runnable) {
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
                    runnable = this._internalQueue.poll(100L, TimeUnit.MILLISECONDS);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                runnable.run();
            }
        }
    }
}
