/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.share.internal;

import android.content.Intent;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;

static final class LikeActionController
implements LikeActionController.CreationCallback {
    final /* synthetic */ Intent val$data;
    final /* synthetic */ int val$requestCode;
    final /* synthetic */ int val$resultCode;

    LikeActionController(int n, int n2, Intent intent) {
        this.val$requestCode = n;
        this.val$resultCode = n2;
        this.val$data = intent;
    }

    @Override
    public void onComplete(com.facebook.share.internal.LikeActionController likeActionController, FacebookException facebookException) {
        if (facebookException == null) {
            likeActionController.onActivityResult(this.val$requestCode, this.val$resultCode, this.val$data);
            return;
        }
        Utility.logd(TAG, facebookException);
    }
}
