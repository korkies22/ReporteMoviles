/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

static final class ExecutorUtils
implements ThreadFactory {
    final /* synthetic */ AtomicLong val$count;
    final /* synthetic */ String val$threadNameTemplate;

    ExecutorUtils(String string, AtomicLong atomicLong) {
        this.val$threadNameTemplate = string;
        this.val$count = atomicLong;
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        runnable = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable(){

            @Override
            public void onRun() {
                runnable.run();
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.val$threadNameTemplate);
        stringBuilder.append(this.val$count.getAndIncrement());
        runnable.setName(stringBuilder.toString());
        return runnable;
    }

}
