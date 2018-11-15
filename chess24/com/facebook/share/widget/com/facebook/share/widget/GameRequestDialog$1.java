/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.os.Bundle;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.widget.GameRequestDialog;

class GameRequestDialog
extends ResultProcessor {
    final /* synthetic */ FacebookCallback val$callback;

    GameRequestDialog(FacebookCallback facebookCallback, FacebookCallback facebookCallback2) {
        this.val$callback = facebookCallback2;
        super(facebookCallback);
    }

    @Override
    public void onSuccess(AppCall appCall, Bundle bundle) {
        if (bundle != null) {
            this.val$callback.onSuccess(new GameRequestDialog.Result(bundle, null));
            return;
        }
        this.onCancel(appCall);
    }
}
