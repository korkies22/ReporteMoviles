/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook;

import android.content.Context;
import com.facebook.AccessTokenCache;
import com.facebook.FacebookSdk;
import com.facebook.LegacyTokenHelper;

static class AccessTokenCache.SharedPreferencesTokenCachingStrategyFactory {
    AccessTokenCache.SharedPreferencesTokenCachingStrategyFactory() {
    }

    public LegacyTokenHelper create() {
        return new LegacyTokenHelper(FacebookSdk.getApplicationContext());
    }
}
