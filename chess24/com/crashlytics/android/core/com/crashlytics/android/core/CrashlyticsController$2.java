/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;

static final class CrashlyticsController
implements FilenameFilter {
    CrashlyticsController() {
    }

    @Override
    public boolean accept(File file, String string) {
        if (string.length() == 35 + ".cls".length() && string.endsWith(".cls")) {
            return true;
        }
        return false;
    }
}
