/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.os.Build;
import com.google.android.gms.tagmanager.zzbo;
import java.io.File;

class zzan {
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
        String string = String.valueOf(Build.VERSION.SDK);
        string = string.length() != 0 ? "Invalid version number: ".concat(string) : new String("Invalid version number: ");
        zzbo.e(string);
        return 0;
    }

    @TargetApi(value=9)
    static boolean zzbZ(String object) {
        if (zzan.version() < 9) {
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
