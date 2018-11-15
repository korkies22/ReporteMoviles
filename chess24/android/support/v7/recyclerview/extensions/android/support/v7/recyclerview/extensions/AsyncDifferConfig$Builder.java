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
import android.support.annotation.RestrictTo;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public static final class AsyncDifferConfig.Builder<T> {
    private static Executor sDiffExecutor;
    private static final Object sExecutorLock;
    private static final Executor sMainThreadExecutor;
    private Executor mBackgroundThreadExecutor;
    private final DiffUtil.ItemCallback<T> mDiffCallback;
    private Executor mMainThreadExecutor;

    static {
        sExecutorLock = new Object();
        sMainThreadExecutor = new MainThreadExecutor();
    }

    public AsyncDifferConfig.Builder(@NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.mDiffCallback = itemCallback;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    public AsyncDifferConfig<T> build() {
        if (this.mMainThreadExecutor == null) {
            this.mMainThreadExecutor = sMainThreadExecutor;
        }
        if (this.mBackgroundThreadExecutor == null) {
            Object object = sExecutorLock;
            synchronized (object) {
                if (sDiffExecutor == null) {
                    sDiffExecutor = Executors.newFixedThreadPool(2);
                }
            }
            this.mBackgroundThreadExecutor = sDiffExecutor;
        }
        return new AsyncDifferConfig(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback, null);
    }

    @NonNull
    public AsyncDifferConfig.Builder<T> setBackgroundThreadExecutor(Executor executor) {
        this.mBackgroundThreadExecutor = executor;
        return this;
    }

    @NonNull
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public AsyncDifferConfig.Builder<T> setMainThreadExecutor(Executor executor) {
        this.mMainThreadExecutor = executor;
        return this;
    }

    private static class MainThreadExecutor
    implements Executor {
        final Handler mHandler = new Handler(Looper.getMainLooper());

        private MainThreadExecutor() {
        }

        @Override
        public void execute(@NonNull Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

}
