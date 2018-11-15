/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.LegacyTokenHelper;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

class AccessTokenCache {
    static final String CACHED_ACCESS_TOKEN_KEY = "com.facebook.AccessTokenManager.CachedAccessToken";
    private final SharedPreferences sharedPreferences;
    private LegacyTokenHelper tokenCachingStrategy;
    private final SharedPreferencesTokenCachingStrategyFactory tokenCachingStrategyFactory;

    public AccessTokenCache() {
        this(FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.AccessTokenManager.SharedPreferences", 0), new SharedPreferencesTokenCachingStrategyFactory());
    }

    AccessTokenCache(SharedPreferences sharedPreferences, SharedPreferencesTokenCachingStrategyFactory sharedPreferencesTokenCachingStrategyFactory) {
        this.sharedPreferences = sharedPreferences;
        this.tokenCachingStrategyFactory = sharedPreferencesTokenCachingStrategyFactory;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private AccessToken getCachedAccessToken() {
        String object = this.sharedPreferences.getString(CACHED_ACCESS_TOKEN_KEY, null);
        if (object == null) return null;
        try {
            return AccessToken.createFromJSONObject(new JSONObject(object));
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    private AccessToken getLegacyAccessToken() {
        Bundle bundle = this.getTokenCachingStrategy().load();
        if (bundle != null && LegacyTokenHelper.hasTokenInformation(bundle)) {
            return AccessToken.createFromLegacyCache(bundle);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private LegacyTokenHelper getTokenCachingStrategy() {
        if (this.tokenCachingStrategy != null) return this.tokenCachingStrategy;
        synchronized (this) {
            if (this.tokenCachingStrategy != null) return this.tokenCachingStrategy;
            this.tokenCachingStrategy = this.tokenCachingStrategyFactory.create();
            return this.tokenCachingStrategy;
        }
    }

    private boolean hasCachedAccessToken() {
        return this.sharedPreferences.contains(CACHED_ACCESS_TOKEN_KEY);
    }

    private boolean shouldCheckLegacyToken() {
        return FacebookSdk.isLegacyTokenUpgradeSupported();
    }

    public void clear() {
        this.sharedPreferences.edit().remove(CACHED_ACCESS_TOKEN_KEY).apply();
        if (this.shouldCheckLegacyToken()) {
            this.getTokenCachingStrategy().clear();
        }
    }

    public AccessToken load() {
        AccessToken accessToken;
        if (this.hasCachedAccessToken()) {
            return this.getCachedAccessToken();
        }
        if (this.shouldCheckLegacyToken()) {
            AccessToken accessToken2;
            accessToken = accessToken2 = this.getLegacyAccessToken();
            if (accessToken2 != null) {
                this.save(accessToken2);
                this.getTokenCachingStrategy().clear();
                return accessToken2;
            }
        } else {
            accessToken = null;
        }
        return accessToken;
    }

    public void save(AccessToken accessToken) {
        Validate.notNull(accessToken, "accessToken");
        try {
            accessToken = accessToken.toJSONObject();
            this.sharedPreferences.edit().putString(CACHED_ACCESS_TOKEN_KEY, accessToken.toString()).apply();
            return;
        }
        catch (JSONException jSONException) {
            return;
        }
    }

    static class SharedPreferencesTokenCachingStrategyFactory {
        SharedPreferencesTokenCachingStrategyFactory() {
        }

        public LegacyTokenHelper create() {
            return new LegacyTokenHelper(FacebookSdk.getApplicationContext());
        }
    }

}
