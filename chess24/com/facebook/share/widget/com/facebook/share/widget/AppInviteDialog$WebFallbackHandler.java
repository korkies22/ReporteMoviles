/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

private class AppInviteDialog.WebFallbackHandler
extends FacebookDialogBase<AppInviteContent, AppInviteDialog.Result> {
    private AppInviteDialog.WebFallbackHandler() {
        super(AppInviteDialog.this);
    }

    public boolean canShow(AppInviteContent appInviteContent, boolean bl) {
        return false;
    }

    public AppCall createAppCall(AppInviteContent appInviteContent) {
        AppCall appCall = AppInviteDialog.this.createBaseAppCall();
        DialogPresenter.setupAppCallForWebFallbackDialog(appCall, AppInviteDialog.createParameters(appInviteContent), AppInviteDialog.getFeature());
        return appCall;
    }
}
