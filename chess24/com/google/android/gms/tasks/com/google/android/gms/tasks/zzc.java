/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzf;
import java.util.concurrent.Executor;

class zzc<TResult>
implements zzf<TResult> {
    private final Executor zzbDK;
    private OnCompleteListener<TResult> zzbLx;
    private final Object zzrN = new Object();

    public zzc(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzbDK = executor;
        this.zzbLx = onCompleteListener;
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
            this.zzbLx = null;
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
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzbLx == null) {
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
                Object object = zzc.this.zzrN;
                synchronized (object) {
                    if (zzc.this.zzbLx != null) {
                        zzc.this.zzbLx.onComplete(task);
                    }
                    return;
                }
            }
        });
    }

}
