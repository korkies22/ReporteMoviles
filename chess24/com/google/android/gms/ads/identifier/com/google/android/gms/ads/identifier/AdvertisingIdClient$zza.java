/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.ads.identifier;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

static class AdvertisingIdClient.zza
extends Thread {
    private WeakReference<AdvertisingIdClient> zzsl;
    private long zzsm;
    CountDownLatch zzsn;
    boolean zzso;

    public AdvertisingIdClient.zza(AdvertisingIdClient advertisingIdClient, long l) {
        this.zzsl = new WeakReference<AdvertisingIdClient>(advertisingIdClient);
        this.zzsm = l;
        this.zzsn = new CountDownLatch(1);
        this.zzso = false;
        this.start();
    }

    private void disconnect() {
        AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient)this.zzsl.get();
        if (advertisingIdClient != null) {
            advertisingIdClient.finish();
            this.zzso = true;
        }
    }

    public void cancel() {
        this.zzsn.countDown();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        try {
            if (this.zzsn.await(this.zzsm, TimeUnit.MILLISECONDS)) return;
            {
                this.disconnect();
                return;
            }
        }
        catch (InterruptedException interruptedException) {}
        this.disconnect();
    }

    public boolean zzby() {
        return this.zzso;
    }
}
