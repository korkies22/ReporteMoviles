/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzq;

public class zzace {
    private final String mTag;
    private final String zzaEV;
    private final zzq zzaFr;
    private final int zzaeb;

    private zzace(String string, String string2) {
        this.zzaEV = string2;
        this.mTag = string;
        this.zzaFr = new zzq(string);
        this.zzaeb = this.getLogLevel();
    }

    public /* varargs */ zzace(String string, String ... arrstring) {
        this(string, zzace.zzd(arrstring));
    }

    private int getLogLevel() {
        int n;
        for (n = 2; 7 >= n && !Log.isLoggable((String)this.mTag, (int)n); ++n) {
        }
        return n;
    }

    private static /* varargs */ String zzd(String ... arrstring) {
        if (arrstring.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (String string : arrstring) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append(string);
        }
        stringBuilder.append(']');
        stringBuilder.append(' ');
        return stringBuilder.toString();
    }

    protected /* varargs */ String format(String string, Object ... arrobject) {
        String string2 = string;
        if (arrobject != null) {
            string2 = string;
            if (arrobject.length > 0) {
                string2 = String.format(string, arrobject);
            }
        }
        return this.zzaEV.concat(string2);
    }

    public /* varargs */ void zza(String string, Object ... arrobject) {
        if (this.zzai(2)) {
            Log.v((String)this.mTag, (String)this.format(string, arrobject));
        }
    }

    public boolean zzai(int n) {
        if (this.zzaeb <= n) {
            return true;
        }
        return false;
    }

    public /* varargs */ void zzb(String string, Object ... arrobject) {
        if (this.zzai(3)) {
            Log.d((String)this.mTag, (String)this.format(string, arrobject));
        }
    }

    public /* varargs */ void zze(String string, Object ... arrobject) {
        Log.i((String)this.mTag, (String)this.format(string, arrobject));
    }

    public /* varargs */ void zzf(String string, Object ... arrobject) {
        Log.w((String)this.mTag, (String)this.format(string, arrobject));
    }
}
