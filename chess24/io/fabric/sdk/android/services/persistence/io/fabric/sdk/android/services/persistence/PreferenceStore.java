/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package io.fabric.sdk.android.services.persistence;

import android.content.SharedPreferences;

public interface PreferenceStore {
    public SharedPreferences.Editor edit();

    public SharedPreferences get();

    public boolean save(SharedPreferences.Editor var1);
}
