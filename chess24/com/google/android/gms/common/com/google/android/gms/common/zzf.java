/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzacx;

public class zzf {
    private static zzf zzaxr;
    private final Context mContext;

    private zzf(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzf zzav(Context context) {
        zzac.zzw(context);
        synchronized (zzf.class) {
            if (zzaxr == null) {
                zzd.zzao(context);
                zzaxr = new zzf(context);
            }
            return zzaxr;
        }
    }

    /* varargs */ zzd.zza zza(PackageInfo object, zzd.zza ... arrzza) {
        if (object.signatures == null) {
            return null;
        }
        if (object.signatures.length != 1) {
            Log.w((String)"GoogleSignatureVerifier", (String)"Package has more than one signature.");
            return null;
        }
        object = object.signatures;
        object = new zzd.zzb(object[0].toByteArray());
        for (int i = 0; i < arrzza.length; ++i) {
            if (!arrzza[i].equals(object)) continue;
            return arrzza[i];
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean zza(PackageInfo object, boolean bl) {
        if (object != null && object.signatures != null) {
            void var2_3;
            zzd.zza[] arrzza = var2_3 != false ? zzd.zzd.zzaxk : new zzd.zza[]{zzd.zzd.zzaxk[0]};
            zzd.zza zza2 = this.zza((PackageInfo)object, arrzza);
            if (zza2 != null) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(PackageManager packageManager, int n) {
        String[] arrstring = zzacx.zzaQ(this.mContext).getPackagesForUid(n);
        if (arrstring != null) {
            if (arrstring.length == 0) {
                return false;
            }
            int n2 = arrstring.length;
            for (n = 0; n < n2; ++n) {
                if (!this.zzb(packageManager, arrstring[n])) continue;
                return true;
            }
        }
        return false;
    }

    public boolean zza(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zze.zzar(this.mContext)) {
            return this.zzb(packageInfo, true);
        }
        boolean bl = this.zzb(packageInfo, false);
        if (!bl && this.zzb(packageInfo, true)) {
            Log.w((String)"GoogleSignatureVerifier", (String)"Test-keys aren't accepted on this build.");
        }
        return bl;
    }

    boolean zzb(PackageInfo object, boolean bl) {
        if (object.signatures.length != 1) {
            Log.w((String)"GoogleSignatureVerifier", (String)"Package has more than one signature.");
            return false;
        }
        zzd.zzb zzb2 = new zzd.zzb(object.signatures[0].toByteArray());
        object = object.packageName;
        if (bl) {
            return zzd.zzb((String)object, zzb2);
        }
        return zzd.zza((String)object, zzb2);
    }

    public boolean zzb(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (this.zza(packageInfo, false)) {
            return true;
        }
        if (this.zza(packageInfo, true)) {
            if (zze.zzar(this.mContext)) {
                return true;
            }
            Log.w((String)"GoogleSignatureVerifier", (String)"Test-keys aren't accepted on this build.");
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean zzb(PackageManager packageManager, String string) {
        try {
            string = zzacx.zzaQ(this.mContext).getPackageInfo(string, 64);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
        return this.zza(packageManager, (PackageInfo)string);
    }
}
