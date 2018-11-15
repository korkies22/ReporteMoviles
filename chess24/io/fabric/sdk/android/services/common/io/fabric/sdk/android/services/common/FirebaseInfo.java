/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;

public class FirebaseInfo {
    static final String FIREBASE_FEATURE_SWITCH = "com.crashlytics.useFirebaseAppId";
    static final String GOOGLE_APP_ID = "google_app_id";

    protected String createApiKeyFromFirebaseAppId(String string) {
        return CommonUtils.sha256(string).substring(0, 40);
    }

    protected String getApiKeyFromFirebaseAppId(Context context) {
        int n = CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string");
        if (n != 0) {
            Fabric.getLogger().d("Fabric", "Generating Crashlytics ApiKey from google_app_id in Strings");
            return this.createApiKeyFromFirebaseAppId(context.getResources().getString(n));
        }
        return null;
    }

    public boolean isFirebaseCrashlyticsEnabled(Context context) {
        boolean bl = false;
        if (CommonUtils.getBooleanResourceValue(context, FIREBASE_FEATURE_SWITCH, false)) {
            return true;
        }
        boolean bl2 = CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string") != 0;
        boolean bl3 = !TextUtils.isEmpty((CharSequence)new ApiKey().getApiKeyFromManifest(context)) || !TextUtils.isEmpty((CharSequence)new ApiKey().getApiKeyFromStrings(context));
        boolean bl4 = bl;
        if (bl2) {
            bl4 = bl;
            if (!bl3) {
                bl4 = true;
            }
        }
        return bl4;
    }
}
