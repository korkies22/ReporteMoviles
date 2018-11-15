/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

class FileLruCache
implements FileLruCache.StreamCloseCallback {
    final /* synthetic */ File val$buffer;
    final /* synthetic */ long val$bufferFileCreateTime;
    final /* synthetic */ String val$key;

    FileLruCache(long l, File file, String string) {
        this.val$bufferFileCreateTime = l;
        this.val$buffer = file;
        this.val$key = string;
    }

    @Override
    public void onClose() {
        if (this.val$bufferFileCreateTime < FileLruCache.this.lastClearCacheTime.get()) {
            this.val$buffer.delete();
            return;
        }
        FileLruCache.this.renameToTargetAndTrim(this.val$key, this.val$buffer);
    }
}
