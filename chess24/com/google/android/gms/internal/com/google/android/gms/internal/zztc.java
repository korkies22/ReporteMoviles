/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Handler
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzayd;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzso;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztb;
import com.google.android.gms.internal.zztg;

public final class zztc {
    private static Boolean zzaax;
    private final Context mContext;
    private final Handler mHandler;
    private final zza zzafI;

    public zztc(zza zza2) {
        this.mContext = zza2.getContext();
        zzac.zzw(this.mContext);
        this.zzafI = zza2;
        this.mHandler = new Handler();
    }

    public static boolean zzU(Context context) {
        zzac.zzw(context);
        if (zzaax != null) {
            return zzaax;
        }
        boolean bl = zztg.zzr(context, "com.google.android.gms.analytics.AnalyticsService");
        zzaax = bl;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzlS() {
        zzayd zzayd2;
        try {
            Object object = zztb.zztU;
            synchronized (object) {
                zzayd2 = zztb.zzaav;
            }
        }
        catch (SecurityException securityException) {
            return;
        }
        {
            if (zzayd2 != null && zzayd2.isHeld()) {
                zzayd2.release();
            }
            return;
        }
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        zzrw.zzW(this.mContext).zznr().zzbO("Local AnalyticsService is starting up");
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        zzrw.zzW(this.mContext).zznr().zzbO("Local AnalyticsService is shutting down");
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent object, int n, final int n2) {
        this.zzlS();
        final zzrw zzrw2 = zzrw.zzW(this.mContext);
        final zzsx zzsx2 = zzrw2.zznr();
        if (object == null) {
            zzsx2.zzbR("AnalyticsService started with null intent");
            return 2;
        }
        object = object.getAction();
        zzsx2.zza("Local AnalyticsService called. startId, action", n2, object);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(object)) {
            zzrw2.zzlZ().zza(new zzso(){

                @Override
                public void zzf(Throwable throwable) {
                    zztc.this.mHandler.post(new Runnable(){

                        @Override
                        public void run() {
                            if (zztc.this.zzafI.callServiceStopSelfResult(n2)) {
                                zzsx2.zzbO("Local AnalyticsService processed last dispatch request");
                            }
                        }
                    });
                }

            });
        }
        return 2;
    }

    public static interface zza {
        public boolean callServiceStopSelfResult(int var1);

        public Context getContext();
    }

}
