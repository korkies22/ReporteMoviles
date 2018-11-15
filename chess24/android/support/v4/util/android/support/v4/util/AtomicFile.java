/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;

    public AtomicFile(@NonNull File file) {
        this.mBaseName = file;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(".bak");
        this.mBackupName = new File(stringBuilder.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean sync(@NonNull FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.getFD().sync();
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public void delete() {
        this.mBaseName.delete();
        this.mBackupName.delete();
    }

    public void failWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            AtomicFile.sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBaseName.delete();
                this.mBackupName.renameTo(this.mBaseName);
                return;
            }
            catch (IOException iOException) {
                Log.w((String)"AtomicFile", (String)"failWrite: Got exception:", (Throwable)iOException);
            }
        }
    }

    public void finishWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            AtomicFile.sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBackupName.delete();
                return;
            }
            catch (IOException iOException) {
                Log.w((String)"AtomicFile", (String)"finishWrite: Got exception:", (Throwable)iOException);
            }
        }
    }

    @NonNull
    public File getBaseFile() {
        return this.mBaseName;
    }

    @NonNull
    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mBackupName.exists()) {
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
        }
        return new FileInputStream(this.mBaseName);
    }

    @NonNull
    public byte[] readFully() throws IOException {
        byte[] arrby;
        int n;
        int n2;
        FileInputStream fileInputStream = this.openRead();
        try {
            arrby = new byte[fileInputStream.available()];
            n2 = 0;
        }
        catch (Throwable throwable) {
            fileInputStream.close();
            throw throwable;
        }
        do {
            n = fileInputStream.read(arrby, n2, arrby.length - n2);
            if (n > 0) break block6;
            break;
        } while (true);
        {
            block6 : {
                fileInputStream.close();
                return arrby;
            }
            n = n2 + n;
            int n3 = fileInputStream.available();
            n2 = n;
            if (n3 <= arrby.length - n) continue;
            byte[] arrby2 = new byte[n3 + n];
            System.arraycopy(arrby, 0, arrby2, 0, n);
            arrby = arrby2;
            n2 = n;
            continue;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    public FileOutputStream startWrite() throws IOException {
        if (this.mBaseName.exists()) {
            if (!this.mBackupName.exists()) {
                if (!this.mBaseName.renameTo(this.mBackupName)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't rename file ");
                    stringBuilder.append(this.mBaseName);
                    stringBuilder.append(" to backup file ");
                    stringBuilder.append(this.mBackupName);
                    Log.w((String)"AtomicFile", (String)stringBuilder.toString());
                }
            } else {
                this.mBaseName.delete();
            }
        }
        try {
            return new FileOutputStream(this.mBaseName);
        }
        catch (FileNotFoundException fileNotFoundException) {}
        if (!this.mBaseName.getParentFile().mkdirs()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't create directory ");
            stringBuilder.append(this.mBaseName);
            throw new IOException(stringBuilder.toString());
        }
        try {
            return new FileOutputStream(this.mBaseName);
        }
        catch (FileNotFoundException fileNotFoundException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't create ");
        stringBuilder.append(this.mBaseName);
        throw new IOException(stringBuilder.toString());
    }
}
