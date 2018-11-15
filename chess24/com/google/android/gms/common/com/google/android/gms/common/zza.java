/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzac;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zza
implements ServiceConnection {
    boolean zzawV = false;
    private final BlockingQueue<IBinder> zzawW = new LinkedBlockingQueue<IBinder>();

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zzawW.add(iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }

    public IBinder zza(long l, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        zzac.zzdo("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.zzawV) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.zzawV = true;
        if ((timeUnit = this.zzawW.poll(l, timeUnit)) == null) {
            throw new TimeoutException("Timed out waiting for the service connection");
        }
        return timeUnit;
    }

    public IBinder zzuy() throws InterruptedException {
        zzac.zzdo("BlockingServiceConnection.getService() called on main thread");
        if (this.zzawV) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.zzawV = true;
        return this.zzawW.take();
    }
}
