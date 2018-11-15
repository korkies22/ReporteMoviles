/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.http.AndroidHttpClient
 *  android.os.Build
 *  android.os.Build$VERSION
 *  org.apache.http.client.HttpClient
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzf;
import com.google.android.gms.internal.zzl;
import com.google.android.gms.internal.zzt;
import com.google.android.gms.internal.zzv;
import com.google.android.gms.internal.zzw;
import com.google.android.gms.internal.zzy;
import com.google.android.gms.internal.zzz;
import java.io.File;
import org.apache.http.client.HttpClient;

public class zzac {
    public static zzl zza(Context context) {
        return zzac.zza(context, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzl zza(Context object, zzy zzy2) {
        String string;
        File file;
        block3 : {
            file = new File(object.getCacheDir(), "volley");
            try {
                string = object.getPackageName();
                int n = object.getPackageManager().getPackageInfo((String)string, (int)0).versionCode;
                object = new StringBuilder(12 + String.valueOf(string).length());
                object.append(string);
                object.append("/");
                object.append(n);
                string = object.toString();
                break block3;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {}
            string = "volley/0";
        }
        object = zzy2;
        if (zzy2 == null) {
            object = Build.VERSION.SDK_INT >= 9 ? new zzz() : new zzw((HttpClient)AndroidHttpClient.newInstance((String)string));
        }
        object = new zzt((zzy)object);
        object = new zzl(new zzv(file), (zzf)object);
        object.start();
        return object;
    }
}
