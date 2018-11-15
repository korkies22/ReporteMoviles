/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookException;
import com.facebook.share.internal.VideoUploader;

class VideoUploader.UploadWorkItemBase
implements Runnable {
    final /* synthetic */ FacebookException val$error;
    final /* synthetic */ String val$videoId;

    VideoUploader.UploadWorkItemBase(FacebookException facebookException, String string) {
        this.val$error = facebookException;
        this.val$videoId = string;
    }

    @Override
    public void run() {
        VideoUploader.issueResponse(UploadWorkItemBase.this.uploadContext, this.val$error, this.val$videoId);
    }
}
