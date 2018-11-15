/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.util.concurrent.Callable;

class CrashlyticsController
implements Callable<Void> {
    CrashlyticsController() {
    }

    @Override
    public Void call() throws Exception {
        CrashlyticsController.this.doOpenSession();
        return null;
    }
}
