/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.LogFileManager;
import java.io.File;

public static interface LogFileManager.DirectoryProvider {
    public File getLogFileDir();
}
