/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.VideoUploader;

class VideoUploader.UploadWorkItemBase
implements Runnable {
    VideoUploader.UploadWorkItemBase() {
    }

    @Override
    public void run() {
        UploadWorkItemBase.this.enqueueRetry(UploadWorkItemBase.this.completedRetries + 1);
    }
}
