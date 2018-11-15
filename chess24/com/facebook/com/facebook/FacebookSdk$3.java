/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook;

import android.content.Context;
import com.facebook.AccessToken;
import com.facebook.AccessTokenManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileManager;
import com.facebook.appevents.AppEventsLogger;
import java.util.concurrent.Callable;

static final class FacebookSdk
implements Callable<Void> {
    final /* synthetic */ Context val$applicationContext;
    final /* synthetic */ FacebookSdk.InitializeCallback val$callback;

    FacebookSdk(FacebookSdk.InitializeCallback initializeCallback, Context context) {
        this.val$callback = initializeCallback;
        this.val$applicationContext = context;
    }

    @Override
    public Void call() throws Exception {
        AccessTokenManager.getInstance().loadCurrentAccessToken();
        ProfileManager.getInstance().loadCurrentProfile();
        if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() == null) {
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (this.val$callback != null) {
            this.val$callback.onInitialized();
        }
        AppEventsLogger.initializeLib(applicationContext, applicationId);
        AppEventsLogger.newLogger(this.val$applicationContext.getApplicationContext()).flush();
        return null;
    }
}
