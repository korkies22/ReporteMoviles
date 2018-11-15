/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;

static interface CrashlyticsUncaughtExceptionHandler.CrashListener {
    public void onUncaughtException(CrashlyticsUncaughtExceptionHandler.SettingsDataProvider var1, Thread var2, Throwable var3, boolean var4);
}
