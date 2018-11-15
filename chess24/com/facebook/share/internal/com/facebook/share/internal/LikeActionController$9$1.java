/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequestBatch;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.share.internal.LikeActionController;

class LikeActionController
implements GraphRequestBatch.Callback {
    final /* synthetic */ LikeActionController.GetEngagementRequestWrapper val$engagementRequest;
    final /* synthetic */ LikeActionController.LikeRequestWrapper val$likeRequestWrapper;

    LikeActionController(LikeActionController.LikeRequestWrapper likeRequestWrapper, LikeActionController.GetEngagementRequestWrapper getEngagementRequestWrapper) {
        this.val$likeRequestWrapper = likeRequestWrapper;
        this.val$engagementRequest = getEngagementRequestWrapper;
    }

    @Override
    public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
        if (this.val$likeRequestWrapper.getError() == null && this.val$engagementRequest.getError() == null) {
            9.this.this$0.updateState(this.val$likeRequestWrapper.isObjectLiked(), this.val$engagementRequest.likeCountStringWithLike, this.val$engagementRequest.likeCountStringWithoutLike, this.val$engagementRequest.socialSentenceStringWithLike, this.val$engagementRequest.socialSentenceStringWithoutLike, this.val$likeRequestWrapper.getUnlikeToken());
            return;
        }
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Unable to refresh like state for id: '%s'", 9.this.this$0.objectId);
    }
}
