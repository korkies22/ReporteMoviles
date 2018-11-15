/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.content.SharedPreferences;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

final class ProfileCache {
    static final String CACHED_PROFILE_KEY = "com.facebook.ProfileManager.CachedProfile";
    static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    private final SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.AccessTokenManager.SharedPreferences", 0);

    ProfileCache() {
    }

    void clear() {
        this.sharedPreferences.edit().remove(CACHED_PROFILE_KEY).apply();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Profile load() {
        String object = this.sharedPreferences.getString(CACHED_PROFILE_KEY, null);
        if (object == null) return null;
        try {
            return new Profile(new JSONObject(object));
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    void save(Profile profile) {
        Validate.notNull(profile, "profile");
        profile = profile.toJSONObject();
        if (profile != null) {
            this.sharedPreferences.edit().putString(CACHED_PROFILE_KEY, profile.toString()).apply();
        }
    }
}
