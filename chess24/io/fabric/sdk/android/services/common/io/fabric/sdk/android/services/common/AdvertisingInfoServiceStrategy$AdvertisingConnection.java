/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

private static final class AdvertisingInfoServiceStrategy.AdvertisingConnection
implements ServiceConnection {
    private static final int QUEUE_TIMEOUT_IN_MS = 200;
    private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue(1);
    private boolean retrieved = false;

    private AdvertisingInfoServiceStrategy.AdvertisingConnection() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public IBinder getBinder() {
        if (this.retrieved) {
            Fabric.getLogger().e("Fabric", "getBinder already called");
        }
        this.retrieved = true;
        try {
            return this.queue.poll(200L, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException interruptedException) {
            return null;
        }
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.queue.put(iBinder);
            return;
        }
        catch (InterruptedException interruptedException) {
            return;
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.queue.clear();
    }
}
