/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzf;
import com.google.android.gms.tasks.zzh;
import java.util.concurrent.Executor;

class zzb<TResult, TContinuationResult>
implements OnFailureListener,
OnSuccessListener<TContinuationResult>,
zzf<TResult> {
    private final Executor zzbDK;
    private final Continuation<TResult, Task<TContinuationResult>> zzbLs;
    private final zzh<TContinuationResult> zzbLt;

    public zzb(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzh<TContinuationResult> zzh2) {
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
                Task task2;
                block5 : {
                    try {
                        task2 = (Task)zzb.this.zzbLs.then(task);
                        if (task2 != null) break block5;
                    }
                    catch (Exception exception) {
                        zzb.this.zzbLt.setException(exception);
                        return;
                    }
                    catch (RuntimeExecutionException runtimeExecutionException) {
                        zzh zzh2;
                        Exception exception;
                        if (runtimeExecutionException.getCause() instanceof Exception) {
                            zzh2 = zzb.this.zzbLt;
                            exception = (Exception)runtimeExecutionException.getCause();
                        } else {
                            zzh2 = zzb.this.zzbLt;
                        }
                        zzh2.setException(exception);
                        return;
                    }
                    zzb.this.onFailure(new NullPointerException("Continuation returned null"));
                    return;
                }
                task2.addOnSuccessListener(TaskExecutors.zzbLG, zzb.this);
                task2.addOnFailureListener(TaskExecutors.zzbLG, (OnFailureListener)zzb.this);
                return;
            }
        });
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        this.zzbLt.setException(exception);
    }

    @Override
    public void onSuccess(TContinuationResult TContinuationResult) {
        this.zzbLt.setResult(TContinuationResult);
    }

}
