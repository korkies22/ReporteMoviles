/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;

private static class LikeActionController.CreateLikeActionControllerWorkItem
implements Runnable {
    private LikeActionController.CreationCallback callback;
    private String objectId;
    private LikeView.ObjectType objectType;

    LikeActionController.CreateLikeActionControllerWorkItem(String string, LikeView.ObjectType objectType, LikeActionController.CreationCallback creationCallback) {
        this.objectId = string;
        this.objectType = objectType;
        this.callback = creationCallback;
    }

    @Override
    public void run() {
        LikeActionController.createControllerForObjectIdAndType(this.objectId, this.objectType, this.callback);
    }
}
