/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzaa;

public final class zzzs<O extends Api.ApiOptions> {
    private final Api<O> zzawb;
    private final O zzaxG;
    private final boolean zzayv;
    private final int zzayw;

    private zzzs(Api<O> api) {
        this.zzayv = true;
        this.zzawb = api;
        this.zzaxG = null;
        this.zzayw = System.identityHashCode(this);
    }

    private zzzs(Api<O> api, O o) {
        this.zzayv = false;
        this.zzawb = api;
        this.zzaxG = o;
        this.zzayw = zzaa.hashCode(this.zzawb, this.zzaxG);
    }

    public static <O extends Api.ApiOptions> zzzs<O> zza(Api<O> api, O o) {
        return new zzzs<O>(api, o);
    }

    public static <O extends Api.ApiOptions> zzzs<O> zzb(Api<O> api) {
        return new zzzs<O>(api);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzzs)) {
            return false;
        }
        object = (zzzs)object;
        if (!this.zzayv && !object.zzayv && zzaa.equal(this.zzawb, object.zzawb) && zzaa.equal(this.zzaxG, object.zzaxG)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.zzayw;
    }

    public String zzuV() {
        return this.zzawb.getName();
    }
}
