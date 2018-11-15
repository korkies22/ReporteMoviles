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
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.ShareDialog;
import java.util.UUID;

class ShareDialog.CameraEffectHandler
implements DialogPresenter.ParameterProvider {
    final /* synthetic */ AppCall val$appCall;
    final /* synthetic */ ShareContent val$content;
    final /* synthetic */ boolean val$shouldFailOnDataError;

    ShareDialog.CameraEffectHandler(AppCall appCall, ShareContent shareContent, boolean bl) {
        this.val$appCall = appCall;
        this.val$content = shareContent;
        this.val$shouldFailOnDataError = bl;
    }

    @Override
    public Bundle getLegacyParameters() {
        return LegacyNativeDialogParameters.create(this.val$appCall.getCallId(), this.val$content, this.val$shouldFailOnDataError);
    }

    @Override
    public Bundle getParameters() {
        return NativeDialogParameters.create(this.val$appCall.getCallId(), this.val$content, this.val$shouldFailOnDataError);
    }
}
