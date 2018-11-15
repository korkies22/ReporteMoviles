/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.stats.WakeLockEvent;
import com.google.android.gms.common.stats.zzc;
import com.google.android.gms.common.stats.zze;
import com.google.android.gms.common.util.zzj;
import java.util.List;

public class zzg {
    private static String TAG = "WakeLockTracker";
    private static zzg zzaGH = new zzg();
    private static Boolean zzaGI;

    private static boolean zzaG(Context context) {
        if (zzaGI == null) {
            zzaGI = zzg.zzaH(context);
        }
        return zzaGI;
    }

    private static boolean zzaH(Context context) {
        return false;
    }

    public static zzg zzyr() {
        return zzaGH;
    }

    public void zza(Context context, String string, int n, String string2, String string3, String string4, int n2, List<String> list) {
        this.zza(context, string, n, string2, string3, string4, n2, list, 0L);
    }

    public void zza(Context object, String object2, int n, String string, String string2, String string3, int n2, List<String> list, long l) {
        if (!zzg.zzaG((Context)object)) {
            return;
        }
        if (TextUtils.isEmpty((CharSequence)object2)) {
            string = TAG;
            object = String.valueOf(object2);
            object = object.length() != 0 ? "missing wakeLock key. ".concat((String)object) : new String("missing wakeLock key. ");
            Log.e((String)string, (String)object);
            return;
        }
        long l2 = System.currentTimeMillis();
        if (7 == n || 8 == n || 10 == n || 11 == n) {
            object2 = new WakeLockEvent(l2, n, string, n2, zze.zzz(list), (String)object2, SystemClock.elapsedRealtime(), zzj.zzaM((Context)object), string2, zze.zzdB(object.getPackageName()), zzj.zzaN((Context)object), l, string3);
            try {
                object.startService(new Intent().setComponent(zzc.zzaGj).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", (Parcelable)object2));
                return;
            }
            catch (Exception exception) {
                Log.wtf((String)TAG, (Throwable)exception);
            }
        }
    }
}
