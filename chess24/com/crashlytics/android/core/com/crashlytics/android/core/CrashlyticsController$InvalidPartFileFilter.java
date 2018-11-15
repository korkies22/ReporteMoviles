/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ClsFileOutputStream;
import com.crashlytics.android.core.CrashlyticsController;
import java.io.File;
import java.io.FilenameFilter;

static class CrashlyticsController.InvalidPartFileFilter
implements FilenameFilter {
    CrashlyticsController.InvalidPartFileFilter() {
    }

    @Override
    public boolean accept(File file, String string) {
        if (!ClsFileOutputStream.TEMP_FILENAME_FILTER.accept(file, string) && !string.contains(CrashlyticsController.SESSION_EVENT_MISSING_BINARY_IMGS_TAG)) {
            return false;
        }
        return true;
    }
}
