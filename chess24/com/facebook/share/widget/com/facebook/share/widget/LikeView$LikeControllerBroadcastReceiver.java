/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.facebook.share.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.widget.LikeView;

private class LikeView.LikeControllerBroadcastReceiver
extends BroadcastReceiver {
    private LikeView.LikeControllerBroadcastReceiver() {
    }

    public void onReceive(Context object, Intent intent) {
        boolean bl;
        object = intent.getAction();
        intent = intent.getExtras();
        boolean bl2 = bl = true;
        if (intent != null) {
            String string = intent.getString("com.facebook.sdk.LikeActionController.OBJECT_ID");
            bl2 = bl;
            if (!Utility.isNullOrEmpty(string)) {
                bl2 = Utility.areObjectsEqual(LikeView.this.objectId, string) ? bl : false;
            }
        }
        if (!bl2) {
            return;
        }
        if ("com.facebook.sdk.LikeActionController.UPDATED".equals(object)) {
            LikeView.this.updateLikeStateAndLayout();
            return;
        }
        if ("com.facebook.sdk.LikeActionController.DID_ERROR".equals(object)) {
            if (LikeView.this.onErrorListener != null) {
                LikeView.this.onErrorListener.onError(NativeProtocol.getExceptionFromErrorData((Bundle)intent));
                return;
            }
        } else if ("com.facebook.sdk.LikeActionController.DID_RESET".equals(object)) {
            LikeView.this.setObjectIdAndTypeForced(LikeView.this.objectId, LikeView.this.objectType);
            LikeView.this.updateLikeStateAndLayout();
        }
    }
}
