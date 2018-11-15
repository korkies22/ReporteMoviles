/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;

private static final class CrashlyticsController.DefaultSettingsDataProvider
implements CrashlyticsUncaughtExceptionHandler.SettingsDataProvider {
    private CrashlyticsController.DefaultSettingsDataProvider() {
    }

    @Override
    public SettingsData getSettingsData() {
        return Settings.getInstance().awaitSettingsData();
    }
}
