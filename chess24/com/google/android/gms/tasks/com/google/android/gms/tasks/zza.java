/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzf;
import com.google.android.gms.tasks.zzh;
import java.util.concurrent.Executor;

class zza<TResult, TContinuationResult>
implements zzf<TResult> {
    private final Executor zzbDK;
    private final Continuation<TResult, TContinuationResult> zzbLs;
    private final zzh<TContinuationResult> zzbLt;

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzh<TContinuationResult> zzh2) {
        this.zzbDK = executor;
        this.zzbLs = continuation;
        this.zzbLt = zzh2;
    }

    @Override
    public void cancel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onComplete(final @NonNull Task<TResult> task) {
        this.zzbDK.execute(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object TContinuationResult;
                try {
                    TContinuationResult = zza.this.zzbLs.then(task);
                }
                catch (Exception exception) {
                    zza.this.zzbLt.setException(exception);
                    return;
                }
                catch (RuntimeExecutionException runtimeExecutionException) {
                    zzh zzh2;
                    Exception exception;
                    if (runtimeExecutionException.getCause() instanceof Exception) {
                        zzh2 = zza.this.zzbLt;
                        exception = (Exception)runtimeExecutionException.getCause();
                    } else {
                        zzh2 = zza.this.zzbLt;
                    }
                    zzh2.setException(exception);
                    return;
                }
                zza.this.zzbLt.setResult(TContinuationResult);
                return;
            }
        });
    }

}
