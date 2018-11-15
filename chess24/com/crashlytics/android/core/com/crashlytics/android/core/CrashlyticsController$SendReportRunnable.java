/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.core;

import android.content.Context;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.ReportUploader;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;

private static final class CrashlyticsController.SendReportRunnable
implements Runnable {
    private final Context context;
    private final Report report;
    private final ReportUploader reportUploader;

    public CrashlyticsController.SendReportRunnable(Context context, Report report, ReportUploader reportUploader) {
        this.context = context;
        this.report = report;
        this.reportUploader = reportUploader;
    }

    @Override
    public void run() {
        if (!CommonUtils.canTryConnection(this.context)) {
            return;
        }
        Fabric.getLogger().d("CrashlyticsCore", "Attempting to send crash report at time of crash...");
        this.reportUploader.forceUpload(this.report);
    }
}
