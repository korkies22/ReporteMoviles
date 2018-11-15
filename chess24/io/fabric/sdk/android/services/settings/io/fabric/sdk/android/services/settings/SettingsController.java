/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.settings.SettingsCacheBehavior;
import io.fabric.sdk.android.services.settings.SettingsData;

public interface SettingsController {
    public SettingsData loadSettingsData();

    public SettingsData loadSettingsData(SettingsCacheBehavior var1);
}
