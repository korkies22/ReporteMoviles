/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzf;
import java.util.concurrent.Executor;

class zzd<TResult>
implements zzf<TResult> {
    private final Executor zzbDK;
    private OnFailureListener zzbLz;
    private final Object zzrN = new Object();

    public zzd(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzbDK = executor;
        this.zzbLz = onFailureListener;
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
            this.zzbLz = null;
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
        if (task.isSuccessful()) {
            return;
        }
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzbLz == null) {
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
                Object object = zzd.this.zzrN;
                synchronized (object) {
                    if (zzd.this.zzbLz != null) {
                        zzd.this.zzbLz.onFailure(task.getException());
                    }
                    return;
                }
            }
        });
    }

}
