/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.internal.LikeActionController;

class LikeActionController.AbstractRequestWrapper
implements GraphRequest.Callback {
    LikeActionController.AbstractRequestWrapper() {
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        AbstractRequestWrapper.this.error = graphResponse.getError();
        if (AbstractRequestWrapper.this.error != null) {
            AbstractRequestWrapper.this.processError(AbstractRequestWrapper.this.error);
            return;
        }
        AbstractRequestWrapper.this.processSuccess(graphResponse);
    }
}
