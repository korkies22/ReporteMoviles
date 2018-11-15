/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AnswersPreferenceManager {
    static final String PREFKEY_ANALYTICS_LAUNCHED = "analytics_launched";
    static final String PREF_STORE_NAME = "settings";
    private final PreferenceStore prefStore;

    AnswersPreferenceManager(PreferenceStore preferenceStore) {
        this.prefStore = preferenceStore;
    }

    public static AnswersPreferenceManager build(Context context) {
        return new AnswersPreferenceManager(new PreferenceStoreImpl(context, PREF_STORE_NAME));
    }

    @SuppressLint(value={"CommitPrefEdits"})
    public boolean hasAnalyticsLaunched() {
        return this.prefStore.get().getBoolean(PREFKEY_ANALYTICS_LAUNCHED, false);
    }

    @SuppressLint(value={"CommitPrefEdits"})
    public void setAnalyticsLaunched() {
        this.prefStore.save(this.prefStore.edit().putBoolean(PREFKEY_ANALYTICS_LAUNCHED, true));
    }
}
