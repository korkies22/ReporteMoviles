/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.Report;

class CreateReportRequest {
    public final String apiKey;
    public final Report report;

    public CreateReportRequest(String string, Report report) {
        this.apiKey = string;
        this.report = report;
    }
}
