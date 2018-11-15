/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookException;
import com.facebook.share.internal.LikeActionController;

static final class LikeActionController
implements Runnable {
    final /* synthetic */ LikeActionController.CreationCallback val$callback;
    final /* synthetic */ com.facebook.share.internal.LikeActionController val$controller;
    final /* synthetic */ FacebookException val$error;

    LikeActionController(LikeActionController.CreationCallback creationCallback, com.facebook.share.internal.LikeActionController likeActionController, FacebookException facebookException) {
        this.val$callback = creationCallback;
        this.val$controller = likeActionController;
        this.val$error = facebookException;
    }

    @Override
    public void run() {
        this.val$callback.onComplete(this.val$controller, this.val$error);
    }
}
