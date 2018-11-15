/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.LikeActionController;

private static interface LikeActionController.LikeRequestWrapper
extends LikeActionController.RequestWrapper {
    public String getUnlikeToken();

    public boolean isObjectLiked();
}
