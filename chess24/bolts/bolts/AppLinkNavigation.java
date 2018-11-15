/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.util.SparseArray
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package bolts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import bolts.AppLink;
import bolts.AppLinkResolver;
import bolts.Continuation;
import bolts.MeasurementEvent;
import bolts.Task;
import bolts.WebViewAppLinkResolver;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkNavigation {
    private static final String KEY_NAME_REFERER_APP_LINK = "referer_app_link";
    private static final String KEY_NAME_REFERER_APP_LINK_APP_NAME = "app_name";
    private static final String KEY_NAME_REFERER_APP_LINK_PACKAGE = "package";
    private static final String KEY_NAME_USER_AGENT = "user_agent";
    private static final String KEY_NAME_VERSION = "version";
    private static final String VERSION = "1.0";
    private static AppLinkResolver defaultResolver;
    private final AppLink appLink;
    private final Bundle appLinkData;
    private final Bundle extras;

    public AppLinkNavigation(AppLink appLink, Bundle bundle, Bundle bundle2) {
        if (appLink == null) {
            throw new IllegalArgumentException("appLink must not be null.");
        }
        Bundle bundle3 = bundle;
        if (bundle == null) {
            bundle3 = new Bundle();
        }
        bundle = bundle2;
        if (bundle2 == null) {
            bundle = new Bundle();
        }
        this.appLink = appLink;
        this.extras = bundle3;
        this.appLinkData = bundle;
    }

    private Bundle buildAppLinkDataForNavigation(Context object) {
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        if (object != null) {
            String string = object.getPackageName();
            if (string != null) {
                bundle2.putString(KEY_NAME_REFERER_APP_LINK_PACKAGE, string);
            }
            if ((string = object.getApplicationInfo()) != null && (object = object.getString(string.labelRes)) != null) {
                bundle2.putString(KEY_NAME_REFERER_APP_LINK_APP_NAME, (String)object);
            }
        }
        bundle.putAll(this.getAppLinkData());
        bundle.putString("target_url", this.getAppLink().getSourceUrl().toString());
        bundle.putString(KEY_NAME_VERSION, VERSION);
        bundle.putString(KEY_NAME_USER_AGENT, "Bolts Android 1.4.0");
        bundle.putBundle(KEY_NAME_REFERER_APP_LINK, bundle2);
        bundle.putBundle("extras", this.getExtras());
        return bundle;
    }

    public static AppLinkResolver getDefaultResolver() {
        return defaultResolver;
    }

    private JSONObject getJSONForBundle(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String string : bundle.keySet()) {
            jSONObject.put(string, this.getJSONValue(bundle.get(string)));
        }
        return jSONObject;
    }

    private Object getJSONValue(Object arrobject) throws JSONException {
        int n;
        if (arrobject instanceof Bundle) {
            return this.getJSONForBundle((Bundle)arrobject);
        }
        if (arrobject instanceof CharSequence) {
            return arrobject.toString();
        }
        if (arrobject instanceof List) {
            JSONArray jSONArray = new JSONArray();
            arrobject = ((List)arrobject).iterator();
            while (arrobject.hasNext()) {
                jSONArray.put(this.getJSONValue(arrobject.next()));
            }
            return jSONArray;
        }
        boolean bl = arrobject instanceof SparseArray;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        if (bl) {
            JSONArray jSONArray = new JSONArray();
            arrobject = arrobject;
            for (n = n10; n < arrobject.size(); ++n) {
                jSONArray.put(arrobject.keyAt(n), this.getJSONValue(arrobject.valueAt(n)));
            }
            return jSONArray;
        }
        if (arrobject instanceof Character) {
            return arrobject.toString();
        }
        if (arrobject instanceof Boolean) {
            return arrobject;
        }
        if (arrobject instanceof Number) {
            if (!(arrobject instanceof Double) && !(arrobject instanceof Float)) {
                return ((Number)arrobject).longValue();
            }
            return ((Number)arrobject).doubleValue();
        }
        if (arrobject instanceof boolean[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = arrobject;
            n2 = arrobject.length;
            for (n = 0; n < n2; ++n) {
                jSONArray.put(this.getJSONValue(arrobject[n]));
            }
            return jSONArray;
        }
        if (arrobject instanceof char[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (char[])arrobject;
            n3 = arrobject.length;
            for (n = n2; n < n3; ++n) {
                jSONArray.put(this.getJSONValue(Character.valueOf(arrobject[n])));
            }
            return jSONArray;
        }
        if (arrobject instanceof CharSequence[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (CharSequence[])arrobject;
            n2 = arrobject.length;
            for (n = n3; n < n2; ++n) {
                jSONArray.put(this.getJSONValue(arrobject[n]));
            }
            return jSONArray;
        }
        if (arrobject instanceof double[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (double[])arrobject;
            n2 = arrobject.length;
            for (n = n4; n < n2; ++n) {
                jSONArray.put(this.getJSONValue((double)arrobject[n]));
            }
            return jSONArray;
        }
        if (arrobject instanceof float[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (float[])arrobject;
            n2 = arrobject.length;
            for (n = n5; n < n2; ++n) {
                jSONArray.put(this.getJSONValue(Float.valueOf((float)arrobject[n])));
            }
            return jSONArray;
        }
        if (arrobject instanceof int[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (int[])arrobject;
            n2 = arrobject.length;
            for (n = n6; n < n2; ++n) {
                jSONArray.put(this.getJSONValue(arrobject[n]));
            }
            return jSONArray;
        }
        if (arrobject instanceof long[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (long[])arrobject;
            n2 = arrobject.length;
            for (n = n7; n < n2; ++n) {
                jSONArray.put(this.getJSONValue(Long.valueOf(arrobject[n])));
            }
            return jSONArray;
        }
        if (arrobject instanceof short[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (short[])arrobject;
            n2 = arrobject.length;
            for (n = n8; n < n2; ++n) {
                jSONArray.put(this.getJSONValue(arrobject[n]));
            }
            return jSONArray;
        }
        if (arrobject instanceof String[]) {
            JSONArray jSONArray = new JSONArray();
            arrobject = (String[])arrobject;
            n2 = arrobject.length;
            for (n = n9; n < n2; ++n) {
                jSONArray.put(this.getJSONValue(arrobject[n]));
            }
            return jSONArray;
        }
        return null;
    }

    private static AppLinkResolver getResolver(Context context) {
        if (AppLinkNavigation.getDefaultResolver() != null) {
            return AppLinkNavigation.getDefaultResolver();
        }
        return new WebViewAppLinkResolver(context);
    }

    public static NavigationResult navigate(Context context, AppLink appLink) {
        return new AppLinkNavigation(appLink, null, null).navigate(context);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, Uri uri) {
        return AppLinkNavigation.navigateInBackground(context, uri, AppLinkNavigation.getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(final Context context, Uri uri, AppLinkResolver appLinkResolver) {
        return appLinkResolver.getAppLinkFromUrlInBackground(uri).onSuccess(new Continuation<AppLink, NavigationResult>(){

            @Override
            public NavigationResult then(Task<AppLink> task) throws Exception {
                return AppLinkNavigation.navigate(context, task.getResult());
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String string) {
        return AppLinkNavigation.navigateInBackground(context, string, AppLinkNavigation.getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String string, AppLinkResolver appLinkResolver) {
        return AppLinkNavigation.navigateInBackground(context, Uri.parse((String)string), appLinkResolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL uRL) {
        return AppLinkNavigation.navigateInBackground(context, uRL, AppLinkNavigation.getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL uRL, AppLinkResolver appLinkResolver) {
        return AppLinkNavigation.navigateInBackground(context, Uri.parse((String)uRL.toString()), appLinkResolver);
    }

    private void sendAppLinkNavigateEventBroadcast(Context context, Intent intent, NavigationResult navigationResult, JSONException object) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (object != null) {
            hashMap.put("error", object.getLocalizedMessage());
        }
        object = navigationResult.isSucceeded() ? "1" : "0";
        hashMap.put("success", (String)object);
        hashMap.put("type", navigationResult.getCode());
        MeasurementEvent.sendBroadcastEvent(context, "al_nav_out", intent, hashMap);
    }

    public static void setDefaultResolver(AppLinkResolver appLinkResolver) {
        defaultResolver = appLinkResolver;
    }

    public AppLink getAppLink() {
        return this.appLink;
    }

    public Bundle getAppLinkData() {
        return this.appLinkData;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    public NavigationResult navigate(Context context) {
        Object object;
        Bundle bundle;
        block9 : {
            Object object2;
            block10 : {
                block8 : {
                    block7 : {
                        object2 = context.getPackageManager();
                        bundle = this.buildAppLinkDataForNavigation(context);
                        for (AppLink.Target target : this.getAppLink().getTargets()) {
                            object = new Intent("android.intent.action.VIEW");
                            if (target.getUrl() != null) {
                                object.setData(target.getUrl());
                            } else {
                                object.setData(this.appLink.getSourceUrl());
                            }
                            object.setPackage(target.getPackageName());
                            if (target.getClassName() != null) {
                                object.setClassName(target.getPackageName(), target.getClassName());
                            }
                            object.putExtra("al_applink_data", bundle);
                            if (object2.resolveActivity(object, 65536) == null) continue;
                            break block7;
                        }
                        object = null;
                    }
                    object2 = NavigationResult.FAILED;
                    if (object == null) break block8;
                    object2 = NavigationResult.APP;
                    bundle = object;
                    object = object2;
                    break block9;
                }
                Uri uri = this.getAppLink().getWebUrl();
                if (uri == null) break block10;
                try {
                    bundle = this.getJSONForBundle(bundle);
                }
                catch (JSONException jSONException) {
                    this.sendAppLinkNavigateEventBroadcast(context, (Intent)object, NavigationResult.FAILED, jSONException);
                    throw new RuntimeException((Throwable)jSONException);
                }
                bundle = new Intent("android.intent.action.VIEW", uri.buildUpon().appendQueryParameter("al_applink_data", bundle.toString()).build());
                object = NavigationResult.WEB;
                break block9;
            }
            bundle = null;
            object = object2;
        }
        this.sendAppLinkNavigateEventBroadcast(context, (Intent)bundle, (NavigationResult)((Object)object), null);
        if (bundle != null) {
            context.startActivity((Intent)bundle);
        }
        return object;
    }

    public static enum NavigationResult {
        FAILED("failed", false),
        WEB("web", true),
        APP("app", true);
        
        private String code;
        private boolean succeeded;

        private NavigationResult(String string2, boolean bl) {
            this.code = string2;
            this.succeeded = bl;
        }

        public String getCode() {
            return this.code;
        }

        public boolean isSucceeded() {
            return this.succeeded;
        }
    }

}
