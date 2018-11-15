/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

final class zzab
implements PendingResult.zza {
    final /* synthetic */ TaskCompletionSource zzaFb;
    final /* synthetic */ zzab.zza zzaFc;
    final /* synthetic */ zzab.zzb zzaFd;

    zzab(TaskCompletionSource taskCompletionSource, zzab.zza zza2, zzab.zzb zzb2) {
        this.zzaFb = taskCompletionSource;
        this.zzaFc = zza2;
        this.zzaFd = zzb2;
    }

    @Override
    public void zzx(Status status) {
        if (status.isSuccess()) {
            status = PendingResult.this.await(0L, TimeUnit.MILLISECONDS);
            this.zzaFb.setResult(this.zzaFc.zzf(status));
            return;
        }
        this.zzaFb.setException(this.zzaFd.zzG(status));
    }
}
