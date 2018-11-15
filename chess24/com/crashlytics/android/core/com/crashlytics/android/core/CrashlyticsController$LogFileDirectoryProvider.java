/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.LogFileManager;
import io.fabric.sdk.android.services.persistence.FileStore;
import java.io.File;

private static final class CrashlyticsController.LogFileDirectoryProvider
implements LogFileManager.DirectoryProvider {
    private static final String LOG_FILES_DIR = "log-files";
    private final FileStore rootFileStore;

    public CrashlyticsController.LogFileDirectoryProvider(FileStore fileStore) {
        this.rootFileStore = fileStore;
    }

    @Override
    public File getLogFileDir() {
        File file = new File(this.rootFileStore.getFilesDir(), LOG_FILES_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
