/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.AppGroupCreationContent;
import com.facebook.share.widget.CreateAppGroupDialog;

private class CreateAppGroupDialog.WebHandler
extends FacebookDialogBase<AppGroupCreationContent, CreateAppGroupDialog.Result> {
    private CreateAppGroupDialog.WebHandler() {
        super(CreateAppGroupDialog.this);
    }

    public boolean canShow(AppGroupCreationContent appGroupCreationContent, boolean bl) {
        return true;
    }

    public AppCall createAppCall(AppGroupCreationContent appGroupCreationContent) {
        AppCall appCall = CreateAppGroupDialog.this.createBaseAppCall();
        DialogPresenter.setupAppCallForWebDialog(appCall, CreateAppGroupDialog.GAME_GROUP_CREATION_DIALOG, WebDialogParameters.create(appGroupCreationContent));
        return appCall;
    }
}
