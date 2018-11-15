/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

static final class ExecutorUtils
extends BackgroundPriorityRunnable {
    final /* synthetic */ ExecutorService val$service;
    final /* synthetic */ String val$serviceName;
    final /* synthetic */ long val$terminationTimeout;
    final /* synthetic */ TimeUnit val$timeUnit;

    ExecutorUtils(String string, ExecutorService executorService, long l, TimeUnit timeUnit) {
        this.val$serviceName = string;
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
            stringBuilder.append(this.val$serviceName);
            logger.d("Fabric", stringBuilder.toString());
            this.val$service.shutdown();
            if (this.val$service.awaitTermination(this.val$terminationTimeout, this.val$timeUnit)) return;
            logger = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.val$serviceName);
            stringBuilder.append(" did not shut down in the allocated time. Requesting immediate shutdown.");
            logger.d("Fabric", stringBuilder.toString());
            this.val$service.shutdownNow();
            return;
        }
        catch (InterruptedException interruptedException) {}
        Fabric.getLogger().d("Fabric", String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", this.val$serviceName));
        this.val$service.shutdownNow();
    }
}
