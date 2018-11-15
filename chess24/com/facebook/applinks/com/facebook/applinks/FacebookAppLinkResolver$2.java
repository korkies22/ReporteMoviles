/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.applinks;

import android.net.Uri;
import bolts.AppLink;
import bolts.Task;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class FacebookAppLinkResolver
implements GraphRequest.Callback {
    final /* synthetic */ Map val$appLinkResults;
    final /* synthetic */ Task.TaskCompletionSource val$taskCompletionSource;
    final /* synthetic */ HashSet val$urisToRequest;

    FacebookAppLinkResolver(Task.TaskCompletionSource taskCompletionSource, Map map, HashSet hashSet) {
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
            Object object2;
            int n;
            Object object3;
            int n2;
            ArrayList<AppLink.Target> arrayList;
            if (!object.hasNext()) {
                this.val$taskCompletionSource.setResult(this.val$appLinkResults);
                return;
            }
            Uri uri = (Uri)object.next();
            if (!graphResponse.has(uri.toString())) continue;
            try {
                object2 = graphResponse.getJSONObject(uri.toString()).getJSONObject(com.facebook.applinks.FacebookAppLinkResolver.APP_LINK_KEY);
                object3 = object2.getJSONArray(com.facebook.applinks.FacebookAppLinkResolver.APP_LINK_ANDROID_TARGET_KEY);
                n2 = object3.length();
                arrayList = new ArrayList<AppLink.Target>(n2);
                n = 0;
            }
            catch (JSONException jSONException) {
                continue;
            }
            do {
                if (n < n2) {
                    AppLink.Target target = com.facebook.applinks.FacebookAppLinkResolver.getAndroidTargetFromJson(object3.getJSONObject(n));
                    if (target != null) {
                        arrayList.add(target);
                    }
                } else {
                    object3 = new AppLink(uri, arrayList, com.facebook.applinks.FacebookAppLinkResolver.getWebFallbackUriFromJson(uri, object2));
                    this.val$appLinkResults.put(uri, object3);
                    object2 = FacebookAppLinkResolver.this.cachedAppLinks;
                    synchronized (object2) {
                        FacebookAppLinkResolver.this.cachedAppLinks.put(uri, object3);
                        continue block5;
                    }
                }
                ++n;
            } while (true);
            break;
        } while (true);
    }
}
