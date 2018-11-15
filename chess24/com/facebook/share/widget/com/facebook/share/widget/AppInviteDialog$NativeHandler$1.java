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
import com.facebook.internal.DialogPresenter;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

class AppInviteDialog.NativeHandler
implements DialogPresenter.ParameterProvider {
    final /* synthetic */ AppInviteContent val$content;

    AppInviteDialog.NativeHandler(AppInviteContent appInviteContent) {
        this.val$content = appInviteContent;
    }

    @Override
    public Bundle getLegacyParameters() {
        Log.e((String)AppInviteDialog.TAG, (String)"Attempting to present the AppInviteDialog with an outdated Facebook app on the device");
        return new Bundle();
    }

    @Override
    public Bundle getParameters() {
        return AppInviteDialog.createParameters(this.val$content);
    }
}
