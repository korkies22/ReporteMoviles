/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.facebook;

import android.util.Log;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.Utility;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

class AccessTokenManager
implements GraphRequest.Callback {
    final /* synthetic */ Set val$declinedPermissions;
    final /* synthetic */ Set val$permissions;
    final /* synthetic */ AtomicBoolean val$permissionsCallSucceeded;

    AccessTokenManager(AtomicBoolean atomicBoolean, Set set, Set set2) {
        this.val$permissionsCallSucceeded = atomicBoolean;
        this.val$permissions = set;
        this.val$declinedPermissions = set2;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        if ((graphResponse = graphResponse.getJSONObject()) == null) {
            return;
        }
        if ((graphResponse = graphResponse.optJSONArray("data")) == null) {
            return;
        }
        this.val$permissionsCallSucceeded.set(true);
        for (int i = 0; i < graphResponse.length(); ++i) {
            Object object = graphResponse.optJSONObject(i);
            if (object == null) continue;
            CharSequence charSequence = object.optString("permission");
            object = object.optString("status");
            if (Utility.isNullOrEmpty((String)charSequence) || Utility.isNullOrEmpty((String)object)) continue;
            if ((object = object.toLowerCase(Locale.US)).equals("granted")) {
                this.val$permissions.add(charSequence);
                continue;
            }
            if (object.equals("declined")) {
                this.val$declinedPermissions.add(charSequence);
                continue;
            }
            charSequence = new StringBuilder();
            charSequence.append("Unexpected status: ");
            charSequence.append((String)object);
            Log.w((String)com.facebook.AccessTokenManager.TAG, (String)charSequence.toString());
        }
    }
}
