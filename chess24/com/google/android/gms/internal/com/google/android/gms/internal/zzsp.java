/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.os.Build;
import com.google.android.gms.internal.zzsw;
import java.io.File;

public class zzsp {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int version() {
        try {
            return Integer.parseInt(Build.VERSION.SDK);
        }
        catch (NumberFormatException numberFormatException) {}
        zzsw.zzf("Invalid version number", Build.VERSION.SDK);
        return 0;
    }

    public static boolean zzbZ(String object) {
        if (zzsp.version() < 9) {
            return false;
        }
        object = new File((String)object);
        object.setReadable(false, false);
        object.setWritable(false, false);
        object.setReadable(true, true);
        object.setWritable(true, true);
        return true;
    }
}
