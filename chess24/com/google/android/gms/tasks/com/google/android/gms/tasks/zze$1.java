/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

class zze
implements Runnable {
    final /* synthetic */ Task zzbLu;

    zze(Task task) {
        this.zzbLu = task;
    }

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
                zze.this.zzbLB.onSuccess(this.zzbLu.getResult());
            }
            return;
        }
    }
}
