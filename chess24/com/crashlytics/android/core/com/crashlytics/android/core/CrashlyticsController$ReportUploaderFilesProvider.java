/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.ReportUploader;
import java.io.File;

private final class CrashlyticsController.ReportUploaderFilesProvider
implements ReportUploader.ReportFilesProvider {
    private CrashlyticsController.ReportUploaderFilesProvider() {
    }

    @Override
    public File[] getCompleteSessionFiles() {
        return CrashlyticsController.this.listCompleteSessionFiles();
    }

    @Override
    public File[] getInvalidSessionFiles() {
        return CrashlyticsController.this.getInvalidFilesDir().listFiles();
    }

    @Override
    public File[] getNativeReportFiles() {
        return CrashlyticsController.this.listNativeSessionFileDirectories();
    }
}
