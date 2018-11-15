/*
 * Decompiled with CFR 0_134.
 */
package android.arch.core.executor;

import java.util.concurrent.Executor;

static final class ArchTaskExecutor
implements Executor {
    ArchTaskExecutor() {
    }

    @Override
    public void execute(Runnable runnable) {
        android.arch.core.executor.ArchTaskExecutor.getInstance().postToMainThread(runnable);
    }
}
