/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.tasks.zzh;
import java.util.concurrent.ExecutionException;

private static final class Tasks.zzc
implements Tasks.zzb {
    private final zzh<Void> zzbLF;
    private Exception zzbLK;
    private final int zzbLM;
    private int zzbLN;
    private int zzbLO;
    private final Object zzrN = new Object();

    public Tasks.zzc(int n, zzh<Void> zzh2) {
        this.zzbLM = n;
        this.zzbLF = zzh2;
    }

    private void zzSh() {
        if (this.zzbLN + this.zzbLO == this.zzbLM) {
            if (this.zzbLK == null) {
                this.zzbLF.setResult(null);
                return;
            }
            zzh<Void> zzh2 = this.zzbLF;
            int n = this.zzbLO;
            int n2 = this.zzbLM;
            StringBuilder stringBuilder = new StringBuilder(54);
            stringBuilder.append(n);
            stringBuilder.append(" out of ");
            stringBuilder.append(n2);
            stringBuilder.append(" underlying tasks failed");
            zzh2.setException(new ExecutionException(stringBuilder.toString(), this.zzbLK));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onFailure(@NonNull Exception exception) {
        Object object = this.zzrN;
        synchronized (object) {
            ++this.zzbLO;
            this.zzbLK = exception;
            this.zzSh();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSuccess(Object object) {
        object = this.zzrN;
        synchronized (object) {
            ++this.zzbLN;
            this.zzSh();
            return;
        }
    }
}
