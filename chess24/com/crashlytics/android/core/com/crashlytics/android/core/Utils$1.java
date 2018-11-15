/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;

static final class Utils
implements FilenameFilter {
    Utils() {
    }

    @Override
    public boolean accept(File file, String string) {
        return true;
    }
}
