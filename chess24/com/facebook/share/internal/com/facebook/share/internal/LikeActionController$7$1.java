/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequestBatch;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;

class LikeActionController
implements GraphRequestBatch.Callback {
    final /* synthetic */ LikeActionController.PublishLikeRequestWrapper val$likeRequest;

    LikeActionController(LikeActionController.PublishLikeRequestWrapper publishLikeRequestWrapper) {
        this.val$likeRequest = publishLikeRequestWrapper;
    }

    @Override
    public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
        7.this.this$0.isPendingLikeOrUnlike = false;
        if (this.val$likeRequest.getError() != null) {
            7.this.this$0.publishDidError(false);
            return;
        }
        7.this.this$0.unlikeToken = Utility.coerceValueIfNullOrEmpty(this.val$likeRequest.unlikeToken, null);
        7.this.this$0.isObjectLikedOnServer = true;
        7.this.this$0.getAppEventsLogger().logSdkEvent("fb_like_control_did_like", null, 7.this.val$analyticsParameters);
        7.this.this$0.publishAgainIfNeeded(7.this.val$analyticsParameters);
    }
}
