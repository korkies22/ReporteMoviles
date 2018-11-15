/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import java.io.File;
import java.io.FilenameFilter;

class CrashlyticsController
implements Runnable {
    CrashlyticsController() {
    }

    @Override
    public void run() {
        CrashlyticsController.this.doCleanInvalidTempFiles(CrashlyticsController.this.listFilesMatching(new CrashlyticsController.InvalidPartFileFilter()));
    }
}
