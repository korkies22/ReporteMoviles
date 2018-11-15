/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

class zzc
implements Runnable {
    final /* synthetic */ Task zzbLu;

    zzc(Task task) {
        this.zzbLu = task;
    }

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
                zzc.this.zzbLx.onComplete(this.zzbLu);
            }
            return;
        }
    }
}
