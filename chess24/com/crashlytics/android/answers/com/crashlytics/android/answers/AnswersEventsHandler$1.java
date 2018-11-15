/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;

class AnswersEventsHandler
implements Runnable {
    final /* synthetic */ AnalyticsSettingsData val$analyticsSettingsData;
    final /* synthetic */ String val$protocolAndHostOverride;

    AnswersEventsHandler(AnalyticsSettingsData analyticsSettingsData, String string) {
        this.val$analyticsSettingsData = analyticsSettingsData;
        this.val$protocolAndHostOverride = string;
    }

    @Override
    public void run() {
        try {
            AnswersEventsHandler.this.strategy.setAnalyticsSettingsData(this.val$analyticsSettingsData, this.val$protocolAndHostOverride);
            return;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Answers", "Failed to set analytics settings data", exception);
            return;
        }
    }
}
