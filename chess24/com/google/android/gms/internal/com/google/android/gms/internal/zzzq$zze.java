/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzabe;
import com.google.android.gms.internal.zzabf;
import com.google.android.gms.internal.zzzq;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

public static final class zzzq.zze
extends zzzq.zza {
    public final zzaaz.zzb<?> zzayu;

    public zzzq.zze(zzaaz.zzb<?> zzb2, TaskCompletionSource<Void> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zzayu = zzb2;
    }

    @Override
    public void zzb(zzaap.zza<?> object) throws RemoteException {
        if ((object = object.zzwc().remove(this.zzayu)) != null) {
            object.zzayq.zzwq();
            return;
        }
        Log.wtf((String)"UnregisterListenerTask", (String)"Received call to unregister a listener without a matching registration call.", (Throwable)new Exception());
        this.zzayo.trySetException(new zza(Status.zzayj));
    }
}
