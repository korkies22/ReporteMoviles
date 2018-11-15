/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.WorkSource
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.stats.zze;
import com.google.android.gms.common.stats.zzg;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzv;
import com.google.android.gms.common.util.zzy;
import java.util.List;

public class zzayd {
    private static boolean DEBUG = false;
    private static String TAG = "WakeLock";
    private static String zzbCt = "*gcore*:";
    private final Context mContext;
    private final String zzaGw;
    private final String zzaGy;
    private final PowerManager.WakeLock zzbCu;
    private final int zzbCv;
    private final String zzbCw;
    private boolean zzbCx;
    private int zzbCy;
    private int zzbCz;
    private WorkSource zzbiJ;

    public zzayd(Context context, int n, String string) {
        String string2 = context == null ? null : context.getPackageName();
        this(context, n, string, null, string2);
    }

    @SuppressLint(value={"UnwrappedWakeLock"})
    public zzayd(Context context, int n, String string, String string2, String string3) {
        this(context, n, string, string2, string3, null);
    }

    @SuppressLint(value={"UnwrappedWakeLock"})
    public zzayd(Context context, int n, String string, String string2, String string3, String string4) {
        this.zzbCx = true;
        zzac.zzh(string, "Wake lock name can NOT be empty");
        this.zzbCv = n;
        this.zzbCw = string2;
        this.zzaGy = string4;
        this.mContext = context.getApplicationContext();
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            string2 = String.valueOf(zzbCt);
            string4 = String.valueOf(string);
            string2 = string4.length() != 0 ? string2.concat(string4) : new String(string2);
            this.zzaGw = string2;
        } else {
            this.zzaGw = string;
        }
        this.zzbCu = ((PowerManager)context.getSystemService("power")).newWakeLock(n, string);
        if (zzy.zzaO(this.mContext)) {
            string = string3;
            if (zzv.zzdD(string3)) {
                string = context.getPackageName();
            }
            this.zzbiJ = zzy.zzy(context, string);
            this.zzc(this.zzbiJ);
        }
    }

    private void zzd(WorkSource workSource) {
        try {
            this.zzbCu.setWorkSource(workSource);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.wtf((String)TAG, (String)illegalArgumentException.toString());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzgP(String string) {
        boolean bl = this.zzgQ(string);
        string = this.zzo(string, bl);
        synchronized (this) {
            block6 : {
                block5 : {
                    block4 : {
                        int n;
                        if (!this.zzbCx) break block4;
                        this.zzbCy = n = this.zzbCy - 1;
                        if (n == 0 || bl) break block5;
                    }
                    if (this.zzbCx || this.zzbCz != 1) break block6;
                }
                zzg.zzyr().zza(this.mContext, zze.zza(this.zzbCu, string), 8, this.zzaGw, string, this.zzaGy, this.zzbCv, zzy.zzb(this.zzbiJ));
                --this.zzbCz;
            }
            return;
        }
    }

    private boolean zzgQ(String string) {
        if (!TextUtils.isEmpty((CharSequence)string) && !string.equals(this.zzbCw)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzm(String string, long l) {
        boolean bl = this.zzgQ(string);
        string = this.zzo(string, bl);
        synchronized (this) {
            block6 : {
                block5 : {
                    block4 : {
                        if (!this.zzbCx) break block4;
                        int n = this.zzbCy;
                        this.zzbCy = n + 1;
                        if (n == 0 || bl) break block5;
                    }
                    if (this.zzbCx || this.zzbCz != 0) break block6;
                }
                zzg.zzyr().zza(this.mContext, zze.zza(this.zzbCu, string), 7, this.zzaGw, string, this.zzaGy, this.zzbCv, zzy.zzb(this.zzbiJ), l);
                ++this.zzbCz;
            }
            return;
        }
    }

    private String zzo(String string, boolean bl) {
        if (this.zzbCx && bl) {
            return string;
        }
        return this.zzbCw;
    }

    public void acquire(long l) {
        if (!zzs.zzyA() && this.zzbCx) {
            String string = TAG;
            String string2 = String.valueOf(this.zzaGw);
            string2 = string2.length() != 0 ? "Do not acquire with timeout on reference counted WakeLocks before ICS. wakelock: ".concat(string2) : new String("Do not acquire with timeout on reference counted WakeLocks before ICS. wakelock: ");
            Log.wtf((String)string, (String)string2);
        }
        this.zzm(null, l);
        this.zzbCu.acquire(l);
    }

    public boolean isHeld() {
        return this.zzbCu.isHeld();
    }

    public void release() {
        this.zzgP(null);
        this.zzbCu.release();
    }

    public void setReferenceCounted(boolean bl) {
        this.zzbCu.setReferenceCounted(bl);
        this.zzbCx = bl;
    }

    public void zzc(WorkSource workSource) {
        if (workSource != null && zzy.zzaO(this.mContext)) {
            if (this.zzbiJ != null) {
                this.zzbiJ.add(workSource);
            } else {
                this.zzbiJ = workSource;
            }
            this.zzd(this.zzbiJ);
        }
    }
}
