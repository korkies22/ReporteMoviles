/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public interface CachedSettingsIo {
    public JSONObject readCachedSettings();

    public void writeCachedSettings(long var1, JSONObject var3);
}
