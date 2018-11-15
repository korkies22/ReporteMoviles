/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.google.android.gms.common.util.zzs;
import java.io.File;

public class zzw {
    @TargetApi(value=21)
    public static File getNoBackupFilesDir(Context context) {
        if (zzs.zzyI()) {
            return context.getNoBackupFilesDir();
        }
        return zzw.zze(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    private static File zze(File object) {
        synchronized (zzw.class) {
            block5 : {
                block6 : {
                    if (object.exists() || object.mkdirs()) break block5;
                    boolean bl = object.exists();
                    if (!bl) break block6;
                    return object;
                }
                object = String.valueOf(object.getPath());
                object = object.length() != 0 ? "Unable to create no-backup dir ".concat((String)object) : new String("Unable to create no-backup dir ");
                Log.w((String)"SupportV4Utils", (String)object);
                return null;
            }
            return object;
            finally {
            }
        }
    }
}
