/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.internal.zzacx;

public class zzz {
    private static boolean zzPT;
    private static String zzaEW;
    private static int zzaEX;
    private static Object zztU;

    static {
        zztU = new Object();
    }

    public static String zzaD(Context context) {
        zzz.zzaF(context);
        return zzaEW;
    }

    public static int zzaE(Context context) {
        zzz.zzaF(context);
        return zzaEX;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void zzaF(Context object) {
        Object object2 = zztU;
        synchronized (object2) {
            block6 : {
                if (zzPT) {
                    return;
                }
                zzPT = true;
                String string = object.getPackageName();
                object = zzacx.zzaQ((Context)object);
                object = object.getApplicationInfo((String)string, (int)128).metaData;
                if (object != null) break block6;
                return;
            }
            try {
                zzaEW = object.getString("com.google.app.id");
                zzaEX = object.getInt("com.google.android.gms.version");
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.wtf((String)"MetadataValueReader", (String)"This should never happen.", (Throwable)nameNotFoundException);
            }
            return;
        }
    }
}
