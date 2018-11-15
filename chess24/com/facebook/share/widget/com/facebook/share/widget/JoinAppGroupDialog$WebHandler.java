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
import com.facebook.share.widget.JoinAppGroupDialog;

private class JoinAppGroupDialog.WebHandler
extends FacebookDialogBase<String, JoinAppGroupDialog.Result> {
    private JoinAppGroupDialog.WebHandler() {
        super(JoinAppGroupDialog.this);
    }

    public boolean canShow(String string, boolean bl) {
        return true;
    }

    public AppCall createAppCall(String string) {
        AppCall appCall = JoinAppGroupDialog.this.createBaseAppCall();
        Bundle bundle = new Bundle();
        bundle.putString("id", string);
        DialogPresenter.setupAppCallForWebDialog(appCall, JoinAppGroupDialog.JOIN_GAME_GROUP_DIALOG, bundle);
        return appCall;
    }
}
