/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphRequestBatch;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;

class LikeActionController
implements LikeActionController.RequestCompletionCallback {
    LikeActionController() {
    }

    @Override
    public void onComplete() {
        LikeActionController.AbstractRequestWrapper abstractRequestWrapper = LikeActionController.$SwitchMap$com$facebook$share$widget$LikeView$ObjectType[LikeActionController.this.objectType.ordinal()] != 1 ? new LikeActionController.GetOGObjectLikesRequestWrapper(LikeActionController.this, LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType) : new LikeActionController.GetPageLikesRequestWrapper(LikeActionController.this, LikeActionController.this.verifiedObjectId);
        LikeActionController.GetEngagementRequestWrapper getEngagementRequestWrapper = new LikeActionController.GetEngagementRequestWrapper(LikeActionController.this, LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
        abstractRequestWrapper.addToBatch(graphRequestBatch);
        getEngagementRequestWrapper.addToBatch(graphRequestBatch);
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback((LikeActionController.LikeRequestWrapper)((Object)abstractRequestWrapper), getEngagementRequestWrapper){
            final /* synthetic */ LikeActionController.GetEngagementRequestWrapper val$engagementRequest;
            final /* synthetic */ LikeActionController.LikeRequestWrapper val$likeRequestWrapper;
            {
                this.val$likeRequestWrapper = likeRequestWrapper;
                this.val$engagementRequest = getEngagementRequestWrapper;
            }

            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                if (this.val$likeRequestWrapper.getError() == null && this.val$engagementRequest.getError() == null) {
                    LikeActionController.this.updateState(this.val$likeRequestWrapper.isObjectLiked(), this.val$engagementRequest.likeCountStringWithLike, this.val$engagementRequest.likeCountStringWithoutLike, this.val$engagementRequest.socialSentenceStringWithLike, this.val$engagementRequest.socialSentenceStringWithoutLike, this.val$likeRequestWrapper.getUnlikeToken());
                    return;
                }
                Logger.log(LoggingBehavior.REQUESTS, TAG, "Unable to refresh like state for id: '%s'", LikeActionController.this.objectId);
            }
        });
        graphRequestBatch.executeAsync();
    }

}
