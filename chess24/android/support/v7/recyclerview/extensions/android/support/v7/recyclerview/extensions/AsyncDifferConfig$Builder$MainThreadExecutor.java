/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package android.support.v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import java.util.concurrent.Executor;

private static class AsyncDifferConfig.Builder.MainThreadExecutor
implements Executor {
    final Handler mHandler = new Handler(Looper.getMainLooper());

    private AsyncDifferConfig.Builder.MainThreadExecutor() {
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
