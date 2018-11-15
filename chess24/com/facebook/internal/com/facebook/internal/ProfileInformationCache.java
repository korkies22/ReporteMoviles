/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.internal;

import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class ProfileInformationCache {
    private static final ConcurrentHashMap<String, JSONObject> infoCache = new ConcurrentHashMap();

    ProfileInformationCache() {
    }

    public static JSONObject getProfileInformation(String string) {
        return infoCache.get(string);
    }

    public static void putProfileInformation(String string, JSONObject jSONObject) {
        infoCache.put(string, jSONObject);
    }
}
