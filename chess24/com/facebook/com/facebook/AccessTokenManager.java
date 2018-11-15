/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenCache;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

public final class AccessTokenManager {
    public static final String ACTION_CURRENT_ACCESS_TOKEN_CHANGED = "com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED";
    public static final String EXTRA_NEW_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN";
    public static final String EXTRA_OLD_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN";
    private static final String ME_PERMISSIONS_GRAPH_PATH = "me/permissions";
    public static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    public static final String TAG = "AccessTokenManager";
    private static final String TOKEN_EXTEND_GRAPH_PATH = "oauth/access_token";
    private static final int TOKEN_EXTEND_RETRY_SECONDS = 3600;
    private static final int TOKEN_EXTEND_THRESHOLD_SECONDS = 86400;
    private static volatile AccessTokenManager instance;
    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;
    private Date lastAttemptedTokenExtendDate = new Date(0L);
    private final LocalBroadcastManager localBroadcastManager;
    private AtomicBoolean tokenRefreshInProgress = new AtomicBoolean(false);

    AccessTokenManager(LocalBroadcastManager localBroadcastManager, AccessTokenCache accessTokenCache) {
        Validate.notNull(localBroadcastManager, "localBroadcastManager");
        Validate.notNull(accessTokenCache, "accessTokenCache");
        this.localBroadcastManager = localBroadcastManager;
        this.accessTokenCache = accessTokenCache;
    }

    private static GraphRequest createExtendAccessTokenRequest(AccessToken accessToken, GraphRequest.Callback callback) {
        Bundle bundle = new Bundle();
        bundle.putString("grant_type", "fb_extend_sso_token");
        return new GraphRequest(accessToken, TOKEN_EXTEND_GRAPH_PATH, bundle, HttpMethod.GET, callback);
    }

    private static GraphRequest createGrantedPermissionsRequest(AccessToken accessToken, GraphRequest.Callback callback) {
        return new GraphRequest(accessToken, ME_PERMISSIONS_GRAPH_PATH, new Bundle(), HttpMethod.GET, callback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static AccessTokenManager getInstance() {
        if (instance != null) return instance;
        synchronized (AccessTokenManager.class) {
            if (instance != null) return instance;
            instance = new AccessTokenManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new AccessTokenCache());
            return instance;
        }
    }

    private void refreshCurrentAccessTokenImpl(final AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback) {
        final AccessToken accessToken = this.currentAccessToken;
        if (accessToken == null) {
            if (accessTokenRefreshCallback != null) {
                accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("No current access token to refresh"));
            }
            return;
        }
        if (!this.tokenRefreshInProgress.compareAndSet(false, true)) {
            if (accessTokenRefreshCallback != null) {
                accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("Refresh already in progress"));
            }
            return;
        }
        this.lastAttemptedTokenExtendDate = new Date();
        final HashSet hashSet = new HashSet();
        final HashSet hashSet2 = new HashSet();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final RefreshResult refreshResult = new RefreshResult();
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch(AccessTokenManager.createGrantedPermissionsRequest(accessToken, new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if ((graphResponse = graphResponse.getJSONObject()) == null) {
                    return;
                }
                if ((graphResponse = graphResponse.optJSONArray("data")) == null) {
                    return;
                }
                atomicBoolean.set(true);
                for (int i = 0; i < graphResponse.length(); ++i) {
                    Object object = graphResponse.optJSONObject(i);
                    if (object == null) continue;
                    CharSequence charSequence = object.optString("permission");
                    object = object.optString("status");
                    if (Utility.isNullOrEmpty((String)charSequence) || Utility.isNullOrEmpty((String)object)) continue;
                    if ((object = object.toLowerCase(Locale.US)).equals("granted")) {
                        hashSet.add(charSequence);
                        continue;
                    }
                    if (object.equals("declined")) {
                        hashSet2.add(charSequence);
                        continue;
                    }
                    charSequence = new StringBuilder();
                    charSequence.append("Unexpected status: ");
                    charSequence.append((String)object);
                    Log.w((String)AccessTokenManager.TAG, (String)charSequence.toString());
                }
            }
        }), AccessTokenManager.createExtendAccessTokenRequest(accessToken, new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if ((graphResponse = graphResponse.getJSONObject()) == null) {
                    return;
                }
                refreshResult.accessToken = graphResponse.optString("access_token");
                refreshResult.expiresAt = graphResponse.optInt("expires_at");
            }
        }));
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback(){

            @Override
            public void onBatchCompleted(GraphRequestBatch object) {
                Set<String> set;
                void var1_4;
                block12 : {
                    Set<String> set2;
                    block10 : {
                        block11 : {
                            set2 = null;
                            if (AccessTokenManager.getInstance().getCurrentAccessToken() == null || AccessTokenManager.getInstance().getCurrentAccessToken().getUserId() != accessToken.getUserId()) break block10;
                            if (atomicBoolean.get() || refreshResult.accessToken != null || refreshResult.expiresAt != 0) break block11;
                            if (accessTokenRefreshCallback != null) {
                                accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("Failed to refresh access token"));
                            }
                            AccessTokenManager.this.tokenRefreshInProgress.set(false);
                            object = accessTokenRefreshCallback;
                            return;
                        }
                        object = refreshResult.accessToken != null ? refreshResult.accessToken : accessToken.getToken();
                        String string = accessToken.getApplicationId();
                        String string2 = accessToken.getUserId();
                        set = atomicBoolean.get() ? hashSet : accessToken.getPermissions();
                        Set<String> set3 = atomicBoolean.get() ? hashSet2 : accessToken.getDeclinedPermissions();
                        AccessTokenSource accessTokenSource = accessToken.getSource();
                        Date date = refreshResult.expiresAt != 0 ? new Date((long)refreshResult.expiresAt * 1000L) : accessToken.getExpires();
                        set = new AccessToken((String)object, string, string2, set, set3, accessTokenSource, date, new Date());
                        try {
                            AccessTokenManager.getInstance().setCurrentAccessToken((AccessToken)((Object)set));
                            AccessTokenManager.this.tokenRefreshInProgress.set(false);
                            if (accessTokenRefreshCallback != null && set != null) {
                                accessTokenRefreshCallback.OnTokenRefreshed((AccessToken)((Object)set));
                            }
                            return;
                        }
                        catch (Throwable throwable) {}
                        break block12;
                    }
                    try {
                        if (accessTokenRefreshCallback != null) {
                            accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("No current access token to refresh"));
                        }
                        AccessTokenManager.this.tokenRefreshInProgress.set(false);
                        object = accessTokenRefreshCallback;
                        return;
                    }
                    catch (Throwable throwable) {
                        set = set2;
                    }
                }
                AccessTokenManager.this.tokenRefreshInProgress.set(false);
                if (accessTokenRefreshCallback != null && set != null) {
                    accessTokenRefreshCallback.OnTokenRefreshed((AccessToken)((Object)set));
                }
                throw var1_4;
            }
        });
        graphRequestBatch.executeAsync();
    }

    private void sendCurrentAccessTokenChangedBroadcast(AccessToken accessToken, AccessToken accessToken2) {
        Intent intent = new Intent(ACTION_CURRENT_ACCESS_TOKEN_CHANGED);
        intent.putExtra(EXTRA_OLD_ACCESS_TOKEN, (Parcelable)accessToken);
        intent.putExtra(EXTRA_NEW_ACCESS_TOKEN, (Parcelable)accessToken2);
        this.localBroadcastManager.sendBroadcast(intent);
    }

    private void setCurrentAccessToken(AccessToken accessToken, boolean bl) {
        AccessToken accessToken2 = this.currentAccessToken;
        this.currentAccessToken = accessToken;
        this.tokenRefreshInProgress.set(false);
        this.lastAttemptedTokenExtendDate = new Date(0L);
        if (bl) {
            if (accessToken != null) {
                this.accessTokenCache.save(accessToken);
            } else {
                this.accessTokenCache.clear();
                Utility.clearFacebookCookies(FacebookSdk.getApplicationContext());
            }
        }
        if (!Utility.areObjectsEqual(accessToken2, accessToken)) {
            this.sendCurrentAccessTokenChangedBroadcast(accessToken2, accessToken);
        }
    }

    private boolean shouldExtendAccessToken() {
        Object object = this.currentAccessToken;
        boolean bl = false;
        if (object == null) {
            return false;
        }
        object = new Date().getTime();
        boolean bl2 = bl;
        if (this.currentAccessToken.getSource().canExtendToken()) {
            bl2 = bl;
            if (object.longValue() - this.lastAttemptedTokenExtendDate.getTime() > 3600000L) {
                bl2 = bl;
                if (object.longValue() - this.currentAccessToken.getLastRefresh().getTime() > 86400000L) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    void extendAccessTokenIfNeeded() {
        if (!this.shouldExtendAccessToken()) {
            return;
        }
        this.refreshCurrentAccessToken(null);
    }

    AccessToken getCurrentAccessToken() {
        return this.currentAccessToken;
    }

    boolean loadCurrentAccessToken() {
        AccessToken accessToken = this.accessTokenCache.load();
        if (accessToken != null) {
            this.setCurrentAccessToken(accessToken, false);
            return true;
        }
        return false;
    }

    void refreshCurrentAccessToken(final AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback) {
        if (Looper.getMainLooper().equals((Object)Looper.myLooper())) {
            this.refreshCurrentAccessTokenImpl(accessTokenRefreshCallback);
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable(){

            @Override
            public void run() {
                AccessTokenManager.this.refreshCurrentAccessTokenImpl(accessTokenRefreshCallback);
            }
        });
    }

    void setCurrentAccessToken(AccessToken accessToken) {
        this.setCurrentAccessToken(accessToken, true);
    }

    private static class RefreshResult {
        public String accessToken;
        public int expiresAt;

        private RefreshResult() {
        }
    }

}
