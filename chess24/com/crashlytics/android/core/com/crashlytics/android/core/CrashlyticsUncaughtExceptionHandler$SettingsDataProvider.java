/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;
import io.fabric.sdk.android.services.settings.SettingsData;

static interface CrashlyticsUncaughtExceptionHandler.SettingsDataProvider {
    public SettingsData getSettingsData();
}
