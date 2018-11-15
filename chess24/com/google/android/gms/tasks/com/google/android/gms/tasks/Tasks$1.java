/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.zzh;
import java.util.concurrent.Callable;

final class Tasks
implements Runnable {
    final /* synthetic */ Callable zzWN;

    Tasks(Callable callable) {
        this.zzWN = callable;
    }

    @Override
    public void run() {
        try {
            zzh.this.setResult(this.zzWN.call());
            return;
        }
        catch (Exception exception) {
            zzh.this.setException(exception);
            return;
        }
    }
}
