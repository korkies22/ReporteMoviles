/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import io.fabric.sdk.android.services.concurrency.PriorityFutureTask;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.Task;
import java.util.concurrent.Executor;

private static class PriorityAsyncTask.ProxyExecutor<Result>
implements Executor {
    private final Executor executor;
    private final PriorityAsyncTask task;

    public PriorityAsyncTask.ProxyExecutor(Executor executor, PriorityAsyncTask priorityAsyncTask) {
        this.executor = executor;
        this.task = priorityAsyncTask;
    }

    @Override
    public void execute(Runnable runnable) {
        this.executor.execute(new PriorityFutureTask<Result>(runnable, null){

            @Override
            public <T extends Dependency<Task> & PriorityProvider> T getDelegate() {
                return (T)ProxyExecutor.this.task;
            }
        });
    }

}
