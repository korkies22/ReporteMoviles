/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ReportUploader;
import java.io.File;

static interface ReportUploader.ReportFilesProvider {
    public File[] getCompleteSessionFiles();

    public File[] getInvalidSessionFiles();

    public File[] getNativeReportFiles();
}
