/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.crashlytics.android.beta;

import com.crashlytics.android.beta.CheckForUpdatesResponse;
import java.io.IOException;
import org.json.JSONObject;

class CheckForUpdatesResponseTransform {
    static final String BUILD_VERSION = "build_version";
    static final String DISPLAY_VERSION = "display_version";
    static final String IDENTIFIER = "identifier";
    static final String INSTANCE_IDENTIFIER = "instance_identifier";
    static final String URL = "url";
    static final String VERSION_STRING = "version_string";

    CheckForUpdatesResponseTransform() {
    }

    public CheckForUpdatesResponse fromJson(JSONObject jSONObject) throws IOException {
        if (jSONObject == null) {
            return null;
        }
        String string = jSONObject.optString(URL, null);
        String string2 = jSONObject.optString(VERSION_STRING, null);
        String string3 = jSONObject.optString(BUILD_VERSION, null);
        return new CheckForUpdatesResponse(string, string2, jSONObject.optString(DISPLAY_VERSION, null), string3, jSONObject.optString(IDENTIFIER, null), jSONObject.optString(INSTANCE_IDENTIFIER, null));
    }
}
