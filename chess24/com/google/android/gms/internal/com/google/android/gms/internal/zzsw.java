/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.internal.zzsk;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzsx;

@Deprecated
public class zzsw {
    private static volatile Logger zzafq;

    static {
        zzsw.setLogger(new zzsk());
    }

    public static Logger getLogger() {
        return zzafq;
    }

    public static void setLogger(Logger logger) {
        zzafq = logger;
    }

    public static void v(String string) {
        Object object = zzsx.zzpw();
        if (object != null) {
            object.zzbO(string);
        } else if (zzsw.zzai(0)) {
            Log.v((String)zzsq.zzaek.get(), (String)string);
        }
        object = zzafq;
        if (object != null) {
            object.verbose(string);
        }
    }

    public static boolean zzai(int n) {
        boolean bl;
        Logger logger = zzsw.getLogger();
        boolean bl2 = bl = false;
        if (logger != null) {
            bl2 = bl;
            if (zzsw.getLogger().getLogLevel() <= n) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static void zzbe(String string) {
        Object object = zzsx.zzpw();
        if (object != null) {
            object.zzbR(string);
        } else if (zzsw.zzai(2)) {
            Log.w((String)zzsq.zzaek.get(), (String)string);
        }
        object = zzafq;
        if (object != null) {
            object.warn(string);
        }
    }

    public static void zzf(String string, Object object) {
        Object object2 = zzsx.zzpw();
        if (object2 != null) {
            object2.zze(string, object);
        } else if (zzsw.zzai(3)) {
            if (object != null) {
                object = String.valueOf(object);
                object2 = new StringBuilder(1 + String.valueOf(string).length() + String.valueOf(object).length());
                object2.append(string);
                object2.append(":");
                object2.append((String)object);
                object = object2.toString();
            } else {
                object = string;
            }
            Log.e((String)zzsq.zzaek.get(), (String)object);
        }
        object = zzafq;
        if (object != null) {
            object.error(string);
        }
    }
}
