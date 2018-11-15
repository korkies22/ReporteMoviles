/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;

class ClsFileOutputStream
extends FileOutputStream {
    public static final String IN_PROGRESS_SESSION_FILE_EXTENSION = ".cls_temp";
    public static final String SESSION_FILE_EXTENSION = ".cls";
    public static final FilenameFilter TEMP_FILENAME_FILTER = new FilenameFilter(){

        @Override
        public boolean accept(File file, String string) {
            return string.endsWith(ClsFileOutputStream.IN_PROGRESS_SESSION_FILE_EXTENSION);
        }
    };
    private boolean closed;
    private File complete;
    private File inProgress;
    private final String root;

    public ClsFileOutputStream(File serializable, String string) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(IN_PROGRESS_SESSION_FILE_EXTENSION);
        super(new File((File)serializable, stringBuilder.toString()));
        this.closed = false;
        stringBuilder = new StringBuilder();
        stringBuilder.append(serializable);
        stringBuilder.append(File.separator);
        stringBuilder.append(string);
        this.root = stringBuilder.toString();
        serializable = new StringBuilder();
        serializable.append(this.root);
        serializable.append(IN_PROGRESS_SESSION_FILE_EXTENSION);
        this.inProgress = new File(serializable.toString());
    }

    public ClsFileOutputStream(String string, String string2) throws FileNotFoundException {
        this(new File(string), string2);
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            File file;
            CharSequence charSequence;
            block10 : {
                block9 : {
                    block8 : {
                        boolean bl = this.closed;
                        if (!bl) break block8;
                        return;
                    }
                    this.closed = true;
                    super.flush();
                    super.close();
                    charSequence = new StringBuilder();
                    charSequence.append(this.root);
                    charSequence.append(SESSION_FILE_EXTENSION);
                    file = new File(charSequence.toString());
                    if (this.inProgress.renameTo(file)) {
                        this.inProgress = null;
                        this.complete = file;
                        return;
                    }
                    charSequence = "";
                    if (!file.exists()) break block9;
                    charSequence = " (target already exists)";
                    break block10;
                }
                if (this.inProgress.exists()) break block10;
                charSequence = " (source does not exist)";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not rename temp file: ");
            stringBuilder.append(this.inProgress);
            stringBuilder.append(" -> ");
            stringBuilder.append(file);
            stringBuilder.append((String)charSequence);
            throw new IOException(stringBuilder.toString());
        }
    }

    public void closeInProgressStream() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        super.flush();
        super.close();
    }

    public File getCompleteFile() {
        return this.complete;
    }

    public File getInProgressFile() {
        return this.inProgress;
    }

}
