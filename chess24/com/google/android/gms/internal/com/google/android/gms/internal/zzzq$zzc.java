/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzabe;
import com.google.android.gms.internal.zzabf;
import com.google.android.gms.internal.zzabr;
import com.google.android.gms.internal.zzzq;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

public static final class zzzq.zzc
extends zzzq.zza {
    public final zzabe<Api.zzb, ?> zzayq;
    public final zzabr<Api.zzb, ?> zzayr;

    public zzzq.zzc(zzabf zzabf2, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zzayq = zzabf2.zzayq;
        this.zzayr = zzabf2.zzayr;
    }

    @Override
    public void zzb(zzaap.zza<?> zza2) throws RemoteException {
        if (this.zzayq.zzwp() != null) {
            zza2.zzwc().put(this.zzayq.zzwp(), new zzabf(this.zzayq, this.zzayr));
        }
    }
}
