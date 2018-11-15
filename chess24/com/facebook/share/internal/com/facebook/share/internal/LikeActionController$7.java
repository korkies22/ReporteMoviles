/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphRequestBatch;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;

class LikeActionController
implements LikeActionController.RequestCompletionCallback {
    final /* synthetic */ Bundle val$analyticsParameters;

    LikeActionController(Bundle bundle) {
        this.val$analyticsParameters = bundle;
    }

    @Override
    public void onComplete() {
        if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
            Bundle bundle = new Bundle();
            bundle.putString("com.facebook.platform.status.ERROR_DESCRIPTION", com.facebook.share.internal.LikeActionController.ERROR_INVALID_OBJECT_ID);
            com.facebook.share.internal.LikeActionController.broadcastAction(LikeActionController.this, com.facebook.share.internal.LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle);
            return;
        }
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
        final LikeActionController.PublishLikeRequestWrapper publishLikeRequestWrapper = new LikeActionController.PublishLikeRequestWrapper(LikeActionController.this, LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
        publishLikeRequestWrapper.addToBatch(graphRequestBatch);
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback(){

            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                LikeActionController.this.isPendingLikeOrUnlike = false;
                if (publishLikeRequestWrapper.getError() != null) {
                    LikeActionController.this.publishDidError(false);
                    return;
                }
                LikeActionController.this.unlikeToken = Utility.coerceValueIfNullOrEmpty(publishLikeRequestWrapper.unlikeToken, null);
                LikeActionController.this.isObjectLikedOnServer = true;
                LikeActionController.this.getAppEventsLogger().logSdkEvent("fb_like_control_did_like", null, 7.this.val$analyticsParameters);
                LikeActionController.this.publishAgainIfNeeded(7.this.val$analyticsParameters);
            }
        });
        graphRequestBatch.executeAsync();
    }

}
