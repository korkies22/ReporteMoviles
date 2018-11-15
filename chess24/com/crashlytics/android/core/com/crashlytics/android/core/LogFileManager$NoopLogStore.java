/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.FileLogStore;
import com.crashlytics.android.core.LogFileManager;

private static final class LogFileManager.NoopLogStore
implements FileLogStore {
    private LogFileManager.NoopLogStore() {
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
