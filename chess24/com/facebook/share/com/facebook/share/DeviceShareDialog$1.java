/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcelable
 */
package com.facebook.share;

import android.content.Intent;
import android.os.Parcelable;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.share.DeviceShareDialog;

class DeviceShareDialog
implements CallbackManagerImpl.Callback {
    final /* synthetic */ FacebookCallback val$callback;

    DeviceShareDialog(FacebookCallback facebookCallback) {
        this.val$callback = facebookCallback;
    }

    @Override
    public boolean onActivityResult(int n, Intent object) {
        if (object.hasExtra("error")) {
            object = (FacebookRequestError)object.getParcelableExtra("error");
            this.val$callback.onError(object.getException());
            return true;
        }
        this.val$callback.onSuccess(new DeviceShareDialog.Result());
        return true;
    }
}
