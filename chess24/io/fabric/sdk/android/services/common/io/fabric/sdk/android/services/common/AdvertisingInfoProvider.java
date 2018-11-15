/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfo;
import io.fabric.sdk.android.services.common.AdvertisingInfoReflectionStrategy;
import io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy;
import io.fabric.sdk.android.services.common.AdvertisingInfoStrategy;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AdvertisingInfoProvider {
    private static final String ADVERTISING_INFO_PREFERENCES = "TwitterAdvertisingInfoPreferences";
    private static final String PREFKEY_ADVERTISING_ID = "advertising_id";
    private static final String PREFKEY_LIMIT_AD_TRACKING = "limit_ad_tracking_enabled";
    private final Context context;
    private final PreferenceStore preferenceStore;

    public AdvertisingInfoProvider(Context context) {
        this.context = context.getApplicationContext();
        this.preferenceStore = new PreferenceStoreImpl(context, ADVERTISING_INFO_PREFERENCES);
    }

    private AdvertisingInfo getAdvertisingInfoFromStrategies() {
        AdvertisingInfo advertisingInfo = this.getReflectionStrategy().getAdvertisingInfo();
        if (!this.isInfoValid(advertisingInfo)) {
            advertisingInfo = this.getServiceStrategy().getAdvertisingInfo();
            if (!this.isInfoValid(advertisingInfo)) {
                Fabric.getLogger().d("Fabric", "AdvertisingInfo not present");
                return advertisingInfo;
            }
            Fabric.getLogger().d("Fabric", "Using AdvertisingInfo from Service Provider");
            return advertisingInfo;
        }
        Fabric.getLogger().d("Fabric", "Using AdvertisingInfo from Reflection Provider");
        return advertisingInfo;
    }

    private boolean isInfoValid(AdvertisingInfo advertisingInfo) {
        if (advertisingInfo != null && !TextUtils.isEmpty((CharSequence)advertisingInfo.advertisingId)) {
            return true;
        }
        return false;
    }

    private void refreshInfoIfNeededAsync(final AdvertisingInfo advertisingInfo) {
        new Thread(new BackgroundPriorityRunnable(){

            @Override
            public void onRun() {
                AdvertisingInfo advertisingInfo2 = AdvertisingInfoProvider.this.getAdvertisingInfoFromStrategies();
                if (!advertisingInfo.equals(advertisingInfo2)) {
                    Fabric.getLogger().d("Fabric", "Asychronously getting Advertising Info and storing it to preferences");
                    AdvertisingInfoProvider.this.storeInfoToPreferences(advertisingInfo2);
                }
            }
        }).start();
    }

    @SuppressLint(value={"CommitPrefEdits"})
    private void storeInfoToPreferences(AdvertisingInfo advertisingInfo) {
        if (this.isInfoValid(advertisingInfo)) {
            this.preferenceStore.save(this.preferenceStore.edit().putString(PREFKEY_ADVERTISING_ID, advertisingInfo.advertisingId).putBoolean(PREFKEY_LIMIT_AD_TRACKING, advertisingInfo.limitAdTrackingEnabled));
            return;
        }
        this.preferenceStore.save(this.preferenceStore.edit().remove(PREFKEY_ADVERTISING_ID).remove(PREFKEY_LIMIT_AD_TRACKING));
    }

    public AdvertisingInfo getAdvertisingInfo() {
        AdvertisingInfo advertisingInfo = this.getInfoFromPreferences();
        if (this.isInfoValid(advertisingInfo)) {
            Fabric.getLogger().d("Fabric", "Using AdvertisingInfo from Preference Store");
            this.refreshInfoIfNeededAsync(advertisingInfo);
            return advertisingInfo;
        }
        advertisingInfo = this.getAdvertisingInfoFromStrategies();
        this.storeInfoToPreferences(advertisingInfo);
        return advertisingInfo;
    }

    protected AdvertisingInfo getInfoFromPreferences() {
        return new AdvertisingInfo(this.preferenceStore.get().getString(PREFKEY_ADVERTISING_ID, ""), this.preferenceStore.get().getBoolean(PREFKEY_LIMIT_AD_TRACKING, false));
    }

    public AdvertisingInfoStrategy getReflectionStrategy() {
        return new AdvertisingInfoReflectionStrategy(this.context);
    }

    public AdvertisingInfoStrategy getServiceStrategy() {
        return new AdvertisingInfoServiceStrategy(this.context);
    }

}
