/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.internal.LikeDialog;

private class LikeDialog.WebFallbackHandler
extends FacebookDialogBase<LikeContent, LikeDialog.Result> {
    private LikeDialog.WebFallbackHandler() {
        super(LikeDialog.this);
    }

    public boolean canShow(LikeContent likeContent, boolean bl) {
        return false;
    }

    public AppCall createAppCall(LikeContent likeContent) {
        AppCall appCall = LikeDialog.this.createBaseAppCall();
        DialogPresenter.setupAppCallForWebFallbackDialog(appCall, LikeDialog.createParameters(likeContent), LikeDialog.getFeature());
        return appCall;
    }
}
