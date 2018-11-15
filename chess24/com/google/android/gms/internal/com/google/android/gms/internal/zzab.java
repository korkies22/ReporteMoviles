/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzi;
import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzm;
import com.google.android.gms.internal.zzx;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class zzab
extends zzk<String> {
    private final zzm.zzb<String> zzaF;

    public zzab(int n, String string, zzm.zzb<String> zzb2, zzm.zza zza2) {
        super(n, string, zza2);
        this.zzaF = zzb2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected zzm<String> zza(zzi zzi2) {
        String string;
        try {
            string = new String(zzi2.data, zzx.zza(zzi2.zzy));
            return zzm.zza(string, zzx.zzb(zzi2));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {}
        string = new String(zzi2.data);
        return zzm.zza(string, zzx.zzb(zzi2));
    }

    @Override
    protected /* synthetic */ void zza(Object object) {
        this.zzi((String)object);
    }

    protected void zzi(String string) {
        this.zzaF.zzb(string);
    }
}
