/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

private static class CrashlyticsController.AnySessionPartFileFilter
implements FilenameFilter {
    private CrashlyticsController.AnySessionPartFileFilter() {
    }

    @Override
    public boolean accept(File file, String string) {
        if (!CrashlyticsController.SESSION_FILE_FILTER.accept(file, string) && SESSION_FILE_PATTERN.matcher(string).matches()) {
            return true;
        }
        return false;
    }
}
