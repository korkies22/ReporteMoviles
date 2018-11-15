/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.atomic.AtomicLong;

private static class FileLruCache.BufferFile {
    private static final String FILE_NAME_PREFIX = "buffer";
    private static final FilenameFilter filterExcludeBufferFiles = new FilenameFilter(){

        @Override
        public boolean accept(File file, String string) {
            return string.startsWith(FileLruCache.BufferFile.FILE_NAME_PREFIX) ^ true;
        }
    };
    private static final FilenameFilter filterExcludeNonBufferFiles = new FilenameFilter(){

        @Override
        public boolean accept(File file, String string) {
            return string.startsWith(FileLruCache.BufferFile.FILE_NAME_PREFIX);
        }
    };

    private FileLruCache.BufferFile() {
    }

    static void deleteAll(File arrfile) {
        if ((arrfile = arrfile.listFiles(FileLruCache.BufferFile.excludeNonBufferFiles())) != null) {
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                arrfile[i].delete();
            }
        }
    }

    static FilenameFilter excludeBufferFiles() {
        return filterExcludeBufferFiles;
    }

    static FilenameFilter excludeNonBufferFiles() {
        return filterExcludeNonBufferFiles;
    }

    static File newFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FILE_NAME_PREFIX);
        stringBuilder.append(Long.valueOf(bufferIndex.incrementAndGet()).toString());
        return new File(file, stringBuilder.toString());
    }

}
