/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.util.concurrent.atomic.AtomicBoolean;

class CrashlyticsUncaughtExceptionHandler
implements Thread.UncaughtExceptionHandler {
    private final CrashListener crashListener;
    private final Thread.UncaughtExceptionHandler defaultHandler;
    private final boolean firebaseCrashlyticsClientFlag;
    private final AtomicBoolean isHandlingException;
    private final SettingsDataProvider settingsDataProvider;

    public CrashlyticsUncaughtExceptionHandler(CrashListener crashListener, SettingsDataProvider settingsDataProvider, boolean bl, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.crashListener = crashListener;
        this.settingsDataProvider = settingsDataProvider;
        this.firebaseCrashlyticsClientFlag = bl;
        this.defaultHandler = uncaughtExceptionHandler;
        this.isHandlingException = new AtomicBoolean(false);
    }

    boolean isHandlingException() {
        return this.isHandlingException.get();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void uncaughtException(Thread var1_1, Throwable var2_2) {
        this.isHandlingException.set(true);
        this.crashListener.onUncaughtException(this.settingsDataProvider, var1_1, var2_2, this.firebaseCrashlyticsClientFlag);
        Fabric.getLogger().d("CrashlyticsCore", "Crashlytics completed exception processing. Invoking default exception handler.");
        this.defaultHandler.uncaughtException(var1_1, var2_2);
        this.isHandlingException.set(false);
        return;
        {
            catch (Exception var3_4) {}
            {
                Fabric.getLogger().e("CrashlyticsCore", "An error occurred in the uncaught exception handler", var3_4);
            }
        }
        ** finally { 
lbl11: // 1 sources:
        Fabric.getLogger().d("CrashlyticsCore", "Crashlytics completed exception processing. Invoking default exception handler.");
        this.defaultHandler.uncaughtException(var1_1, var2_2);
        this.isHandlingException.set(false);
        throw var3_3;
    }

    static interface CrashListener {
        public void onUncaughtException(SettingsDataProvider var1, Thread var2, Throwable var3, boolean var4);
    }

    static interface SettingsDataProvider {
        public SettingsData getSettingsData();
    }

}
