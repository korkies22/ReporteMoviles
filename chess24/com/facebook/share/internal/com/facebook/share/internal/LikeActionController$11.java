/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequestBatch;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;

class LikeActionController
implements GraphRequestBatch.Callback {
    final /* synthetic */ LikeActionController.RequestCompletionCallback val$completionHandler;
    final /* synthetic */ LikeActionController.GetOGObjectIdRequestWrapper val$objectIdRequest;
    final /* synthetic */ LikeActionController.GetPageIdRequestWrapper val$pageIdRequest;

    LikeActionController(LikeActionController.GetOGObjectIdRequestWrapper getOGObjectIdRequestWrapper, LikeActionController.GetPageIdRequestWrapper getPageIdRequestWrapper, LikeActionController.RequestCompletionCallback requestCompletionCallback) {
        this.val$objectIdRequest = getOGObjectIdRequestWrapper;
        this.val$pageIdRequest = getPageIdRequestWrapper;
        this.val$completionHandler = requestCompletionCallback;
    }

    @Override
    public void onBatchCompleted(GraphRequestBatch object) {
        LikeActionController.this.verifiedObjectId = this.val$objectIdRequest.verifiedObjectId;
        if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
            LikeActionController.this.verifiedObjectId = this.val$pageIdRequest.verifiedObjectId;
            LikeActionController.this.objectIsPage = this.val$pageIdRequest.objectIsPage;
        }
        if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Unable to verify the FB id for '%s'. Verify that it is a valid FB object or page", LikeActionController.this.objectId);
            com.facebook.share.internal.LikeActionController likeActionController = LikeActionController.this;
            object = this.val$pageIdRequest.getError() != null ? this.val$pageIdRequest.getError() : this.val$objectIdRequest.getError();
            likeActionController.logAppEventForError("get_verified_id", (FacebookRequestError)object);
        }
        if (this.val$completionHandler != null) {
            this.val$completionHandler.onComplete();
        }
    }
}
