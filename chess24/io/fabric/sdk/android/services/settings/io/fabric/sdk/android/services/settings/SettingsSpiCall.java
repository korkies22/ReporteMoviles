/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.settings.SettingsRequest;
import org.json.JSONObject;

public interface SettingsSpiCall {
    public JSONObject invoke(SettingsRequest var1);
}
