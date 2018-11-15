/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzf;
import java.util.ArrayDeque;
import java.util.Queue;

class zzg<TResult> {
    private Queue<zzf<TResult>> zzbLD;
    private boolean zzbLE;
    private final Object zzrN = new Object();

    zzg() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(@NonNull Task<TResult> task) {
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzbLD != null && !this.zzbLE) {
                this.zzbLE = true;
            } else {
                return;
            }
        }
        do {
            zzf<TResult> zzf2;
            object = this.zzrN;
            synchronized (object) {
                zzf2 = this.zzbLD.poll();
                if (zzf2 == null) {
                    this.zzbLE = false;
                    return;
                }
            }
            zzf2.onComplete(task);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(@NonNull zzf<TResult> zzf2) {
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzbLD == null) {
                this.zzbLD = new ArrayDeque<zzf<TResult>>();
            }
            this.zzbLD.add(zzf2);
            return;
        }
    }
}
