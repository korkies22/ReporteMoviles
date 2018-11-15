/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.GraphRequestBatch;

public static interface GraphRequestBatch.OnProgressCallback
extends GraphRequestBatch.Callback {
    public void onBatchProgress(GraphRequestBatch var1, long var2, long var4);
}
