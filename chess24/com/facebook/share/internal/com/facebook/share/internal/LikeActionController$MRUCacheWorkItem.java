/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.share.internal;

import com.facebook.share.internal.LikeActionController;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

private static class LikeActionController.MRUCacheWorkItem
implements Runnable {
    private static ArrayList<String> mruCachedItems = new ArrayList();
    private String cacheItem;
    private boolean shouldTrim;

    LikeActionController.MRUCacheWorkItem(String string, boolean bl) {
        this.cacheItem = string;
        this.shouldTrim = bl;
    }

    @Override
    public void run() {
        if (this.cacheItem != null) {
            mruCachedItems.remove(this.cacheItem);
            mruCachedItems.add(0, this.cacheItem);
        }
        if (this.shouldTrim && mruCachedItems.size() >= 128) {
            while (64 < mruCachedItems.size()) {
                String string = mruCachedItems.remove(mruCachedItems.size() - 1);
                cache.remove(string);
            }
        }
    }
}
