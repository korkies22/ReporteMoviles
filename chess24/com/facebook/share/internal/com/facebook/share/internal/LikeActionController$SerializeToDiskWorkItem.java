/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.LikeActionController;

private static class LikeActionController.SerializeToDiskWorkItem
implements Runnable {
    private String cacheKey;
    private String controllerJson;

    LikeActionController.SerializeToDiskWorkItem(String string, String string2) {
        this.cacheKey = string;
        this.controllerJson = string2;
    }

    @Override
    public void run() {
        LikeActionController.serializeToDiskSynchronously(this.cacheKey, this.controllerJson);
    }
}
