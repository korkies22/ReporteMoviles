/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.internal;

import android.content.Context;
import com.facebook.internal.ImageDownloader;

private static class ImageDownloader.CacheReadWorkItem
implements Runnable {
    private boolean allowCachedRedirects;
    private Context context;
    private ImageDownloader.RequestKey key;

    ImageDownloader.CacheReadWorkItem(Context context, ImageDownloader.RequestKey requestKey, boolean bl) {
        this.context = context;
        this.key = requestKey;
        this.allowCachedRedirects = bl;
    }

    @Override
    public void run() {
        ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
    }
}
