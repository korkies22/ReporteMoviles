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
import android.support.v7.util.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsyncDifferConfig<T> {
    @NonNull
    private final Executor mBackgroundThreadExecutor;
    @NonNull
    private final DiffUtil.ItemCallback<T> mDiffCallback;
    @NonNull
    private final Executor mMainThreadExecutor;

    private AsyncDifferConfig(@NonNull Executor executor, @NonNull Executor executor2, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.mMainThreadExecutor = executor;
        this.mBackgroundThreadExecutor = executor2;
        this.mDiffCallback = itemCallback;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.mBackgroundThreadExecutor;
    }

    @NonNull
    public DiffUtil.ItemCallback<T> getDiffCallback() {
        return this.mDiffCallback;
    }

    @NonNull
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }

    public static final class Builder<T> {
        private static Executor sDiffExecutor;
        private static final Object sExecutorLock;
        private static final Executor sMainThreadExecutor;
        private Executor mBackgroundThreadExecutor;
        private final DiffUtil.ItemCallback<T> mDiffCallback;
        private Executor mMainThreadExecutor;

        static {
            sExecutorLock = new Object();
            sMainThreadExecutor = new Builder$MainThreadExecutor();
        }

        public Builder(@NonNull DiffUtil.ItemCallback<T> itemCallback) {
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
            return new AsyncDifferConfig(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
        }

        @NonNull
        public Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.mBackgroundThreadExecutor = executor;
            return this;
        }

        @NonNull
        @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
        public Builder<T> setMainThreadExecutor(Executor executor) {
            this.mMainThreadExecutor = executor;
            return this;
        }
    }

    private static class Builder$MainThreadExecutor
    implements Executor {
        final Handler mHandler = new Handler(Looper.getMainLooper());

        private Builder$MainThreadExecutor() {
        }

        @Override
        public void execute(@NonNull Runnable runnable) {
            this.mHandler.post(runnable);
        }
    }

}
