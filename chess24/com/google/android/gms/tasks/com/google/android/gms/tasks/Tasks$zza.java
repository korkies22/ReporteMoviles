/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Tasks;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

private static final class Tasks.zza
implements Tasks.zzb {
    private final CountDownLatch zzth = new CountDownLatch(1);

    private Tasks.zza() {
    }

    public void await() throws InterruptedException {
        this.zzth.await();
    }

    public boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.zzth.await(l, timeUnit);
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        this.zzth.countDown();
    }

    @Override
    public void onSuccess(Object object) {
        this.zzth.countDown();
    }
}
