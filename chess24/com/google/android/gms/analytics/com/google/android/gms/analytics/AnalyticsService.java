/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.os.IBinder
 */
package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.internal.zztc;

public final class AnalyticsService
extends Service
implements zztc.zza {
    private zztc zzaap;

    private zztc zzlP() {
        if (this.zzaap == null) {
            this.zzaap = new zztc(this);
        }
        return this.zzaap;
    }

    @Override
    public boolean callServiceStopSelfResult(int n) {
        return this.stopSelfResult(n);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public IBinder onBind(Intent intent) {
        this.zzlP();
        return null;
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        super.onCreate();
        this.zzlP().onCreate();
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        this.zzlP().onDestroy();
        super.onDestroy();
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent intent, int n, int n2) {
        return this.zzlP().onStartCommand(intent, n, n2);
    }
}
