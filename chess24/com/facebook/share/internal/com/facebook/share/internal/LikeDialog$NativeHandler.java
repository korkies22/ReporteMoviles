/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 */
package com.facebook.share.internal;

import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.internal.LikeDialog;

private class LikeDialog.NativeHandler
extends FacebookDialogBase<LikeContent, LikeDialog.Result> {
    private LikeDialog.NativeHandler() {
        super(LikeDialog.this);
    }

    public boolean canShow(LikeContent likeContent, boolean bl) {
        return false;
    }

    public AppCall createAppCall(final LikeContent likeContent) {
        AppCall appCall = LikeDialog.this.createBaseAppCall();
        DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(){

            @Override
            public Bundle getLegacyParameters() {
                Log.e((String)LikeDialog.TAG, (String)"Attempting to present the Like Dialog with an outdated Facebook app on the device");
                return new Bundle();
            }

            @Override
            public Bundle getParameters() {
                return LikeDialog.createParameters(likeContent);
            }
        }, LikeDialog.getFeature());
        return appCall;
    }

}
