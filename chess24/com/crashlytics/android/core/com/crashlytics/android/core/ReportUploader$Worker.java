/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.ReportUploader;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.util.Iterator;
import java.util.List;

private class ReportUploader.Worker
extends BackgroundPriorityRunnable {
    private final float delay;
    private final ReportUploader.SendCheck sendCheck;

    ReportUploader.Worker(float f, ReportUploader.SendCheck sendCheck) {
        this.delay = f;
        this.sendCheck = sendCheck;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void attemptUploadWithRetry() {
        block11 : {
            List<Report> list;
            block12 : {
                list = Fabric.getLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Starting report processing in ");
                stringBuilder.append(this.delay);
                stringBuilder.append(" second(s)...");
                list.d("CrashlyticsCore", stringBuilder.toString());
                if (this.delay > 0.0f) {
                    Thread.sleep((long)(this.delay * 1000.0f));
                }
                list = ReportUploader.this.findReports();
                if (ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                    return;
                }
                if (!list.isEmpty() && !this.sendCheck.canSendReports()) {
                    Logger logger = Fabric.getLogger();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("User declined to send. Removing ");
                    stringBuilder2.append(list.size());
                    stringBuilder2.append(" Report(s).");
                    logger.d("CrashlyticsCore", stringBuilder2.toString());
                    list = list.iterator();
                    while (list.hasNext()) {
                        ((Report)list.next()).remove();
                    }
                    return;
                }
                break block12;
                catch (InterruptedException interruptedException) {}
                Thread.currentThread().interrupt();
                return;
            }
            int n = 0;
            while (!list.isEmpty()) {
                List<Report> list2;
                if (ReportUploader.this.handlingExceptionCheck.isHandlingException()) {
                    return;
                }
                Logger logger = Fabric.getLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Attempting to send ");
                stringBuilder.append(list.size());
                stringBuilder.append(" report(s)");
                logger.d("CrashlyticsCore", stringBuilder.toString());
                for (Report report : list) {
                    ReportUploader.this.forceUpload(report);
                }
                list = list2 = ReportUploader.this.findReports();
                if (list2.isEmpty()) continue;
                long l = RETRY_INTERVALS[Math.min(n, RETRY_INTERVALS.length - 1)];
                list = Fabric.getLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("Report submisson: scheduling delayed retry in ");
                stringBuilder.append(l);
                stringBuilder.append(" seconds");
                list.d("CrashlyticsCore", stringBuilder.toString());
                try {
                    Thread.sleep(l * 1000L);
                    ++n;
                    list = list2;
                    continue;
                }
                catch (InterruptedException interruptedException) {}
                break block11;
            }
            return;
        }
        Thread.currentThread().interrupt();
    }

    @Override
    public void onRun() {
        try {
            this.attemptUploadWithRetry();
        }
        catch (Exception exception) {
            Fabric.getLogger().e("CrashlyticsCore", "An unexpected error occurred while attempting to upload crash reports.", exception);
        }
        ReportUploader.this.uploadThread = null;
    }
}
