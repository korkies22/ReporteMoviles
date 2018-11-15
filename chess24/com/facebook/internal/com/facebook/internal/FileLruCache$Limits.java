/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.security.InvalidParameterException;

public static final class FileLruCache.Limits {
    private int byteCount = 1048576;
    private int fileCount = 1024;

    int getByteCount() {
        return this.byteCount;
    }

    int getFileCount() {
        return this.fileCount;
    }

    void setByteCount(int n) {
        if (n < 0) {
            throw new InvalidParameterException("Cache byte-count limit must be >= 0");
        }
        this.byteCount = n;
    }

    void setFileCount(int n) {
        if (n < 0) {
            throw new InvalidParameterException("Cache file count limit must be >= 0");
        }
        this.fileCount = n;
    }
}
