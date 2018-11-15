/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.executor;

import android.arch.core.executor.DefaultTaskExecutor;
import android.arch.core.executor.TaskExecutor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import java.util.concurrent.Executor;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class ArchTaskExecutor
extends TaskExecutor {
    @NonNull
    private static final Executor sIOThreadExecutor;
    private static volatile ArchTaskExecutor sInstance;
    @NonNull
    private static final Executor sMainThreadExecutor;
    @NonNull
    private TaskExecutor mDefaultTaskExecutor;
    @NonNull
    private TaskExecutor mDelegate;

    static {
        sMainThreadExecutor = new Executor(){

            @Override
            public void execute(Runnable runnable) {
                ArchTaskExecutor.getInstance().postToMainThread(runnable);
            }
        };
        sIOThreadExecutor = new Executor(){

            @Override
            public void execute(Runnable runnable) {
                ArchTaskExecutor.getInstance().executeOnDiskIO(runnable);
            }
        };
    }

    private ArchTaskExecutor() {
        this.mDelegate = this.mDefaultTaskExecutor = new DefaultTaskExecutor();
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new ArchTaskExecutor();
            }
            return sInstance;
        }
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @Override
    public void executeOnDiskIO(Runnable runnable) {
        this.mDelegate.executeOnDiskIO(runnable);
    }

    @Override
    public boolean isMainThread() {
        return this.mDelegate.isMainThread();
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        this.mDelegate.postToMainThread(runnable);
    }

    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        TaskExecutor taskExecutor2 = taskExecutor;
        if (taskExecutor == null) {
            taskExecutor2 = this.mDefaultTaskExecutor;
        }
        this.mDelegate = taskExecutor2;
    }

}
