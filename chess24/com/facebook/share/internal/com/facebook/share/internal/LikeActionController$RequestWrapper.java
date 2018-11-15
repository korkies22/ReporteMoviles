/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequestBatch;
import com.facebook.share.internal.LikeActionController;

private static interface LikeActionController.RequestWrapper {
    public void addToBatch(GraphRequestBatch var1);

    public FacebookRequestError getError();
}
