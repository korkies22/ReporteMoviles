/*
 * Decompiled with CFR 0_134.
 */
package com.facebook;

import com.facebook.AccessToken;
import com.facebook.AccessTokenManager;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.GraphRequestBatch;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

class AccessTokenManager
implements GraphRequestBatch.Callback {
    final /* synthetic */ AccessToken val$accessToken;
    final /* synthetic */ AccessToken.AccessTokenRefreshCallback val$callback;
    final /* synthetic */ Set val$declinedPermissions;
    final /* synthetic */ Set val$permissions;
    final /* synthetic */ AtomicBoolean val$permissionsCallSucceeded;
    final /* synthetic */ AccessTokenManager.RefreshResult val$refreshResult;

    AccessTokenManager(AccessToken accessToken, AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback, AtomicBoolean atomicBoolean, AccessTokenManager.RefreshResult refreshResult, Set set, Set set2) {
        this.val$accessToken = accessToken;
        this.val$callback = accessTokenRefreshCallback;
        this.val$permissionsCallSucceeded = atomicBoolean;
        this.val$refreshResult = refreshResult;
        this.val$permissions = set;
        this.val$declinedPermissions = set2;
    }

    @Override
    public void onBatchCompleted(GraphRequestBatch object) {
        Set<String> set;
        void var1_4;
        block12 : {
            Set<String> set2;
            block10 : {
                block11 : {
                    set2 = null;
                    if (com.facebook.AccessTokenManager.getInstance().getCurrentAccessToken() == null || com.facebook.AccessTokenManager.getInstance().getCurrentAccessToken().getUserId() != this.val$accessToken.getUserId()) break block10;
                    if (this.val$permissionsCallSucceeded.get() || this.val$refreshResult.accessToken != null || this.val$refreshResult.expiresAt != 0) break block11;
                    if (this.val$callback != null) {
                        this.val$callback.OnTokenRefreshFailed(new FacebookException("Failed to refresh access token"));
                    }
                    AccessTokenManager.this.tokenRefreshInProgress.set(false);
                    object = this.val$callback;
                    return;
                }
                object = this.val$refreshResult.accessToken != null ? this.val$refreshResult.accessToken : this.val$accessToken.getToken();
                String string = this.val$accessToken.getApplicationId();
                String string2 = this.val$accessToken.getUserId();
                set = this.val$permissionsCallSucceeded.get() ? this.val$permissions : this.val$accessToken.getPermissions();
                Set<String> set3 = this.val$permissionsCallSucceeded.get() ? this.val$declinedPermissions : this.val$accessToken.getDeclinedPermissions();
                AccessTokenSource accessTokenSource = this.val$accessToken.getSource();
                Date date = this.val$refreshResult.expiresAt != 0 ? new Date((long)this.val$refreshResult.expiresAt * 1000L) : this.val$accessToken.getExpires();
                set = new AccessToken((String)object, string, string2, set, set3, accessTokenSource, date, new Date());
                try {
                    com.facebook.AccessTokenManager.getInstance().setCurrentAccessToken((AccessToken)((Object)set));
                    AccessTokenManager.this.tokenRefreshInProgress.set(false);
                    if (this.val$callback != null && set != null) {
                        this.val$callback.OnTokenRefreshed((AccessToken)((Object)set));
                    }
                    return;
                }
                catch (Throwable throwable) {}
                break block12;
            }
            try {
                if (this.val$callback != null) {
                    this.val$callback.OnTokenRefreshFailed(new FacebookException("No current access token to refresh"));
                }
                AccessTokenManager.this.tokenRefreshInProgress.set(false);
                object = this.val$callback;
                return;
            }
            catch (Throwable throwable) {
                set = set2;
            }
        }
        AccessTokenManager.this.tokenRefreshInProgress.set(false);
        if (this.val$callback != null && set != null) {
            this.val$callback.OnTokenRefreshed((AccessToken)((Object)set));
        }
        throw var1_4;
    }
}
