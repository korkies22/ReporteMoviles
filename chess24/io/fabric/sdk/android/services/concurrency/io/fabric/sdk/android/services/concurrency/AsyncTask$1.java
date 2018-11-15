/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

static final class AsyncTask
implements ThreadFactory {
    private final AtomicInteger count = new AtomicInteger(1);

    AsyncTask() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AsyncTask #");
        stringBuilder.append(this.count.getAndIncrement());
        return new Thread(runnable, stringBuilder.toString());
    }
}
