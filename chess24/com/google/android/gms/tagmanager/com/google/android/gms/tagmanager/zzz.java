/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.tagmanager;

import android.util.Log;
import com.google.android.gms.tagmanager.zzbp;

public class zzz
implements zzbp {
    private int zzaeb = 5;

    @Override
    public void e(String string) {
        if (this.zzaeb <= 6) {
            Log.e((String)"GoogleTagManager", (String)string);
        }
    }

    @Override
    public void setLogLevel(int n) {
        this.zzaeb = n;
    }

    @Override
    public void v(String string) {
        if (this.zzaeb <= 2) {
            Log.v((String)"GoogleTagManager", (String)string);
        }
    }

    @Override
    public void zzb(String string, Throwable throwable) {
        if (this.zzaeb <= 6) {
            Log.e((String)"GoogleTagManager", (String)string, (Throwable)throwable);
        }
    }

    @Override
    public void zzbc(String string) {
        if (this.zzaeb <= 3) {
            Log.d((String)"GoogleTagManager", (String)string);
        }
    }

    @Override
    public void zzbd(String string) {
        if (this.zzaeb <= 4) {
            Log.i((String)"GoogleTagManager", (String)string);
        }
    }

    @Override
    public void zzbe(String string) {
        if (this.zzaeb <= 5) {
            Log.w((String)"GoogleTagManager", (String)string);
        }
    }

    @Override
    public void zzc(String string, Throwable throwable) {
        if (this.zzaeb <= 5) {
            Log.w((String)"GoogleTagManager", (String)string, (Throwable)throwable);
        }
    }
}
