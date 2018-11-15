/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.LogFileManager;
import java.util.concurrent.Callable;

class CrashlyticsController
implements Callable<Void> {
    final /* synthetic */ String val$msg;
    final /* synthetic */ long val$timestamp;

    CrashlyticsController(long l, String string) {
        this.val$timestamp = l;
        this.val$msg = string;
    }

    @Override
    public Void call() throws Exception {
        if (!CrashlyticsController.this.isHandlingException()) {
            CrashlyticsController.this.logFileManager.writeToLog(this.val$timestamp, this.val$msg);
        }
        return null;
    }
}
