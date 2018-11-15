/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.WorkSource
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.WorkSource;
import android.util.Log;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzv;
import com.google.android.gms.internal.zzacx;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class zzy {
    private static final Method zzaHc = zzy.zzyL();
    private static final Method zzaHd = zzy.zzyM();
    private static final Method zzaHe = zzy.zzyN();
    private static final Method zzaHf = zzy.zzyO();
    private static final Method zzaHg = zzy.zzyP();

    public static int zza(WorkSource workSource) {
        if (zzaHe != null) {
            try {
                int n = (Integer)zzaHe.invoke((Object)workSource, new Object[0]);
                return n;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
            }
        }
        return 0;
    }

    public static String zza(WorkSource object, int n) {
        if (zzaHg != null) {
            try {
                object = (String)zzaHg.invoke(object, n);
                return object;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
            }
        }
        return null;
    }

    public static void zza(WorkSource workSource, int n, String string) {
        if (zzaHd != null) {
            String string2 = string;
            if (string == null) {
                string2 = "";
            }
            try {
                zzaHd.invoke((Object)workSource, n, string2);
                return;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
                return;
            }
        }
        if (zzaHc != null) {
            try {
                zzaHc.invoke((Object)workSource, n);
                return;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
            }
        }
    }

    public static boolean zzaO(Context context) {
        boolean bl = false;
        if (context == null) {
            return false;
        }
        if (context.getPackageManager() == null) {
            return false;
        }
        if (zzacx.zzaQ(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) == 0) {
            bl = true;
        }
        return bl;
    }

    public static List<String> zzb(WorkSource workSource) {
        int n = workSource == null ? 0 : zzy.zza(workSource);
        if (n == 0) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < n; ++i) {
            String string = zzy.zza(workSource, i);
            if (zzv.zzdD(string)) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    public static WorkSource zzf(int n, String string) {
        WorkSource workSource = new WorkSource();
        zzy.zza(workSource, n, string);
        return workSource;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static WorkSource zzy(Context object, String string) {
        if (object != null) {
            block4 : {
                if (object.getPackageManager() == null) {
                    return null;
                }
                try {
                    object = zzacx.zzaQ((Context)object).getApplicationInfo(string, 0);
                    if (object != null) break block4;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {}
                object = String.valueOf(string);
                object = object.length() != 0 ? "Could not get applicationInfo from package: ".concat((String)object) : new String("Could not get applicationInfo from package: ");
                Log.e((String)"WorkSourceUtil", (String)object);
                return null;
            }
            return zzy.zzf(object.uid, string);
            String string2 = String.valueOf(string);
            string2 = string2.length() != 0 ? "Could not find package: ".concat(string2) : new String("Could not find package: ");
            Log.e((String)"WorkSourceUtil", (String)string2);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzyL() {
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzyM() {
        if (!zzs.zzyE()) return null;
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE, String.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzyN() {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzyO() {
        try {
            return WorkSource.class.getMethod("get", Integer.TYPE);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method zzyP() {
        if (!zzs.zzyE()) return null;
        try {
            return WorkSource.class.getMethod("getName", Integer.TYPE);
        }
        catch (Exception exception) {
            return null;
        }
    }
}
