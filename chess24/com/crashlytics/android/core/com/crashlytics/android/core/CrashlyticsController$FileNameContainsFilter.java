/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import java.io.File;
import java.io.FilenameFilter;

static class CrashlyticsController.FileNameContainsFilter
implements FilenameFilter {
    private final String string;

    public CrashlyticsController.FileNameContainsFilter(String string) {
        this.string = string;
    }

    @Override
    public boolean accept(File file, String string) {
        if (string.contains(this.string) && !string.endsWith(".cls_temp")) {
            return true;
        }
        return false;
    }
}
