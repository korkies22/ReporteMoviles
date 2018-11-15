/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.applinks;

import android.net.Uri;
import android.os.Bundle;
import bolts.AppLink;
import bolts.AppLinkResolver;
import bolts.Continuation;
import bolts.Task;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookAppLinkResolver
implements AppLinkResolver {
    private static final String APP_LINK_ANDROID_TARGET_KEY = "android";
    private static final String APP_LINK_KEY = "app_links";
    private static final String APP_LINK_TARGET_APP_NAME_KEY = "app_name";
    private static final String APP_LINK_TARGET_CLASS_KEY = "class";
    private static final String APP_LINK_TARGET_PACKAGE_KEY = "package";
    private static final String APP_LINK_TARGET_SHOULD_FALLBACK_KEY = "should_fallback";
    private static final String APP_LINK_TARGET_URL_KEY = "url";
    private static final String APP_LINK_WEB_TARGET_KEY = "web";
    private final HashMap<Uri, AppLink> cachedAppLinks = new HashMap();

    private static AppLink.Target getAndroidTargetFromJson(JSONObject jSONObject) {
        Object var1_1 = null;
        String string = FacebookAppLinkResolver.tryGetStringFromJson(jSONObject, APP_LINK_TARGET_PACKAGE_KEY, null);
        if (string == null) {
            return null;
        }
        String string2 = FacebookAppLinkResolver.tryGetStringFromJson(jSONObject, APP_LINK_TARGET_CLASS_KEY, null);
        String string3 = FacebookAppLinkResolver.tryGetStringFromJson(jSONObject, APP_LINK_TARGET_APP_NAME_KEY, null);
        String string4 = FacebookAppLinkResolver.tryGetStringFromJson(jSONObject, APP_LINK_TARGET_URL_KEY, null);
        jSONObject = var1_1;
        if (string4 != null) {
            jSONObject = Uri.parse((String)string4);
        }
        return new AppLink.Target(string, string2, (Uri)jSONObject, string3);
    }

    private static Uri getWebFallbackUriFromJson(Uri uri, JSONObject object) {
        block7 : {
            block6 : {
                block5 : {
                    try {
                        object = object.getJSONObject(APP_LINK_WEB_TARGET_KEY);
                        if (FacebookAppLinkResolver.tryGetBooleanFromJson(object, APP_LINK_TARGET_SHOULD_FALLBACK_KEY, true)) break block5;
                        return null;
                    }
                    catch (JSONException jSONException) {
                        return uri;
                    }
                }
                object = FacebookAppLinkResolver.tryGetStringFromJson(object, APP_LINK_TARGET_URL_KEY, null);
                if (object == null) break block6;
                object = Uri.parse((String)object);
                break block7;
            }
            object = null;
        }
        if (object != null) {
            uri = object;
        }
        return uri;
    }

    private static boolean tryGetBooleanFromJson(JSONObject jSONObject, String string, boolean bl) {
        try {
            boolean bl2 = jSONObject.getBoolean(string);
            return bl2;
        }
        catch (JSONException jSONException) {
            return bl;
        }
    }

    private static String tryGetStringFromJson(JSONObject object, String string, String string2) {
        try {
            object = object.getString(string);
            return object;
        }
        catch (JSONException jSONException) {
            return string2;
        }
    }

    @Override
    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri uri) {
        ArrayList<Uri> arrayList = new ArrayList<Uri>();
        arrayList.add(uri);
        return this.getAppLinkFromUrlsInBackground(arrayList).onSuccess(new Continuation<Map<Uri, AppLink>, AppLink>(){

            @Override
            public AppLink then(Task<Map<Uri, AppLink>> task) throws Exception {
                return task.getResult().get((Object)uri);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public Task<Map<Uri, AppLink>> getAppLinkFromUrlsInBackground(List<Uri> hashMap) {
        HashMap<Uri, AppLink> hashMap2 = new HashMap<Uri, AppLink>();
        HashSet<Uri> hashSet = new HashSet<Uri>();
        StringBuilder stringBuilder = new StringBuilder();
        Bundle bundle = hashMap.iterator();
        while (bundle.hasNext()) {
            Uri uri = (Uri)bundle.next();
            hashMap = this.cachedAppLinks;
            // MONITORENTER : hashMap
            AppLink appLink = this.cachedAppLinks.get((Object)uri);
            // MONITOREXIT : hashMap
            if (appLink != null) {
                hashMap2.put(uri, appLink);
                continue;
            }
            if (!hashSet.isEmpty()) {
                stringBuilder.append(',');
            }
            stringBuilder.append(uri.toString());
            hashSet.add(uri);
        }
        if (hashSet.isEmpty()) {
            return Task.forResult(hashMap2);
        }
        hashMap = Task.create();
        bundle = new Bundle();
        bundle.putString("ids", stringBuilder.toString());
        bundle.putString("fields", String.format("%s.fields(%s,%s)", APP_LINK_KEY, APP_LINK_ANDROID_TARGET_KEY, APP_LINK_WEB_TARGET_KEY));
        new GraphRequest(AccessToken.getCurrentAccessToken(), "", bundle, null, new GraphRequest.Callback((Task.TaskCompletionSource)((Object)hashMap), hashMap2, hashSet){
            final /* synthetic */ Map val$appLinkResults;
            final /* synthetic */ Task.TaskCompletionSource val$taskCompletionSource;
            final /* synthetic */ HashSet val$urisToRequest;
            {
                this.val$taskCompletionSource = taskCompletionSource;
                this.val$appLinkResults = map;
                this.val$urisToRequest = hashSet;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Object object = graphResponse.getError();
                if (object != null) {
                    this.val$taskCompletionSource.setError(object.getException());
                    return;
                }
                if ((graphResponse = graphResponse.getJSONObject()) == null) {
                    this.val$taskCompletionSource.setResult(this.val$appLinkResults);
                    return;
                }
                object = this.val$urisToRequest.iterator();
                block5 : do {
                    int n;
                    Object object2;
                    int n2;
                    Object object3;
                    ArrayList<AppLink.Target> arrayList;
                    if (!object.hasNext()) {
                        this.val$taskCompletionSource.setResult(this.val$appLinkResults);
                        return;
                    }
                    Uri uri = (Uri)object.next();
                    if (!graphResponse.has(uri.toString())) continue;
                    try {
                        object2 = graphResponse.getJSONObject(uri.toString()).getJSONObject(FacebookAppLinkResolver.APP_LINK_KEY);
                        object3 = object2.getJSONArray(FacebookAppLinkResolver.APP_LINK_ANDROID_TARGET_KEY);
                        n = object3.length();
                        arrayList = new ArrayList<AppLink.Target>(n);
                        n2 = 0;
                    }
                    catch (JSONException jSONException) {
                        continue;
                    }
                    do {
                        if (n2 < n) {
                            AppLink.Target target = FacebookAppLinkResolver.getAndroidTargetFromJson(object3.getJSONObject(n2));
                            if (target != null) {
                                arrayList.add(target);
                            }
                        } else {
                            object3 = new AppLink(uri, arrayList, FacebookAppLinkResolver.getWebFallbackUriFromJson(uri, object2));
                            this.val$appLinkResults.put(uri, object3);
                            object2 = FacebookAppLinkResolver.this.cachedAppLinks;
                            synchronized (object2) {
                                FacebookAppLinkResolver.this.cachedAppLinks.put(uri, object3);
                                continue block5;
                            }
                        }
                        ++n2;
                    } while (true);
                    break;
                } while (true);
            }
        }).executeAsync();
        return hashMap.getTask();
    }

}
