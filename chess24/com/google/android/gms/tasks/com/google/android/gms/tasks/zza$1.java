/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzh;

class zza
implements Runnable {
    final /* synthetic */ Task zzbLu;

    zza(Task task) {
        this.zzbLu = task;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Object TContinuationResult;
        try {
            TContinuationResult = zza.this.zzbLs.then(this.zzbLu);
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
}
