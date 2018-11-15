/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.applinks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkData {
    private static final String APPLINK_BRIDGE_ARGS_KEY = "bridge_args";
    private static final String APPLINK_METHOD_ARGS_KEY = "method_args";
    private static final String APPLINK_VERSION_KEY = "version";
    public static final String ARGUMENTS_EXTRAS_KEY = "extras";
    public static final String ARGUMENTS_NATIVE_CLASS_KEY = "com.facebook.platform.APPLINK_NATIVE_CLASS";
    public static final String ARGUMENTS_NATIVE_URL = "com.facebook.platform.APPLINK_NATIVE_URL";
    public static final String ARGUMENTS_REFERER_DATA_KEY = "referer_data";
    public static final String ARGUMENTS_TAPTIME_KEY = "com.facebook.platform.APPLINK_TAP_TIME_UTC";
    private static final String BRIDGE_ARGS_METHOD_KEY = "method";
    private static final String BUNDLE_AL_APPLINK_DATA_KEY = "al_applink_data";
    static final String BUNDLE_APPLINK_ARGS_KEY = "com.facebook.platform.APPLINK_ARGS";
    private static final String DEFERRED_APP_LINK_ARGS_FIELD = "applink_args";
    private static final String DEFERRED_APP_LINK_CLASS_FIELD = "applink_class";
    private static final String DEFERRED_APP_LINK_CLICK_TIME_FIELD = "click_time";
    private static final String DEFERRED_APP_LINK_EVENT = "DEFERRED_APP_LINK";
    private static final String DEFERRED_APP_LINK_PATH = "%s/activities";
    private static final String DEFERRED_APP_LINK_URL_FIELD = "applink_url";
    private static final String EXTRAS_DEEPLINK_CONTEXT_KEY = "deeplink_context";
    private static final String METHOD_ARGS_REF_KEY = "ref";
    private static final String METHOD_ARGS_TARGET_URL_KEY = "target_url";
    private static final String PROMOTION_CODE_KEY = "promo_code";
    private static final String REFERER_DATA_REF_KEY = "fb_ref";
    private static final String TAG = AppLinkData.class.getCanonicalName();
    private Bundle argumentBundle;
    private JSONObject arguments;
    private String promotionCode;
    private String ref;
    private Uri targetUri;

    private AppLinkData() {
    }

    public static AppLinkData createFromActivity(Activity object) {
        Validate.notNull(object, "activity");
        Intent intent = object.getIntent();
        if (intent == null) {
            return null;
        }
        AppLinkData appLinkData = AppLinkData.createFromAlApplinkData(intent);
        object = appLinkData;
        if (appLinkData == null) {
            object = AppLinkData.createFromJson(intent.getStringExtra(BUNDLE_APPLINK_ARGS_KEY));
        }
        appLinkData = object;
        if (object == null) {
            appLinkData = AppLinkData.createFromUri(intent.getData());
        }
        return appLinkData;
    }

    public static AppLinkData createFromAlApplinkData(Intent object) {
        if (object == null) {
            return null;
        }
        Bundle bundle = object.getBundleExtra(BUNDLE_AL_APPLINK_DATA_KEY);
        if (bundle == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.targetUri = object.getData();
        if (appLinkData.targetUri == null && (object = bundle.getString(METHOD_ARGS_TARGET_URL_KEY)) != null) {
            appLinkData.targetUri = Uri.parse((String)object);
        }
        appLinkData.argumentBundle = bundle;
        appLinkData.arguments = null;
        object = bundle.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        if (object != null) {
            appLinkData.ref = object.getString(REFERER_DATA_REF_KEY);
        }
        if ((object = bundle.getBundle(ARGUMENTS_EXTRAS_KEY)) != null && (object = object.getString(EXTRAS_DEEPLINK_CONTEXT_KEY)) != null) {
            try {
                object = new JSONObject((String)object);
                if (object.has(PROMOTION_CODE_KEY)) {
                    appLinkData.promotionCode = object.getString(PROMOTION_CODE_KEY);
                    return appLinkData;
                }
            }
            catch (JSONException jSONException) {
                Log.d((String)TAG, (String)"Unable to parse deeplink_context JSON", (Throwable)jSONException);
            }
        }
        return appLinkData;
    }

    private static AppLinkData createFromJson(String object) {
        if (object == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject((String)object);
            object = jSONObject.getString(APPLINK_VERSION_KEY);
            if (jSONObject.getJSONObject(APPLINK_BRIDGE_ARGS_KEY).getString(BRIDGE_ARGS_METHOD_KEY).equals("applink") && object.equals("2")) {
                object = new AppLinkData();
                object.arguments = jSONObject.getJSONObject(APPLINK_METHOD_ARGS_KEY);
                if (object.arguments.has(METHOD_ARGS_REF_KEY)) {
                    object.ref = object.arguments.getString(METHOD_ARGS_REF_KEY);
                } else if (object.arguments.has(ARGUMENTS_REFERER_DATA_KEY) && (jSONObject = object.arguments.getJSONObject(ARGUMENTS_REFERER_DATA_KEY)).has(REFERER_DATA_REF_KEY)) {
                    object.ref = jSONObject.getString(REFERER_DATA_REF_KEY);
                }
                if (object.arguments.has(METHOD_ARGS_TARGET_URL_KEY)) {
                    object.targetUri = Uri.parse((String)object.arguments.getString(METHOD_ARGS_TARGET_URL_KEY));
                }
                if (object.arguments.has(ARGUMENTS_EXTRAS_KEY) && (jSONObject = object.arguments.getJSONObject(ARGUMENTS_EXTRAS_KEY)).has(EXTRAS_DEEPLINK_CONTEXT_KEY) && (jSONObject = jSONObject.getJSONObject(EXTRAS_DEEPLINK_CONTEXT_KEY)).has(PROMOTION_CODE_KEY)) {
                    object.promotionCode = jSONObject.getString(PROMOTION_CODE_KEY);
                }
                object.argumentBundle = AppLinkData.toBundle(object.arguments);
                return object;
            }
        }
        catch (FacebookException facebookException) {
            Log.d((String)TAG, (String)"Unable to parse AppLink JSON", (Throwable)facebookException);
            return null;
        }
        catch (JSONException jSONException) {
            Log.d((String)TAG, (String)"Unable to parse AppLink JSON", (Throwable)jSONException);
        }
        return null;
    }

    private static AppLinkData createFromUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        AppLinkData appLinkData = new AppLinkData();
        appLinkData.targetUri = uri;
        return appLinkData;
    }

    public static void fetchDeferredAppLinkData(Context context, CompletionHandler completionHandler) {
        AppLinkData.fetchDeferredAppLinkData(context, null, completionHandler);
    }

    public static void fetchDeferredAppLinkData(final Context context, String string, final CompletionHandler completionHandler) {
        Validate.notNull((Object)context, "context");
        Validate.notNull(completionHandler, "completionHandler");
        final String string2 = string;
        if (string == null) {
            string2 = Utility.getMetadataApplicationId(context);
        }
        Validate.notNull(string2, "applicationId");
        context = context.getApplicationContext();
        FacebookSdk.getExecutor().execute(new Runnable(){

            @Override
            public void run() {
                AppLinkData.fetchDeferredAppLinkFromServer(context, string2, completionHandler);
            }
        });
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void fetchDeferredAppLinkFromServer(Context var0, String var1_3, CompletionHandler var2_8) {
        block21 : {
            block23 : {
                block22 : {
                    var6_16 = new JSONObject();
                    var6_16.put("event", (Object)"DEFERRED_APP_LINK");
                    Utility.setAppEventAttributionParameters(var6_16, AttributionIdentifiers.getAttributionIdentifiers(var0 /* !! */ ), AppEventsLogger.getAnonymousAppDeviceGUID(var0 /* !! */ ), FacebookSdk.getLimitEventAndDataUsage(var0 /* !! */ ));
                    Utility.setAppEventExtendedDeviceInfoParameters(var6_16, FacebookSdk.getApplicationContext());
                    var6_16.put("application_package_name", (Object)var0 /* !! */ .getPackageName());
                    {
                        catch (JSONException var0_7) {
                            throw new FacebookException("An error occurred while preparing deferred app link", (Throwable)var0_7);
                        }
                    }
                    var0_1 = String.format("%s/activities", new Object[]{var1_10});
                    var5_18 = null;
                    var1_10 = null;
                    var7_19 = GraphRequest.newPostRequest(null, var0_1, var6_16, null).executeAndWait().getJSONObject();
                    var0_2 = var5_18;
                    if (var7_19 == null) break block21;
                    var8_21 = var7_19.optString("applink_args");
                    var3_22 = var7_19.optLong("click_time", -1L);
                    var6_17 = var7_19.optString("applink_class");
                    var7_20 = var7_19.optString("applink_url");
                    var0_3 = var5_18;
                    if (TextUtils.isEmpty((CharSequence)var8_21)) break block21;
                    var0_4 = AppLinkData.createFromJson(var8_21);
                    ** if (var3_22 == -1L) goto lbl30
lbl-1000: // 1 sources:
                    {
                        if (var0_4.arguments != null) {
                            var0_4.arguments.put("com.facebook.platform.APPLINK_TAP_TIME_UTC", var3_22);
                        }
                        if (var0_4.argumentBundle != null) {
                            var0_4.argumentBundle.putString("com.facebook.platform.APPLINK_TAP_TIME_UTC", Long.toString(var3_22));
                        }
                    }
lbl30: // 4 sources:
                    ** GOTO lbl36
                    {
                        catch (Exception var0_8) {
                            var0_9 = var1_10;
                        }
                        break block22;
                        catch (JSONException var1_11) {}
                        Log.d((String)AppLinkData.TAG, (String)"Unable to put tap time in AppLinkData.arguments");
lbl36: // 2 sources:
                        if (var6_17 != null) {
                            if (var0_4.arguments != null) {
                                var0_4.arguments.put("com.facebook.platform.APPLINK_NATIVE_CLASS", (Object)var6_17);
                            }
                            if (var0_4.argumentBundle != null) {
                                var0_4.argumentBundle.putString("com.facebook.platform.APPLINK_NATIVE_CLASS", var6_17);
                            }
                        }
                        break block23;
                    }
                    catch (Exception var1_12) {}
                }
                Utility.logd(AppLinkData.TAG, "Unable to fetch deferred applink from server");
                break block21;
                catch (JSONException var1_13) {}
                Log.d((String)AppLinkData.TAG, (String)"Unable to put tap time in AppLinkData.arguments");
            }
            if (var7_20 != null) {
                try {
                    if (var0_4.arguments != null) {
                        var0_4.arguments.put("com.facebook.platform.APPLINK_NATIVE_URL", (Object)var7_20);
                    }
                    if (var0_4.argumentBundle != null) {
                        var0_4.argumentBundle.putString("com.facebook.platform.APPLINK_NATIVE_URL", var7_20);
                    }
                    break block21;
                }
                catch (JSONException var1_14) {}
                Log.d((String)AppLinkData.TAG, (String)"Unable to put tap time in AppLinkData.arguments");
            }
        }
        var2_15.onDeferredAppLinkDataFetched((AppLinkData)var0_6);
    }

    private static Bundle toBundle(JSONObject jSONObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator iterator = jSONObject.keys();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            Object object = jSONObject.get(string);
            if (object instanceof JSONObject) {
                bundle.putBundle(string, AppLinkData.toBundle((JSONObject)object));
                continue;
            }
            if (object instanceof JSONArray) {
                int n;
                object = (JSONArray)object;
                int n2 = object.length();
                int n3 = 0;
                if (n2 == 0) {
                    bundle.putStringArray(string, new String[0]);
                    continue;
                }
                Object[] arrobject = object.get(0);
                if (arrobject instanceof JSONObject) {
                    arrobject = new Bundle[object.length()];
                    for (n = 0; n < object.length(); ++n) {
                        arrobject[n] = AppLinkData.toBundle(object.getJSONObject(n));
                    }
                    bundle.putParcelableArray(string, (Parcelable[])arrobject);
                    continue;
                }
                if (arrobject instanceof JSONArray) {
                    throw new FacebookException("Nested arrays are not supported.");
                }
                arrobject = new String[object.length()];
                for (n = n3; n < object.length(); ++n) {
                    arrobject[n] = object.get(n).toString();
                }
                bundle.putStringArray(string, (String[])arrobject);
                continue;
            }
            bundle.putString(string, object.toString());
        }
        return bundle;
    }

    public Bundle getArgumentBundle() {
        return this.argumentBundle;
    }

    public String getPromotionCode() {
        return this.promotionCode;
    }

    public String getRef() {
        return this.ref;
    }

    public Bundle getRefererData() {
        if (this.argumentBundle != null) {
            return this.argumentBundle.getBundle(ARGUMENTS_REFERER_DATA_KEY);
        }
        return null;
    }

    public Uri getTargetUri() {
        return this.targetUri;
    }

    public static interface CompletionHandler {
        public void onDeferredAppLinkDataFetched(AppLinkData var1);
    }

}
