/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zze;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

public class zzab {
    private static final zzb zzaEZ = new zzb(){

        @Override
        public com.google.android.gms.common.api.zza zzG(Status status) {
            return com.google.android.gms.common.internal.zzb.zzF(status);
        }
    };

    public static <R extends Result, T extends zze<R>> Task<T> zza(PendingResult<R> pendingResult, T t) {
        return zzab.zza(pendingResult, new zza<R, T>(){

            public T zze(R r) {
                zze.this.zzb(r);
                return (T)zze.this;
            }

            @Override
            public /* synthetic */ Object zzf(Result result) {
                return this.zze(result);
            }
        });
    }

    public static <R extends Result, T> Task<T> zza(PendingResult<R> pendingResult, zza<R, T> zza2) {
        return zzab.zza(pendingResult, zza2, zzaEZ);
    }

    public static <R extends Result, T> Task<T> zza(PendingResult<R> pendingResult, final zza<R, T> zza2, final zzb zzb2) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        pendingResult.zza(new PendingResult.zza(){

            @Override
            public void zzx(Status status) {
                if (status.isSuccess()) {
                    status = PendingResult.this.await(0L, TimeUnit.MILLISECONDS);
                    taskCompletionSource.setResult(zza2.zzf(status));
                    return;
                }
                taskCompletionSource.setException(zzb2.zzG(status));
            }
        });
        return taskCompletionSource.getTask();
    }

    public static interface zza<R extends Result, T> {
        public T zzf(R var1);
    }

    public static interface zzb {
        public com.google.android.gms.common.api.zza zzG(Status var1);
    }

}
