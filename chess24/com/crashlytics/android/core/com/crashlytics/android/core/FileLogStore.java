/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;

interface FileLogStore {
    public void closeLogFile();

    public void deleteLogFile();

    public ByteString getLogAsByteString();

    public byte[] getLogAsBytes();

    public void writeToLog(long var1, String var3);
}
