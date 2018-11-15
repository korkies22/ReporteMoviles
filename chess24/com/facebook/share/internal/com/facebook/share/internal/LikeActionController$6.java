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
import com.facebook.FacebookOperationCanceledException;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AppCall;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ResultProcessor;
import java.util.UUID;

class LikeActionController
extends ResultProcessor {
    final /* synthetic */ Bundle val$analyticsParameters;

    LikeActionController(FacebookCallback facebookCallback, Bundle bundle) {
        this.val$analyticsParameters = bundle;
        super(facebookCallback);
    }

    @Override
    public void onCancel(AppCall appCall) {
        this.onError(appCall, new FacebookOperationCanceledException());
    }

    @Override
    public void onError(AppCall appCall, FacebookException facebookException) {
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Like Dialog failed with error : %s", facebookException);
        Bundle bundle = this.val$analyticsParameters == null ? new Bundle() : this.val$analyticsParameters;
        bundle.putString("call_id", appCall.getCallId().toString());
        LikeActionController.this.logAppEventForError("present_dialog", bundle);
        com.facebook.share.internal.LikeActionController.broadcastAction(LikeActionController.this, com.facebook.share.internal.LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, NativeProtocol.createBundleForException(facebookException));
    }

    @Override
    public void onSuccess(AppCall appCall, Bundle object) {
        if (object != null) {
            if (!object.containsKey(com.facebook.share.internal.LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                return;
            }
            boolean bl = object.getBoolean(com.facebook.share.internal.LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY);
            String string = LikeActionController.this.likeCountStringWithLike;
            String string2 = LikeActionController.this.likeCountStringWithoutLike;
            if (object.containsKey(com.facebook.share.internal.LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY)) {
                string2 = string = object.getString(com.facebook.share.internal.LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY);
            }
            String string3 = LikeActionController.this.socialSentenceWithLike;
            String string4 = LikeActionController.this.socialSentenceWithoutLike;
            if (object.containsKey(com.facebook.share.internal.LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY)) {
                string4 = string3 = object.getString(com.facebook.share.internal.LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY);
            }
            object = object.containsKey(com.facebook.share.internal.LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY) ? object.getString("unlike_token") : LikeActionController.this.unlikeToken;
            Bundle bundle = this.val$analyticsParameters == null ? new Bundle() : this.val$analyticsParameters;
            bundle.putString("call_id", appCall.getCallId().toString());
            LikeActionController.this.getAppEventsLogger().logSdkEvent("fb_like_control_dialog_did_succeed", null, bundle);
            LikeActionController.this.updateState(bl, string, string2, string3, string4, (String)object);
            return;
        }
    }
}
