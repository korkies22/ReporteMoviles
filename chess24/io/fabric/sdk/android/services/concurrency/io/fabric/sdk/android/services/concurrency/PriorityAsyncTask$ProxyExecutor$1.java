/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import io.fabric.sdk.android.services.concurrency.PriorityFutureTask;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.Task;

class PriorityAsyncTask.ProxyExecutor
extends PriorityFutureTask<Result> {
    PriorityAsyncTask.ProxyExecutor(Runnable runnable, Object object) {
        super(runnable, object);
    }

    @Override
    public <T extends Dependency<Task> & PriorityProvider> T getDelegate() {
        return (T)ProxyExecutor.this.task;
    }
}
