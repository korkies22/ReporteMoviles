/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.File;

private static final class FileLruCache.ModifiedFile
implements Comparable<FileLruCache.ModifiedFile> {
    private static final int HASH_MULTIPLIER = 37;
    private static final int HASH_SEED = 29;
    private final File file;
    private final long modified;

    FileLruCache.ModifiedFile(File file) {
        this.file = file;
        this.modified = file.lastModified();
    }

    @Override
    public int compareTo(FileLruCache.ModifiedFile modifiedFile) {
        if (this.getModified() < modifiedFile.getModified()) {
            return -1;
        }
        if (this.getModified() > modifiedFile.getModified()) {
            return 1;
        }
        return this.getFile().compareTo(modifiedFile.getFile());
    }

    public boolean equals(Object object) {
        if (object instanceof FileLruCache.ModifiedFile && this.compareTo((FileLruCache.ModifiedFile)object) == 0) {
            return true;
        }
        return false;
    }

    File getFile() {
        return this.file;
    }

    long getModified() {
        return this.modified;
    }

    public int hashCode() {
        return (1073 + this.file.hashCode()) * 37 + (int)(this.modified % Integer.MAX_VALUE);
    }
}
