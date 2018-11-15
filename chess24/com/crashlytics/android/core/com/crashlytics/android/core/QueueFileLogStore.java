/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.FileLogStore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

class QueueFileLogStore
implements FileLogStore {
    private QueueFile logFile;
    private final int maxLogSize;
    private final File workingFile;

    public QueueFileLogStore(File file, int n) {
        this.workingFile = file;
        this.maxLogSize = n;
    }

    private void doWriteToLog(long l, String charSequence) {
        if (this.logFile == null) {
            return;
        }
        String string = charSequence;
        if (charSequence == null) {
            string = "null";
        }
        int n = this.maxLogSize / 4;
        charSequence = string;
        try {
            if (string.length() > n) {
                charSequence = new StringBuilder();
                charSequence.append("...");
                charSequence.append(string.substring(string.length() - n));
                charSequence = charSequence.toString();
            }
            charSequence = charSequence.replaceAll("\r", " ").replaceAll("\n", " ");
            charSequence = String.format(Locale.US, "%d %s%n", l, charSequence).getBytes("UTF-8");
            this.logFile.add((byte[])charSequence);
            while (!this.logFile.isEmpty() && this.logFile.usedBytes() > this.maxLogSize) {
                this.logFile.remove();
            }
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("CrashlyticsCore", "There was a problem writing to the Crashlytics log.", iOException);
        }
    }

    private LogBytes getLogBytes() {
        if (!this.workingFile.exists()) {
            return null;
        }
        this.openLogFile();
        if (this.logFile == null) {
            return null;
        }
        final int[] arrn = new int[]{0};
        final byte[] arrby = new byte[this.logFile.usedBytes()];
        try {
            this.logFile.forEach(new QueueFile.ElementReader(){

                @Override
                public void read(InputStream inputStream, int n) throws IOException {
                    int[] arrn2;
                    try {
                        inputStream.read(arrby, arrn[0], n);
                        arrn2 = arrn;
                    }
                    catch (Throwable throwable) {
                        inputStream.close();
                        throw throwable;
                    }
                    arrn2[0] = arrn2[0] + n;
                    inputStream.close();
                }
            });
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("CrashlyticsCore", "A problem occurred while reading the Crashlytics log file.", iOException);
        }
        return new LogBytes(arrby, arrn[0]);
    }

    private void openLogFile() {
        if (this.logFile == null) {
            try {
                this.logFile = new QueueFile(this.workingFile);
                return;
            }
            catch (IOException iOException) {
                Logger logger = Fabric.getLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not open log file: ");
                stringBuilder.append(this.workingFile);
                logger.e("CrashlyticsCore", stringBuilder.toString(), iOException);
            }
        }
    }

    @Override
    public void closeLogFile() {
        CommonUtils.closeOrLog(this.logFile, "There was a problem closing the Crashlytics log file.");
        this.logFile = null;
    }

    @Override
    public void deleteLogFile() {
        this.closeLogFile();
        this.workingFile.delete();
    }

    @Override
    public ByteString getLogAsByteString() {
        LogBytes logBytes = this.getLogBytes();
        if (logBytes == null) {
            return null;
        }
        return ByteString.copyFrom(logBytes.bytes, 0, logBytes.offset);
    }

    @Override
    public byte[] getLogAsBytes() {
        LogBytes logBytes = this.getLogBytes();
        if (logBytes == null) {
            return null;
        }
        return logBytes.bytes;
    }

    @Override
    public void writeToLog(long l, String string) {
        this.openLogFile();
        this.doWriteToLog(l, string);
    }

    public class LogBytes {
        public final byte[] bytes;
        public final int offset;

        public LogBytes(byte[] arrby, int n) {
            this.bytes = arrby;
            this.offset = n;
        }
    }

}
