/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.AppCall;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ResultProcessor;

static final class ShareInternalUtility
extends ResultProcessor {
    final /* synthetic */ FacebookCallback val$callback;

    ShareInternalUtility(FacebookCallback facebookCallback, FacebookCallback facebookCallback2) {
        this.val$callback = facebookCallback2;
        super(facebookCallback);
    }

    @Override
    public void onCancel(AppCall appCall) {
        com.facebook.share.internal.ShareInternalUtility.invokeOnCancelCallback(this.val$callback);
    }

    @Override
    public void onError(AppCall appCall, FacebookException facebookException) {
        com.facebook.share.internal.ShareInternalUtility.invokeOnErrorCallback((FacebookCallback<Sharer.Result>)this.val$callback, facebookException);
    }

    @Override
    public void onSuccess(AppCall object, Bundle bundle) {
        if (bundle != null) {
            object = com.facebook.share.internal.ShareInternalUtility.getNativeDialogCompletionGesture(bundle);
            if (object != null && !"post".equalsIgnoreCase((String)object)) {
                if ("cancel".equalsIgnoreCase((String)object)) {
                    com.facebook.share.internal.ShareInternalUtility.invokeOnCancelCallback(this.val$callback);
                    return;
                }
                com.facebook.share.internal.ShareInternalUtility.invokeOnErrorCallback((FacebookCallback<Sharer.Result>)this.val$callback, new FacebookException("UnknownError"));
                return;
            }
            object = com.facebook.share.internal.ShareInternalUtility.getShareDialogPostId(bundle);
            com.facebook.share.internal.ShareInternalUtility.invokeOnSuccessCallback(this.val$callback, (String)object);
        }
    }
}
