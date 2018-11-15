/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.stats.zzc;
import com.google.android.gms.common.stats.zzd;
import com.google.android.gms.internal.zzabs;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class zza {
    private static final Object zzaED = new Object();
    private static zza zzaFT;
    private static Integer zzaFZ;
    private final List<String> zzaFU;
    private final List<String> zzaFV;
    private final List<String> zzaFW;
    private final List<String> zzaFX;
    private zzd zzaFY;
    private zzd zzaGa;

    private zza() {
        if (this.zzyd()) {
            this.zzaFU = Collections.EMPTY_LIST;
            this.zzaFV = Collections.EMPTY_LIST;
            this.zzaFW = Collections.EMPTY_LIST;
            this.zzaFX = Collections.EMPTY_LIST;
            return;
        }
        Object object = zzb.zza.zzaGe.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(object.split(","));
        this.zzaFU = object;
        object = zzb.zza.zzaGf.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(object.split(","));
        this.zzaFV = object;
        object = zzb.zza.zzaGg.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(object.split(","));
        this.zzaFW = object;
        object = zzb.zza.zzaGh.get();
        object = object == null ? Collections.EMPTY_LIST : Arrays.asList(object.split(","));
        this.zzaFX = object;
        this.zzaFY = new zzd(1024, zzb.zza.zzaGi.get());
        this.zzaGa = new zzd(1024, zzb.zza.zzaGi.get());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int getLogLevel() {
        if (zzaFZ != null) return zzaFZ;
        try {
            zzaFZ = zzc.LOG_LEVEL_OFF;
            return zzaFZ;
        }
        catch (SecurityException securityException) {}
        zzaFZ = zzc.LOG_LEVEL_OFF;
        return zzaFZ;
    }

    private boolean zzc(Context context, Intent intent) {
        if ((intent = intent.getComponent()) == null) {
            return false;
        }
        return com.google.android.gms.common.util.zzd.zzx(context, intent.getPackageName());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zza zzyc() {
        Object object = zzaED;
        synchronized (object) {
            if (zzaFT == null) {
                zzaFT = new zza();
            }
            return zzaFT;
        }
    }

    private boolean zzyd() {
        if (zza.getLogLevel() == zzc.LOG_LEVEL_OFF) {
            return true;
        }
        return false;
    }

    @SuppressLint(value={"UntrackedBindService"})
    public void zza(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
    }

    public void zza(Context context, ServiceConnection serviceConnection, String string, Intent intent) {
    }

    public boolean zza(Context context, Intent intent, ServiceConnection serviceConnection, int n) {
        return this.zza(context, context.getClass().getName(), intent, serviceConnection, n);
    }

    @SuppressLint(value={"UntrackedBindService"})
    public boolean zza(Context context, String string, Intent intent, ServiceConnection serviceConnection, int n) {
        if (this.zzc(context, intent)) {
            Log.w((String)"ConnectionTracker", (String)"Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        return context.bindService(intent, serviceConnection, n);
    }

    public void zzb(Context context, ServiceConnection serviceConnection) {
    }
}
