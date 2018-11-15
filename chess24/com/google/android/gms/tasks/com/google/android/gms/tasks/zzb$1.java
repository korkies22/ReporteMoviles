/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzh;
import java.util.concurrent.Executor;

class zzb
implements Runnable {
    final /* synthetic */ Task zzbLu;

    zzb(Task task) {
        this.zzbLu = task;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Task task;
        block5 : {
            try {
                task = (Task)zzb.this.zzbLs.then(this.zzbLu);
                if (task != null) break block5;
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
        task.addOnSuccessListener(TaskExecutors.zzbLG, zzb.this);
        task.addOnFailureListener(TaskExecutors.zzbLG, (OnFailureListener)zzb.this);
        return;
    }
}
