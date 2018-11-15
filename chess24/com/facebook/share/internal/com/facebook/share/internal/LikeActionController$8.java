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
import com.facebook.share.internal.LikeActionController;

class LikeActionController
implements GraphRequestBatch.Callback {
    final /* synthetic */ Bundle val$analyticsParameters;
    final /* synthetic */ LikeActionController.PublishUnlikeRequestWrapper val$unlikeRequest;

    LikeActionController(LikeActionController.PublishUnlikeRequestWrapper publishUnlikeRequestWrapper, Bundle bundle) {
        this.val$unlikeRequest = publishUnlikeRequestWrapper;
        this.val$analyticsParameters = bundle;
    }

    @Override
    public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
        LikeActionController.this.isPendingLikeOrUnlike = false;
        if (this.val$unlikeRequest.getError() != null) {
            LikeActionController.this.publishDidError(true);
            return;
        }
        LikeActionController.this.unlikeToken = null;
        LikeActionController.this.isObjectLikedOnServer = false;
        LikeActionController.this.getAppEventsLogger().logSdkEvent("fb_like_control_did_unlike", null, this.val$analyticsParameters);
        LikeActionController.this.publishAgainIfNeeded(this.val$analyticsParameters);
    }
}
