/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.FirebaseInfo;

public class ApiKey {
    static final String CRASHLYTICS_API_KEY = "com.crashlytics.ApiKey";
    static final String FABRIC_API_KEY = "io.fabric.ApiKey";
    static final String STRING_TWITTER_CONSUMER_SECRET = "@string/twitter_consumer_secret";

    @Deprecated
    public static String getApiKey(Context context) {
        Fabric.getLogger().w("Fabric", "getApiKey(context) is deprecated, please upgrade kit(s) to the latest version.");
        return new ApiKey().getValue(context);
    }

    @Deprecated
    public static String getApiKey(Context context, boolean bl) {
        Fabric.getLogger().w("Fabric", "getApiKey(context, debug) is deprecated, please upgrade kit(s) to the latest version.");
        return new ApiKey().getValue(context);
    }

    protected String buildApiKeyInstructions() {
        return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }

    protected String getApiKeyFromFirebaseAppId(Context context) {
        return new FirebaseInfo().getApiKeyFromFirebaseAppId(context);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected String getApiKeyFromManifest(Context object) {
        Logger logger;
        StringBuilder stringBuilder2;
        Object stringBuilder;
        block6 : {
            stringBuilder2 = null;
            Object var5_4 = null;
            logger = null;
            stringBuilder = stringBuilder2;
            String string = object.getPackageName();
            stringBuilder = stringBuilder2;
            string = object.getPackageManager().getApplicationInfo((String)string, (int)128).metaData;
            stringBuilder = var5_4;
            if (string == null) return stringBuilder;
            stringBuilder = stringBuilder2;
            object = string.getString(FABRIC_API_KEY);
            if (STRING_TWITTER_CONSUMER_SECRET.equals(object)) {
                Fabric.getLogger().d("Fabric", "Ignoring bad default value for Fabric ApiKey set by FirebaseUI-Auth");
                object = logger;
            }
            stringBuilder = object;
            if (object != null) return stringBuilder;
            stringBuilder = object;
            Fabric.getLogger().d("Fabric", "Falling back to Crashlytics key lookup from Manifest");
            stringBuilder = object;
            return string.getString(CRASHLYTICS_API_KEY);
            catch (Exception exception) {
                stringBuilder = object;
                object = exception;
            }
            break block6;
            catch (Exception exception) {
                // empty catch block
            }
        }
        logger = Fabric.getLogger();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Caught non-fatal exception while retrieving apiKey: ");
        stringBuilder2.append(object);
        logger.d("Fabric", stringBuilder2.toString());
        return stringBuilder;
    }

    protected String getApiKeyFromStrings(Context context) {
        int n;
        int n2 = n = CommonUtils.getResourcesIdentifier(context, FABRIC_API_KEY, "string");
        if (n == 0) {
            Fabric.getLogger().d("Fabric", "Falling back to Crashlytics key lookup from Strings");
            n2 = CommonUtils.getResourcesIdentifier(context, CRASHLYTICS_API_KEY, "string");
        }
        if (n2 != 0) {
            return context.getResources().getString(n2);
        }
        return null;
    }

    public String getValue(Context context) {
        String string;
        String string2 = string = this.getApiKeyFromManifest(context);
        if (TextUtils.isEmpty((CharSequence)string)) {
            string2 = this.getApiKeyFromStrings(context);
        }
        string = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string = this.getApiKeyFromFirebaseAppId(context);
        }
        if (TextUtils.isEmpty((CharSequence)string)) {
            this.logErrorOrThrowException(context);
        }
        return string;
    }

    protected void logErrorOrThrowException(Context context) {
        if (!Fabric.isDebuggable() && !CommonUtils.isAppDebuggable(context)) {
            Fabric.getLogger().e("Fabric", this.buildApiKeyInstructions());
            return;
        }
        throw new IllegalArgumentException(this.buildApiKeyInstructions());
    }
}
