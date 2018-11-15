/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FileFilter;

static final class CrashlyticsController
implements FileFilter {
    CrashlyticsController() {
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory() && file.getName().length() == 35) {
            return true;
        }
        return false;
    }
}
