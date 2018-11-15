/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.ReportUploader;

private final class CrashlyticsController.ReportUploaderHandlingExceptionCheck
implements ReportUploader.HandlingExceptionCheck {
    private CrashlyticsController.ReportUploaderHandlingExceptionCheck() {
    }

    @Override
    public boolean isHandlingException() {
        return CrashlyticsController.this.isHandlingException();
    }
}
