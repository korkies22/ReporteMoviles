/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 */
package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzv;

public final class zzf.zzh
implements ServiceConnection {
    private final int zzaDY;

    public zzf.zzh(int n) {
        this.zzaDY = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onServiceConnected(ComponentName object, IBinder iBinder) {
        if (iBinder == null) {
            zzf.this.zzm(new ConnectionResult(8, null, "ServiceBroker IBinder is null"));
            return;
        }
        object = zzf.this.zzaDH;
        synchronized (object) {
            zzf.this.zzaDI = zzv.zza.zzbu(iBinder);
        }
        zzf.this.zza(0, null, this.zzaDY);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onServiceDisconnected(ComponentName object) {
        object = zzf.this.zzaDH;
        synchronized (object) {
            zzf.this.zzaDI = null;
        }
        zzf.this.mHandler.sendMessage(zzf.this.mHandler.obtainMessage(4, this.zzaDY, 1));
    }
}
