/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzac;

public final class zzq {
    public static final int zzaES = 23 - " PII_LOG".length();
    private static final String zzaET;
    private final String zzaEU;
    private final String zzaEV;

    public zzq(String string) {
        this(string, null);
    }

    public zzq(String string, String string2) {
        zzac.zzb(string, (Object)"log tag cannot be null");
        boolean bl = string.length() <= 23;
        zzac.zzb(bl, "tag \"%s\" is longer than the %d character maximum", string, 23);
        this.zzaEU = string;
        if (string2 != null && string2.length() > 0) {
            this.zzaEV = string2;
            return;
        }
        this.zzaEV = null;
    }

    private String zzdu(String string) {
        if (this.zzaEV == null) {
            return string;
        }
        return this.zzaEV.concat(string);
    }

    public void zzD(String string, String string2) {
        if (this.zzcQ(3)) {
            Log.d((String)string, (String)this.zzdu(string2));
        }
    }

    public void zzE(String string, String string2) {
        if (this.zzcQ(5)) {
            Log.w((String)string, (String)this.zzdu(string2));
        }
    }

    public void zzF(String string, String string2) {
        if (this.zzcQ(6)) {
            Log.e((String)string, (String)this.zzdu(string2));
        }
    }

    public void zzb(String string, String string2, Throwable throwable) {
        if (this.zzcQ(4)) {
            Log.i((String)string, (String)this.zzdu(string2), (Throwable)throwable);
        }
    }

    public void zzc(String string, String string2, Throwable throwable) {
        if (this.zzcQ(5)) {
            Log.w((String)string, (String)this.zzdu(string2), (Throwable)throwable);
        }
    }

    public boolean zzcQ(int n) {
        return Log.isLoggable((String)this.zzaEU, (int)n);
    }

    public void zzd(String string, String string2, Throwable throwable) {
        if (this.zzcQ(6)) {
            Log.e((String)string, (String)this.zzdu(string2), (Throwable)throwable);
        }
    }

    public void zze(String string, String string2, Throwable throwable) {
        if (this.zzcQ(7)) {
            Log.e((String)string, (String)this.zzdu(string2), (Throwable)throwable);
            Log.wtf((String)string, (String)this.zzdu(string2), (Throwable)throwable);
        }
    }
}
