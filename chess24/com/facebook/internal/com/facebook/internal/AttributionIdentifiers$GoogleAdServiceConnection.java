/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package com.facebook.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.facebook.internal.AttributionIdentifiers;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

private static final class AttributionIdentifiers.GoogleAdServiceConnection
implements ServiceConnection {
    private AtomicBoolean consumed = new AtomicBoolean(false);
    private final BlockingQueue<IBinder> queue = new LinkedBlockingDeque<IBinder>();

    private AttributionIdentifiers.GoogleAdServiceConnection() {
    }

    public IBinder getBinder() throws InterruptedException {
        if (this.consumed.compareAndSet(true, true)) {
            throw new IllegalStateException("Binder already consumed");
        }
        return this.queue.take();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) return;
        try {
            this.queue.put(iBinder);
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }
}
