/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.facebook.share.internal;

import android.content.SharedPreferences;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.internal.FileLruCache;
import java.util.concurrent.ConcurrentHashMap;

static final class LikeActionController
extends AccessTokenTracker {
    LikeActionController() {
    }

    @Override
    protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
        accessToken = FacebookSdk.getApplicationContext();
        if (accessToken2 == null) {
            objectSuffix = (objectSuffix + 1) % 1000;
            accessToken.getSharedPreferences(com.facebook.share.internal.LikeActionController.LIKE_ACTION_CONTROLLER_STORE, 0).edit().putInt(com.facebook.share.internal.LikeActionController.LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, objectSuffix).apply();
            cache.clear();
            controllerDiskCache.clearCache();
        }
        com.facebook.share.internal.LikeActionController.broadcastAction(null, com.facebook.share.internal.LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
    }
}
