/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.CachedSettingsIo;
import io.fabric.sdk.android.services.settings.SettingsCacheBehavior;
import io.fabric.sdk.android.services.settings.SettingsController;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.SettingsJsonTransform;
import io.fabric.sdk.android.services.settings.SettingsRequest;
import io.fabric.sdk.android.services.settings.SettingsSpiCall;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsController
implements SettingsController {
    private static final String LOAD_ERROR_MESSAGE = "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.";
    private static final String PREFS_BUILD_INSTANCE_IDENTIFIER = "existing_instance_identifier";
    private final CachedSettingsIo cachedSettingsIo;
    private final CurrentTimeProvider currentTimeProvider;
    private final Kit kit;
    private final PreferenceStore preferenceStore;
    private final SettingsJsonTransform settingsJsonTransform;
    private final SettingsRequest settingsRequest;
    private final SettingsSpiCall settingsSpiCall;

    public DefaultSettingsController(Kit kit, SettingsRequest settingsRequest, CurrentTimeProvider currentTimeProvider, SettingsJsonTransform settingsJsonTransform, CachedSettingsIo cachedSettingsIo, SettingsSpiCall settingsSpiCall) {
        this.kit = kit;
        this.settingsRequest = settingsRequest;
        this.currentTimeProvider = currentTimeProvider;
        this.settingsJsonTransform = settingsJsonTransform;
        this.cachedSettingsIo = cachedSettingsIo;
        this.settingsSpiCall = settingsSpiCall;
        this.preferenceStore = new PreferenceStoreImpl(this.kit);
    }

    private SettingsData getCachedSettingsData(SettingsCacheBehavior object) {
        Object object2;
        block8 : {
            block12 : {
                Object var5_4;
                block9 : {
                    block10 : {
                        block11 : {
                            object2 = null;
                            var5_4 = null;
                            if (SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(object)) break block8;
                            JSONObject jSONObject = this.cachedSettingsIo.readCachedSettings();
                            if (jSONObject == null) break block9;
                            object2 = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, jSONObject);
                            if (object2 == null) break block10;
                            this.logSettings(jSONObject, "Loaded cached settings: ");
                            long l = this.currentTimeProvider.getCurrentTimeMillis();
                            if (SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(object) || !object2.isExpired(l)) break block11;
                            Fabric.getLogger().d("Fabric", "Cached settings have expired.");
                            return null;
                        }
                        try {
                            Fabric.getLogger().d("Fabric", "Returning cached settings.");
                            return object2;
                        }
                        catch (Exception exception) {
                            object = object2;
                            object2 = exception;
                        }
                        break block12;
                    }
                    Fabric.getLogger().e("Fabric", "Failed to transform cached settings data.", null);
                    return null;
                }
                try {
                    Fabric.getLogger().d("Fabric", "No cached settings data found.");
                    return null;
                }
                catch (Exception exception) {
                    object = var5_4;
                }
            }
            Fabric.getLogger().e("Fabric", "Failed to get cached settings", (Throwable)object2);
            object2 = object;
        }
        return object2;
    }

    private void logSettings(JSONObject jSONObject, String string) throws JSONException {
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(jSONObject.toString());
        logger.d("Fabric", stringBuilder.toString());
    }

    boolean buildInstanceIdentifierChanged() {
        return this.getStoredBuildInstanceIdentifier().equals(this.getBuildInstanceIdentifierFromContext()) ^ true;
    }

    String getBuildInstanceIdentifierFromContext() {
        return CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(this.kit.getContext()));
    }

    String getStoredBuildInstanceIdentifier() {
        return this.preferenceStore.get().getString(PREFS_BUILD_INSTANCE_IDENTIFIER, "");
    }

    @Override
    public SettingsData loadSettingsData() {
        return this.loadSettingsData(SettingsCacheBehavior.USE_CACHE);
    }

    @Override
    public SettingsData loadSettingsData(SettingsCacheBehavior object) {
        block14 : {
            Object object2;
            block13 : {
                block12 : {
                    JSONObject jSONObject;
                    Object object3;
                    block11 : {
                        JSONObject jSONObject2 = null;
                        object3 = jSONObject = null;
                        object2 = jSONObject2;
                        if (Fabric.isDebuggable()) break block11;
                        object3 = jSONObject;
                        object2 = jSONObject2;
                        if (this.buildInstanceIdentifierChanged()) break block11;
                        object2 = jSONObject2;
                        try {
                            object3 = this.getCachedSettingsData((SettingsCacheBehavior)((Object)object));
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    object2 = object3;
                    if (object3 == null) {
                        object2 = object3;
                        jSONObject = this.settingsSpiCall.invoke(this.settingsRequest);
                        object2 = object3;
                        if (jSONObject == null) break block12;
                        object2 = object3;
                        object = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, jSONObject);
                        try {
                            this.cachedSettingsIo.writeCachedSettings(object.expiresAtMillis, jSONObject);
                            this.logSettings(jSONObject, "Loaded settings: ");
                            this.setStoredBuildInstanceIdentifier(this.getBuildInstanceIdentifierFromContext());
                            object2 = object;
                        }
                        catch (Exception exception) {
                            object2 = object;
                            object = exception;
                            break block13;
                        }
                    }
                }
                object = object2;
                if (object2 == null) {
                    object = this.getCachedSettingsData(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
                    return object;
                }
                break block14;
            }
            Fabric.getLogger().e("Fabric", LOAD_ERROR_MESSAGE, (Throwable)object);
            object = object2;
        }
        return object;
    }

    @SuppressLint(value={"CommitPrefEdits"})
    boolean setStoredBuildInstanceIdentifier(String string) {
        SharedPreferences.Editor editor = this.preferenceStore.edit();
        editor.putString(PREFS_BUILD_INSTANCE_IDENTIFIER, string);
        return this.preferenceStore.save(editor);
    }
}
