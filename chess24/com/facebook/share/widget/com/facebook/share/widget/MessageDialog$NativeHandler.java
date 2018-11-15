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
import com.facebook.share.Sharer;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.MessageDialog;
import java.util.UUID;

private class MessageDialog.NativeHandler
extends FacebookDialogBase<ShareContent, Sharer.Result> {
    private MessageDialog.NativeHandler() {
        super(MessageDialog.this);
    }

    public boolean canShow(ShareContent shareContent, boolean bl) {
        if (shareContent != null && MessageDialog.canShow(shareContent.getClass())) {
            return true;
        }
        return false;
    }

    public AppCall createAppCall(final ShareContent shareContent) {
        ShareContentValidation.validateForMessage(shareContent);
        final AppCall appCall = MessageDialog.this.createBaseAppCall();
        final boolean bl = MessageDialog.this.getShouldFailOnDataError();
        MessageDialog.logDialogShare((Context)MessageDialog.this.getActivityContext(), shareContent, appCall);
        DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(){

            @Override
            public Bundle getLegacyParameters() {
                return LegacyNativeDialogParameters.create(appCall.getCallId(), shareContent, bl);
            }

            @Override
            public Bundle getParameters() {
                return NativeDialogParameters.create(appCall.getCallId(), shareContent, bl);
            }
        }, MessageDialog.getFeature(shareContent.getClass()));
        return appCall;
    }

}
