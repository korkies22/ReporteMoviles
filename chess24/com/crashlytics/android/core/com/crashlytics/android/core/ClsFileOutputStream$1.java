/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;

static final class ClsFileOutputStream
implements FilenameFilter {
    ClsFileOutputStream() {
    }

    @Override
    public boolean accept(File file, String string) {
        return string.endsWith(com.crashlytics.android.core.ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
    }
}
