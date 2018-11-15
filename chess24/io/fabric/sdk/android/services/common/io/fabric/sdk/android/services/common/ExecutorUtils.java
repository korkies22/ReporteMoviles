/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.RetryThreadPoolExecutor;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils {
    private static final long DEFAULT_TERMINATION_TIMEOUT = 2L;

    private ExecutorUtils() {
    }

    private static final void addDelayedShutdownHook(String string, ExecutorService executorService) {
        ExecutorUtils.addDelayedShutdownHook(string, executorService, 2L, TimeUnit.SECONDS);
    }

    public static final void addDelayedShutdownHook(final String string, ExecutorService object, long l, TimeUnit object2) {
        Runtime runtime = Runtime.getRuntime();
        object = new BackgroundPriorityRunnable((ExecutorService)object, l, (TimeUnit)((Object)object2)){
            final /* synthetic */ ExecutorService val$service;
            final /* synthetic */ long val$terminationTimeout;
            final /* synthetic */ TimeUnit val$timeUnit;
            {
                this.val$service = executorService;
                this.val$terminationTimeout = l;
                this.val$timeUnit = timeUnit;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRun() {
                try {
                    Logger logger = Fabric.getLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Executing shutdown hook for ");
                    stringBuilder.append(string);
                    logger.d("Fabric", stringBuilder.toString());
                    this.val$service.shutdown();
                    if (this.val$service.awaitTermination(this.val$terminationTimeout, this.val$timeUnit)) return;
                    logger = Fabric.getLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string);
                    stringBuilder.append(" did not shut down in the allocated time. Requesting immediate shutdown.");
                    logger.d("Fabric", stringBuilder.toString());
                    this.val$service.shutdownNow();
                    return;
                }
                catch (InterruptedException interruptedException) {}
                Fabric.getLogger().d("Fabric", String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", string));
                this.val$service.shutdownNow();
            }
        };
        object2 = new StringBuilder();
        object2.append("Crashlytics Shutdown Hook for ");
        object2.append(string);
        runtime.addShutdownHook(new Thread((Runnable)object, object2.toString()));
    }

    public static RetryThreadPoolExecutor buildRetryThreadPoolExecutor(String string, int n, RetryPolicy object, Backoff backoff) {
        object = new RetryThreadPoolExecutor(n, ExecutorUtils.getNamedThreadFactory(string), (RetryPolicy)object, backoff);
        ExecutorUtils.addDelayedShutdownHook(string, (ExecutorService)object);
        return object;
    }

    public static ExecutorService buildSingleThreadExecutorService(String string) {
        ExecutorService executorService = Executors.newSingleThreadExecutor(ExecutorUtils.getNamedThreadFactory(string));
        ExecutorUtils.addDelayedShutdownHook(string, executorService);
        return executorService;
    }

    public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String string) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(ExecutorUtils.getNamedThreadFactory(string));
        ExecutorUtils.addDelayedShutdownHook(string, scheduledExecutorService);
        return scheduledExecutorService;
    }

    public static final ThreadFactory getNamedThreadFactory(final String string) {
        return new ThreadFactory(new AtomicLong(1L)){
            final /* synthetic */ AtomicLong val$count;
            {
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
                stringBuilder.append(string);
                stringBuilder.append(this.val$count.getAndIncrement());
                runnable.setName(stringBuilder.toString());
                return runnable;
            }

        };
    }

}
