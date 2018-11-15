/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.widget;

import com.facebook.FacebookException;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;

private class LikeView.LikeActionControllerCreationCallback
implements LikeActionController.CreationCallback {
    private boolean isCancelled;

    private LikeView.LikeActionControllerCreationCallback() {
    }

    public void cancel() {
        this.isCancelled = true;
    }

    @Override
    public void onComplete(LikeActionController likeActionController, FacebookException facebookException) {
        if (this.isCancelled) {
            return;
        }
        FacebookException facebookException2 = facebookException;
        if (likeActionController != null) {
            if (!likeActionController.shouldEnableView()) {
                facebookException = new FacebookException("Cannot use LikeView. The device may not be supported.");
            }
            LikeView.this.associateWithLikeActionController(likeActionController);
            LikeView.this.updateLikeStateAndLayout();
            facebookException2 = facebookException;
        }
        if (facebookException2 != null && LikeView.this.onErrorListener != null) {
            LikeView.this.onErrorListener.onError(facebookException2);
        }
        LikeView.this.creationCallback = null;
    }
}
