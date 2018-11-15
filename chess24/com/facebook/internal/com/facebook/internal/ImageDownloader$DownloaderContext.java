/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.WorkQueue;

private static class ImageDownloader.DownloaderContext {
    boolean isCancelled;
    ImageRequest request;
    WorkQueue.WorkItem workItem;

    private ImageDownloader.DownloaderContext() {
    }
}
