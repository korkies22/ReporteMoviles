/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;

public static interface Settings.SettingsAccess<T> {
    public T usingSettings(SettingsData var1);
}
