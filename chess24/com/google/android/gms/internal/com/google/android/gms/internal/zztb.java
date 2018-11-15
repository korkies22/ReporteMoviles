/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzayd;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztc;
import com.google.android.gms.internal.zztg;

public final class zztb {
    static zzayd zzaav;
    static Boolean zzaaw;
    static Object zztU;

    static {
        zztU = new Object();
    }

    public static boolean zzT(Context context) {
        zzac.zzw(context);
        if (zzaaw != null) {
            return zzaaw;
        }
        boolean bl = zztg.zza(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        zzaaw = bl;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent object) {
        zzsx zzsx2 = zzrw.zzW(context).zznr();
        if (object == null) {
            zzsx2.zzbR("AnalyticsReceiver called with null intent");
            return;
        }
        object = object.getAction();
        zzsx2.zza("Local AnalyticsReceiver got", object);
        if (!"com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(object)) {
            return;
        }
        boolean bl = zztc.zzU(context);
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
        intent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        object = zztU;
        synchronized (object) {
            block8 : {
                context.startService(intent);
                if (!bl) {
                    return;
                }
                try {
                    if (zzaav == null) {
                        zzaav = new zzayd(context, 1, "Analytics WakeLock");
                        zzaav.setReferenceCounted(false);
                    }
                    zzaav.acquire(1000L);
                    break block8;
                }
                catch (SecurityException securityException) {}
                zzsx2.zzbR("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
            }
            return;
        }
    }
}
