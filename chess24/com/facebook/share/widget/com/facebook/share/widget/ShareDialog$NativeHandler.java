/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareDialogFeature;
import com.facebook.share.model.ShareCameraEffectContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import java.util.UUID;

private class ShareDialog.NativeHandler
extends FacebookDialogBase<ShareContent, Sharer.Result> {
    private ShareDialog.NativeHandler() {
        super(ShareDialog.this);
    }

    public boolean canShow(ShareContent shareContent, boolean bl) {
        boolean bl2 = false;
        if (shareContent != null) {
            boolean bl3;
            if (shareContent instanceof ShareCameraEffectContent) {
                return false;
            }
            if (!bl) {
                bl3 = shareContent.getShareHashtag() != null ? DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.HASHTAG) : true;
                bl = bl3;
                if (shareContent instanceof ShareLinkContent) {
                    bl = bl3;
                    if (!Utility.isNullOrEmpty(((ShareLinkContent)shareContent).getQuote())) {
                        bl = bl3 & DialogPresenter.canPresentNativeDialogWithFeature(ShareDialogFeature.LINK_SHARE_QUOTES);
                    }
                }
            } else {
                bl = true;
            }
            bl3 = bl2;
            if (bl) {
                bl3 = bl2;
                if (ShareDialog.canShowNative(shareContent.getClass())) {
                    bl3 = true;
                }
            }
            return bl3;
        }
        return false;
    }

    public AppCall createAppCall(final ShareContent shareContent) {
        ShareDialog.this.logDialogShare((Context)ShareDialog.this.getActivityContext(), shareContent, ShareDialog.Mode.NATIVE);
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
