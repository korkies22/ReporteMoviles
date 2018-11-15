/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.internal;

import android.content.Context;
import com.facebook.internal.ImageDownloader;

private static class ImageDownloader.DownloadImageWorkItem
implements Runnable {
    private Context context;
    private ImageDownloader.RequestKey key;

    ImageDownloader.DownloadImageWorkItem(Context context, ImageDownloader.RequestKey requestKey) {
        this.context = context;
        this.key = requestKey;
    }

    @Override
    public void run() {
        ImageDownloader.download(this.key, this.context);
    }
}
