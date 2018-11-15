/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsj;

public class zzsn
extends zzru {
    private boolean zzaef;
    private boolean zzaeg;
    private AlarmManager zzaeh = (AlarmManager)this.getContext().getSystemService("alarm");

    protected zzsn(zzrw zzrw2) {
        super(zzrw2);
    }

    private PendingIntent zzpe() {
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(this.getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"));
        return PendingIntent.getBroadcast((Context)this.getContext(), (int)0, (Intent)intent, (int)0);
    }

    public void cancel() {
        this.zznA();
        this.zzaeg = false;
        this.zzaeh.cancel(this.zzpe());
    }

    public void schedule() {
        this.zznA();
        zzac.zza(this.zzpd(), (Object)"Receiver not registered");
        long l = this.zzns().zzoE();
        if (l > 0L) {
            this.cancel();
            long l2 = this.zznq().elapsedRealtime();
            this.zzaeg = true;
            this.zzaeh.setInexactRepeating(2, l2 + l, 0L, this.zzpe());
        }
    }

    public boolean zzcv() {
        return this.zzaeg;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected void zzmr() {
        ActivityInfo activityInfo;
        try {
            this.zzaeh.cancel(this.zzpe());
            if (this.zzns().zzoE() <= 0L || (activityInfo = this.getContext().getPackageManager().getReceiverInfo(new ComponentName(this.getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"), 2)) == null) return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
        if (!activityInfo.enabled) return;
        this.zzbO("Receiver registered. Using alarm for local dispatch.");
        this.zzaef = true;
    }

    public boolean zzpd() {
        return this.zzaef;
    }
}
