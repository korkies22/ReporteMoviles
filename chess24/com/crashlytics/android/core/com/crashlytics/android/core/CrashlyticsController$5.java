/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.util.Comparator;

static final class CrashlyticsController
implements Comparator<File> {
    CrashlyticsController() {
    }

    @Override
    public int compare(File file, File file2) {
        return file.getName().compareTo(file2.getName());
    }
}
