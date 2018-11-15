/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

static final class LikeActionController
implements Runnable {
    final /* synthetic */ com.facebook.share.internal.LikeActionController val$controllerToRefresh;

    LikeActionController(com.facebook.share.internal.LikeActionController likeActionController) {
        this.val$controllerToRefresh = likeActionController;
    }

    @Override
    public void run() {
        this.val$controllerToRefresh.refreshStatusAsync();
    }
}
