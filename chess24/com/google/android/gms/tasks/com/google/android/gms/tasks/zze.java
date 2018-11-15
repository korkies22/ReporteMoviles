/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzf;
import java.util.concurrent.Executor;

class zze<TResult>
implements zzf<TResult> {
    private final Executor zzbDK;
    private OnSuccessListener<? super TResult> zzbLB;
    private final Object zzrN = new Object();

    public zze(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzbDK = executor;
        this.zzbLB = onSuccessListener;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void cancel() {
        Object object = this.zzrN;
        synchronized (object) {
            this.zzbLB = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onComplete(final @NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            return;
        }
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzbLB == null) {
                return;
            }
        }
        this.zzbDK.execute(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = zze.this.zzrN;
                synchronized (object) {
                    if (zze.this.zzbLB != null) {
                        zze.this.zzbLB.onSuccess(task.getResult());
                    }
                    return;
                }
            }
        });
    }

}
