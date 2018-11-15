/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.login;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.login.LoginLogger;
import com.facebook.login.LoginMethodHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

class LoginManager
implements PlatformServiceClient.CompletedListener {
    final /* synthetic */ String val$applicationId;
    final /* synthetic */ LoginLogger val$logger;
    final /* synthetic */ String val$loggerRef;
    final /* synthetic */ LoginStatusCallback val$responseCallback;

    LoginManager(String string, LoginLogger loginLogger, LoginStatusCallback loginStatusCallback, String string2) {
        this.val$loggerRef = string;
        this.val$logger = loginLogger;
        this.val$responseCallback = loginStatusCallback;
        this.val$applicationId = string2;
    }

    @Override
    public void completed(Bundle object) {
        if (object != null) {
            Object object2 = object.getString("com.facebook.platform.status.ERROR_TYPE");
            String string = object.getString("com.facebook.platform.status.ERROR_DESCRIPTION");
            if (object2 != null) {
                com.facebook.login.LoginManager.handleLoginStatusError((String)object2, string, this.val$loggerRef, this.val$logger, this.val$responseCallback);
                return;
            }
            string = object.getString("com.facebook.platform.extra.ACCESS_TOKEN");
            long l = object.getLong("com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH");
            ArrayList arrayList = object.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
            String string2 = object.getString("signed request");
            object2 = null;
            if (!Utility.isNullOrEmpty(string2)) {
                object2 = LoginMethodHandler.getUserIDFromSignedRequest(string2);
            }
            if (!(Utility.isNullOrEmpty(string) || arrayList == null || arrayList.isEmpty() || Utility.isNullOrEmpty((String)object2))) {
                object2 = new AccessToken(string, this.val$applicationId, (String)object2, arrayList, null, null, new Date(l), null);
                AccessToken.setCurrentAccessToken((AccessToken)object2);
                object = com.facebook.login.LoginManager.getProfileFromBundle(object);
                if (object != null) {
                    Profile.setCurrentProfile((Profile)object);
                } else {
                    Profile.fetchProfileForCurrentAccessToken();
                }
                this.val$logger.logLoginStatusSuccess(this.val$loggerRef);
                this.val$responseCallback.onCompleted((AccessToken)object2);
                return;
            }
            this.val$logger.logLoginStatusFailure(this.val$loggerRef);
            this.val$responseCallback.onFailure();
            return;
        }
        this.val$logger.logLoginStatusFailure(this.val$loggerRef);
        this.val$responseCallback.onFailure();
    }
}
