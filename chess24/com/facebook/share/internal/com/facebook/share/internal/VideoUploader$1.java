/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.internal.Utility;

static final class VideoUploader
extends AccessTokenTracker {
    VideoUploader() {
    }

    @Override
    protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
        if (accessToken == null) {
            return;
        }
        if (accessToken2 == null || !Utility.areObjectsEqual(accessToken2.getUserId(), accessToken.getUserId())) {
            com.facebook.share.internal.VideoUploader.cancelAllRequests();
        }
    }
}
