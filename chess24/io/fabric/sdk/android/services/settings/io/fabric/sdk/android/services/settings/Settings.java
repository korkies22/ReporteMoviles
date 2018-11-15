/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.settings;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.CachedSettingsIo;
import io.fabric.sdk.android.services.settings.DefaultCachedSettingsIo;
import io.fabric.sdk.android.services.settings.DefaultSettingsController;
import io.fabric.sdk.android.services.settings.DefaultSettingsJsonTransform;
import io.fabric.sdk.android.services.settings.DefaultSettingsSpiCall;
import io.fabric.sdk.android.services.settings.SettingsCacheBehavior;
import io.fabric.sdk.android.services.settings.SettingsController;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.SettingsJsonTransform;
import io.fabric.sdk.android.services.settings.SettingsRequest;
import io.fabric.sdk.android.services.settings.SettingsSpiCall;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Settings {
    public static final String SETTINGS_CACHE_FILENAME = "com.crashlytics.settings.json";
    private static final String SETTINGS_URL_FORMAT = "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings";
    private boolean initialized = false;
    private SettingsController settingsController;
    private final AtomicReference<SettingsData> settingsData = new AtomicReference();
    private final CountDownLatch settingsDataLatch = new CountDownLatch(1);

    private Settings() {
    }

    public static Settings getInstance() {
        return INSTANCE;
    }

    private void setSettingsData(SettingsData settingsData) {
        this.settingsData.set(settingsData);
        this.settingsDataLatch.countDown();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SettingsData awaitSettingsData() {
        try {
            this.settingsDataLatch.await();
            return this.settingsData.get();
        }
        catch (InterruptedException interruptedException) {}
        Fabric.getLogger().e("Fabric", "Interrupted while waiting for settings data.");
        return null;
    }

    public void clearSettings() {
        this.settingsData.set(null);
    }

    public Settings initialize(Kit kit, IdManager idManager, HttpRequestFactory object, String string, String string2, String string3) {
        synchronized (this) {
            block5 : {
                boolean bl = this.initialized;
                if (!bl) break block5;
                return this;
            }
            if (this.settingsController == null) {
                Context context = kit.getContext();
                String string4 = idManager.getAppIdentifier();
                String string5 = new ApiKey().getValue(context);
                String string6 = idManager.getInstallerPackageName();
                SystemCurrentTimeProvider systemCurrentTimeProvider = new SystemCurrentTimeProvider();
                DefaultSettingsJsonTransform defaultSettingsJsonTransform = new DefaultSettingsJsonTransform();
                DefaultCachedSettingsIo defaultCachedSettingsIo = new DefaultCachedSettingsIo(kit);
                String string7 = CommonUtils.getAppIconHashOrNull(context);
                object = new DefaultSettingsSpiCall(kit, string3, String.format(Locale.US, SETTINGS_URL_FORMAT, string4), (HttpRequestFactory)object);
                this.settingsController = new DefaultSettingsController(kit, new SettingsRequest(string5, idManager.getModelName(), idManager.getOsBuildVersionString(), idManager.getOsDisplayVersionString(), idManager.getAdvertisingId(), idManager.getAppInstallIdentifier(), idManager.getAndroidId(), CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(context)), string2, string, DeliveryMechanism.determineFrom(string6).getId(), string7), systemCurrentTimeProvider, defaultSettingsJsonTransform, defaultCachedSettingsIo, (SettingsSpiCall)object);
            }
            this.initialized = true;
            return this;
        }
    }

    public boolean loadSettingsData() {
        synchronized (this) {
            SettingsData settingsData = this.settingsController.loadSettingsData();
            this.setSettingsData(settingsData);
            boolean bl = settingsData != null;
            return bl;
        }
    }

    public boolean loadSettingsSkippingCache() {
        synchronized (this) {
            SettingsData settingsData;
            block4 : {
                settingsData = this.settingsController.loadSettingsData(SettingsCacheBehavior.SKIP_CACHE_LOOKUP);
                this.setSettingsData(settingsData);
                if (settingsData != null) break block4;
                Fabric.getLogger().e("Fabric", "Failed to force reload of settings from Crashlytics.", null);
            }
            boolean bl = settingsData != null;
            return bl;
        }
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public <T> T withSettings(SettingsAccess<T> settingsAccess, T t) {
        SettingsData settingsData = this.settingsData.get();
        if (settingsData == null) {
            return t;
        }
        return settingsAccess.usingSettings(settingsData);
    }

    static class LazyHolder {
        private static final Settings INSTANCE = new Settings();

        LazyHolder() {
        }
    }

    public static interface SettingsAccess<T> {
        public T usingSettings(SettingsData var1);
    }

}
