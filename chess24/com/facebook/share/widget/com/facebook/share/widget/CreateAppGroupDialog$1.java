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
import com.facebook.share.widget.CreateAppGroupDialog;

class CreateAppGroupDialog
extends ResultProcessor {
    final /* synthetic */ FacebookCallback val$callback;

    CreateAppGroupDialog(FacebookCallback facebookCallback, FacebookCallback facebookCallback2) {
        this.val$callback = facebookCallback2;
        super(facebookCallback);
    }

    @Override
    public void onSuccess(AppCall appCall, Bundle bundle) {
        this.val$callback.onSuccess(new CreateAppGroupDialog.Result(bundle.getString("id"), null));
    }
}
