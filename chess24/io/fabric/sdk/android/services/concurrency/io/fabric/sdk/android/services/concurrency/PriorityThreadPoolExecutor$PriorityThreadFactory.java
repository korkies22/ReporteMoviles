/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

protected static final class PriorityThreadPoolExecutor.PriorityThreadFactory
implements ThreadFactory {
    private final int threadPriority;

    public PriorityThreadPoolExecutor.PriorityThreadFactory(int n) {
        this.threadPriority = n;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        runnable = new Thread(runnable);
        runnable.setPriority(this.threadPriority);
        runnable.setName("Queue");
        return runnable;
    }
}
