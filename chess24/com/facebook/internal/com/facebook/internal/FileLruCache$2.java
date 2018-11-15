/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import java.io.File;

class FileLruCache
implements Runnable {
    final /* synthetic */ File[] val$filesToDelete;

    FileLruCache(File[] arrfile) {
        this.val$filesToDelete = arrfile;
    }

    @Override
    public void run() {
        File[] arrfile = this.val$filesToDelete;
        int n = arrfile.length;
        for (int i = 0; i < n; ++i) {
            arrfile[i].delete();
        }
    }
}
