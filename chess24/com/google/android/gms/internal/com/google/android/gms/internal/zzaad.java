/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzabq;
import com.google.android.gms.internal.zzzx;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class zzaad {
    private final Map<zzzx<?>, Boolean> zzazC = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zzazD = Collections.synchronizedMap(new WeakHashMap());

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zza(boolean bl, Status status) {
        Object object;
        Object object2 = this.zzazC;
        synchronized (object2) {
            object = new HashMap(this.zzazC);
        }
        Object object32 = this.zzazD;
        synchronized (object32) {
            object2 = new HashMap(this.zzazD);
        }
        for (Object object32 : object.entrySet()) {
            if (!bl && !((Boolean)object32.getValue()).booleanValue()) continue;
            ((zzzx)object32.getKey()).zzB(status);
        }
        object2 = object2.entrySet().iterator();
        while (object2.hasNext()) {
            object = (Map.Entry)object2.next();
            if (!bl && !((Boolean)object.getValue()).booleanValue()) continue;
            ((TaskCompletionSource)object.getKey()).trySetException(new zza(status));
        }
        return;
    }

    void zza(final zzzx<? extends Result> zzzx2, boolean bl) {
        this.zzazC.put(zzzx2, bl);
        zzzx2.zza(new PendingResult.zza(){

            @Override
            public void zzx(Status status) {
                zzaad.this.zzazC.remove(zzzx2);
            }
        });
    }

    <TResult> void zza(final TaskCompletionSource<TResult> taskCompletionSource, boolean bl) {
        this.zzazD.put(taskCompletionSource, bl);
        taskCompletionSource.getTask().addOnCompleteListener(new OnCompleteListener<TResult>(){

            @Override
            public void onComplete(@NonNull Task<TResult> task) {
                zzaad.this.zzazD.remove(taskCompletionSource);
            }
        });
    }

    boolean zzvu() {
        if (this.zzazC.isEmpty() && this.zzazD.isEmpty()) {
            return false;
        }
        return true;
    }

    public void zzvv() {
        this.zza(false, zzaap.zzaAO);
    }

    public void zzvw() {
        this.zza(true, zzabq.zzaBV);
    }

}
