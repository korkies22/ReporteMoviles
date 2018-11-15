/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import java.util.concurrent.Callable;

class CrashlyticsController
implements Callable<Boolean> {
    final /* synthetic */ SessionSettingsData val$sessionSettingsData;

    CrashlyticsController(SessionSettingsData sessionSettingsData) {
        this.val$sessionSettingsData = sessionSettingsData;
    }

    @Override
    public Boolean call() throws Exception {
        if (CrashlyticsController.this.isHandlingException()) {
            Fabric.getLogger().d("CrashlyticsCore", "Skipping session finalization because a crash has already occurred.");
            return Boolean.FALSE;
        }
        Fabric.getLogger().d("CrashlyticsCore", "Finalizing previously open sessions.");
        CrashlyticsController.this.doCloseSessions(this.val$sessionSettingsData, true);
        Fabric.getLogger().d("CrashlyticsCore", "Closed all previously open sessions");
        return Boolean.TRUE;
    }
}
