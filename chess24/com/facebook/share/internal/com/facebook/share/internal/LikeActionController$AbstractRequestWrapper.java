/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;

private abstract class LikeActionController.AbstractRequestWrapper
implements LikeActionController.RequestWrapper {
    protected FacebookRequestError error;
    protected String objectId;
    protected LikeView.ObjectType objectType;
    private GraphRequest request;

    protected LikeActionController.AbstractRequestWrapper(String string, LikeView.ObjectType objectType) {
        this.objectId = string;
        this.objectType = objectType;
    }

    @Override
    public void addToBatch(GraphRequestBatch graphRequestBatch) {
        graphRequestBatch.add(this.request);
    }

    @Override
    public FacebookRequestError getError() {
        return this.error;
    }

    protected void processError(FacebookRequestError facebookRequestError) {
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Error running request for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
    }

    protected abstract void processSuccess(GraphResponse var1);

    protected void setRequest(GraphRequest graphRequest) {
        this.request = graphRequest;
        graphRequest.setVersion(FacebookSdk.getGraphApiVersion());
        graphRequest.setCallback(new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                AbstractRequestWrapper.this.error = graphResponse.getError();
                if (AbstractRequestWrapper.this.error != null) {
                    AbstractRequestWrapper.this.processError(AbstractRequestWrapper.this.error);
                    return;
                }
                AbstractRequestWrapper.this.processSuccess(graphResponse);
            }
        });
    }

}
