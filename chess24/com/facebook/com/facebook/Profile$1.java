/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONObject
 */
package com.facebook;

import android.net.Uri;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import org.json.JSONObject;

static final class Profile
implements Utility.GraphMeRequestWithCacheCallback {
    Profile() {
    }

    @Override
    public void onFailure(FacebookException facebookException) {
    }

    @Override
    public void onSuccess(JSONObject object) {
        String string = object.optString(com.facebook.Profile.ID_KEY);
        if (string == null) {
            return;
        }
        String string2 = object.optString("link");
        String string3 = object.optString(com.facebook.Profile.FIRST_NAME_KEY);
        String string4 = object.optString(com.facebook.Profile.MIDDLE_NAME_KEY);
        String string5 = object.optString(com.facebook.Profile.LAST_NAME_KEY);
        String string6 = object.optString(com.facebook.Profile.NAME_KEY);
        object = string2 != null ? Uri.parse((String)string2) : null;
        com.facebook.Profile.setCurrentProfile(new com.facebook.Profile(string, string3, string4, string5, string6, (Uri)object));
    }
}
