/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.util.Log
 */
package com.facebook.share.widget;

import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

private class AppInviteDialog.NativeHandler
extends FacebookDialogBase<AppInviteContent, AppInviteDialog.Result> {
    private AppInviteDialog.NativeHandler() {
        super(AppInviteDialog.this);
    }

    public boolean canShow(AppInviteContent appInviteContent, boolean bl) {
        return false;
    }

    public AppCall createAppCall(final AppInviteContent appInviteContent) {
        AppCall appCall = AppInviteDialog.this.createBaseAppCall();
        DialogPresenter.setupAppCallForNativeDialog(appCall, new DialogPresenter.ParameterProvider(){

            @Override
            public Bundle getLegacyParameters() {
                Log.e((String)AppInviteDialog.TAG, (String)"Attempting to present the AppInviteDialog with an outdated Facebook app on the device");
                return new Bundle();
            }

            @Override
            public Bundle getParameters() {
                return AppInviteDialog.createParameters(appInviteContent);
            }
        }, AppInviteDialog.getFeature());
        return appCall;
    }

}
