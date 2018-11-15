/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;

class CrashlyticsController
implements CrashlyticsUncaughtExceptionHandler.CrashListener {
    CrashlyticsController() {
    }

    @Override
    public void onUncaughtException(CrashlyticsUncaughtExceptionHandler.SettingsDataProvider settingsDataProvider, Thread thread, Throwable throwable, boolean bl) {
        CrashlyticsController.this.handleUncaughtException(settingsDataProvider, thread, throwable, bl);
    }
}
