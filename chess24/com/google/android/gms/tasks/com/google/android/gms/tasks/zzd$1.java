/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

class zzd
implements Runnable {
    final /* synthetic */ Task zzbLu;

    zzd(Task task) {
        this.zzbLu = task;
    }

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
                zzd.this.zzbLz.onFailure(this.zzbLu.getException());
            }
            return;
        }
    }
}
