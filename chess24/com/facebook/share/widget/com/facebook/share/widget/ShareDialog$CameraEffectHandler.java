/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.os.Bundle;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.ShareDialog;
import java.util.UUID;

private class ShareDialog.CameraEffectHandler
extends FacebookDialogBase<ShareContent, Sharer.Result> {
    private ShareDialog.CameraEffectHandler() {
        super(ShareDialog.this);
    }

    public boolean canShow(ShareContent shareContent, boolean bl) {
        if (shareContent instanceof ShareCameraEffectContent && ShareDialog.canShowNative(shareContent.getClass())) {
            return true;
        }
        return false;
    }

    public AppCall createAppCall(final ShareContent shareContent) {
        ShareContentValidation.validateForNativeShare(shareContent);
        final AppCall appCall = ShareDialog.this.createBaseAppCall();
        DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(ShareDialog.this.getShouldFailOnDataError()){
            final /* synthetic */ boolean val$shouldFailOnDataError;
            {
                this.val$shouldFailOnDataError = bl;
            }

            @Override
            public Bundle getLegacyParameters() {
                return LegacyNativeDialogParameters.create(appCall.getCallId(), shareContent, this.val$shouldFailOnDataError);
            }

            @Override
            public Bundle getParameters() {
                return NativeDialogParameters.create(appCall.getCallId(), shareContent, this.val$shouldFailOnDataError);
            }
        }, ShareDialog.getFeature(shareContent.getClass()));
        return appCall;
    }

    public Object getMode() {
        return ShareDialog.Mode.NATIVE;
    }

}
