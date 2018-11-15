/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.util.Date;
import java.util.concurrent.Callable;

class CrashlyticsController
implements Callable<Void> {
    final /* synthetic */ Throwable val$ex;
    final /* synthetic */ boolean val$firebaseCrashlyticsClientFlag;
    final /* synthetic */ CrashlyticsUncaughtExceptionHandler.SettingsDataProvider val$settingsDataProvider;
    final /* synthetic */ Thread val$thread;
    final /* synthetic */ Date val$time;

    CrashlyticsController(Date date, Thread thread, Throwable throwable, CrashlyticsUncaughtExceptionHandler.SettingsDataProvider settingsDataProvider, boolean bl) {
        this.val$time = date;
        this.val$thread = thread;
        this.val$ex = throwable;
        this.val$settingsDataProvider = settingsDataProvider;
        this.val$firebaseCrashlyticsClientFlag = bl;
    }

    @Override
    public Void call() throws Exception {
        SessionSettingsData sessionSettingsData;
        Object object;
        CrashlyticsController.this.crashlyticsCore.createCrashMarker();
        CrashlyticsController.this.writeFatal(this.val$time, this.val$thread, this.val$ex);
        SettingsData settingsData = this.val$settingsDataProvider.getSettingsData();
        if (settingsData != null) {
            sessionSettingsData = settingsData.sessionData;
            object = settingsData.featuresData;
        } else {
            sessionSettingsData = null;
            object = sessionSettingsData;
        }
        boolean bl = object == null || object.firebaseCrashlyticsEnabled;
        if (bl || this.val$firebaseCrashlyticsClientFlag) {
            CrashlyticsController.this.recordFatalFirebaseEvent(this.val$time.getTime());
        }
        CrashlyticsController.this.doCloseSessions(sessionSettingsData);
        CrashlyticsController.this.doOpenSession();
        if (sessionSettingsData != null) {
            CrashlyticsController.this.trimSessionFiles(sessionSettingsData.maxCompleteSessionsCount);
        }
        if (!CrashlyticsController.this.shouldPromptUserBeforeSendingCrashReports(settingsData)) {
            CrashlyticsController.this.sendSessionReports(settingsData);
        }
        return null;
    }
}
