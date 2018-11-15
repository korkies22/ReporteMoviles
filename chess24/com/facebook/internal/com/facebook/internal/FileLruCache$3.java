/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

class FileLruCache
implements Runnable {
    FileLruCache() {
    }

    @Override
    public void run() {
        FileLruCache.this.trim();
    }
}
