/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.internal.AppCall;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.internal.GameRequestValidation;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;

private class GameRequestDialog.WebHandler
extends FacebookDialogBase<GameRequestContent, GameRequestDialog.Result> {
    private GameRequestDialog.WebHandler() {
        super(GameRequestDialog.this);
    }

    public boolean canShow(GameRequestContent gameRequestContent, boolean bl) {
        return true;
    }

    public AppCall createAppCall(GameRequestContent gameRequestContent) {
        GameRequestValidation.validate(gameRequestContent);
        AppCall appCall = GameRequestDialog.this.createBaseAppCall();
        DialogPresenter.setupAppCallForWebDialog(appCall, GameRequestDialog.GAME_REQUEST_DIALOG, WebDialogParameters.create(gameRequestContent));
        return appCall;
    }
}
