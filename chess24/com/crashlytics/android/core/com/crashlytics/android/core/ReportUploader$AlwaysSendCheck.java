/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ReportUploader;

static final class ReportUploader.AlwaysSendCheck
implements ReportUploader.SendCheck {
    ReportUploader.AlwaysSendCheck() {
    }

    @Override
    public boolean canSendReports() {
        return true;
    }
}
