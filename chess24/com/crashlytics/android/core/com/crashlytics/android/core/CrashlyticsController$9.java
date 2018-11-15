/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.util.Date;

class CrashlyticsController
implements Runnable {
    final /* synthetic */ Throwable val$ex;
    final /* synthetic */ Date val$now;
    final /* synthetic */ Thread val$thread;

    CrashlyticsController(Date date, Thread thread, Throwable throwable) {
        this.val$now = date;
        this.val$thread = thread;
        this.val$ex = throwable;
    }

    @Override
    public void run() {
        if (!CrashlyticsController.this.isHandlingException()) {
            CrashlyticsController.this.doWriteNonFatal(this.val$now, this.val$thread, this.val$ex);
        }
    }
}
