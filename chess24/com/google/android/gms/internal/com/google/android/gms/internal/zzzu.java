/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.internal.zzzs;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Set;

public final class zzzu {
    private final ArrayMap<zzzs<?>, ConnectionResult> zzaxy = new ArrayMap();
    private final TaskCompletionSource<Void> zzayC = new TaskCompletionSource();
    private int zzayD;
    private boolean zzayE = false;

    public zzzu(Iterable<zzc<? extends Api.ApiOptions>> object) {
        object = object.iterator();
        while (object.hasNext()) {
            zzc zzc2 = (zzc)object.next();
            this.zzaxy.put(zzc2.getApiKey(), null);
        }
        this.zzayD = this.zzaxy.keySet().size();
    }

    public Task<Void> getTask() {
        return this.zzayC.getTask();
    }

    public void zza(zzzs<?> object, ConnectionResult connectionResult) {
        this.zzaxy.put((zzzs<?>)object, connectionResult);
        --this.zzayD;
        if (!connectionResult.isSuccess()) {
            this.zzayE = true;
        }
        if (this.zzayD == 0) {
            if (this.zzayE) {
                object = new zzb(this.zzaxy);
                this.zzayC.setException((Exception)object);
                return;
            }
            this.zzayC.setResult(null);
        }
    }

    public Set<zzzs<?>> zzuY() {
        return this.zzaxy.keySet();
    }

    public void zzuZ() {
        this.zzayC.setResult(null);
    }
}
