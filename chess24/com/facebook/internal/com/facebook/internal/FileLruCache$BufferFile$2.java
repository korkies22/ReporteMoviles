/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.File;
import java.io.FilenameFilter;

static final class FileLruCache.BufferFile
implements FilenameFilter {
    FileLruCache.BufferFile() {
    }

    @Override
    public boolean accept(File file, String string) {
        return string.startsWith(FileLruCache.BufferFile.FILE_NAME_PREFIX);
    }
}
