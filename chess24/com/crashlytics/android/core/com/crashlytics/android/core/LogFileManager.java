/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.core;

import android.content.Context;
import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.FileLogStore;
import com.crashlytics.android.core.QueueFileLogStore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.util.Set;

class LogFileManager {
    private static final String COLLECT_CUSTOM_LOGS = "com.crashlytics.CollectCustomLogs";
    private static final String LOGFILE_EXT = ".temp";
    private static final String LOGFILE_PREFIX = "crashlytics-userlog-";
    static final int MAX_LOG_SIZE = 65536;
    private static final NoopLogStore NOOP_LOG_STORE = new NoopLogStore();
    private final Context context;
    private FileLogStore currentLog;
    private final DirectoryProvider directoryProvider;

    LogFileManager(Context context, DirectoryProvider directoryProvider) {
        this(context, directoryProvider, null);
    }

    LogFileManager(Context context, DirectoryProvider directoryProvider, String string) {
        this.context = context;
        this.directoryProvider = directoryProvider;
        this.currentLog = NOOP_LOG_STORE;
        this.setCurrentSession(string);
    }

    private String getSessionIdForFile(File object) {
        int n = (object = object.getName()).lastIndexOf(LOGFILE_EXT);
        if (n == -1) {
            return object;
        }
        return object.substring(LOGFILE_PREFIX.length(), n);
    }

    private File getWorkingFileForSession(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LOGFILE_PREFIX);
        stringBuilder.append(string);
        stringBuilder.append(LOGFILE_EXT);
        string = stringBuilder.toString();
        return new File(this.directoryProvider.getLogFileDir(), string);
    }

    void clearLog() {
        this.currentLog.deleteLogFile();
    }

    void discardOldLogFiles(Set<String> set) {
        File[] arrfile = this.directoryProvider.getLogFileDir().listFiles();
        if (arrfile != null) {
            for (File file : arrfile) {
                if (set.contains(this.getSessionIdForFile(file))) continue;
                file.delete();
            }
        }
    }

    ByteString getByteStringForLog() {
        return this.currentLog.getLogAsByteString();
    }

    byte[] getBytesForLog() {
        return this.currentLog.getLogAsBytes();
    }

    final void setCurrentSession(String string) {
        this.currentLog.closeLogFile();
        this.currentLog = NOOP_LOG_STORE;
        if (string == null) {
            return;
        }
        if (!CommonUtils.getBooleanResourceValue(this.context, COLLECT_CUSTOM_LOGS, true)) {
            Fabric.getLogger().d("CrashlyticsCore", "Preferences requested no custom logs. Aborting log file creation.");
            return;
        }
        this.setLogFile(this.getWorkingFileForSession(string), 65536);
    }

    void setLogFile(File file, int n) {
        this.currentLog = new QueueFileLogStore(file, n);
    }

    void writeToLog(long l, String string) {
        this.currentLog.writeToLog(l, string);
    }

    public static interface DirectoryProvider {
        public File getLogFileDir();
    }

    private static final class NoopLogStore
    implements FileLogStore {
        private NoopLogStore() {
        }

        @Override
        public void closeLogFile() {
        }

        @Override
        public void deleteLogFile() {
        }

        @Override
        public ByteString getLogAsByteString() {
            return null;
        }

        @Override
        public byte[] getLogAsBytes() {
            return null;
        }

        @Override
        public void writeToLog(long l, String string) {
        }
    }

}
