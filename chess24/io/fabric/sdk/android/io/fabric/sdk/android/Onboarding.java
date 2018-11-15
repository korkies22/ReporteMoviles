/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package io.fabric.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.CreateAppSpiCall;
import io.fabric.sdk.android.services.settings.IconRequest;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.UpdateAppSpiCall;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

class Onboarding
extends Kit<Boolean> {
    private static final String BINARY_BUILD_TYPE = "binary";
    static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private String applicationLabel;
    private String installerPackageName;
    private final Future<Map<String, KitInfo>> kitsFinder;
    private PackageInfo packageInfo;
    private PackageManager packageManager;
    private String packageName;
    private final Collection<Kit> providedKits;
    private final HttpRequestFactory requestFactory = new DefaultHttpRequestFactory();
    private String targetAndroidSdkVersion;
    private String versionCode;
    private String versionName;

    public Onboarding(Future<Map<String, KitInfo>> future, Collection<Kit> collection) {
        this.kitsFinder = future;
        this.providedKits = collection;
    }

    private AppRequestData buildAppRequest(IconRequest iconRequest, Collection<KitInfo> collection) {
        Object object = this.getContext();
        String string = new ApiKey().getValue((Context)object);
        object = CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(object));
        int n = DeliveryMechanism.determineFrom(this.installerPackageName).getId();
        return new AppRequestData(string, this.getIdManager().getAppIdentifier(), this.versionName, this.versionCode, (String)object, this.applicationLabel, n, this.targetAndroidSdkVersion, "0", iconRequest, collection);
    }

    private boolean performAutoConfigure(String string, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        if ("new".equals(appSettingsData.status)) {
            if (this.performCreateApp(string, appSettingsData, collection)) {
                return Settings.getInstance().loadSettingsSkippingCache();
            }
            Fabric.getLogger().e("Fabric", "Failed to create app with Crashlytics service.", null);
            return false;
        }
        if ("configured".equals(appSettingsData.status)) {
            return Settings.getInstance().loadSettingsSkippingCache();
        }
        if (appSettingsData.updateRequired) {
            Fabric.getLogger().d("Fabric", "Server says an update is required - forcing a full App update.");
            this.performUpdateApp(string, appSettingsData, collection);
        }
        return true;
    }

    private boolean performCreateApp(String object, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        object = this.buildAppRequest(IconRequest.build(this.getContext(), (String)object), collection);
        return new CreateAppSpiCall(this, this.getOverridenSpiEndpoint(), appSettingsData.url, this.requestFactory).invoke((AppRequestData)object);
    }

    private boolean performUpdateApp(AppSettingsData appSettingsData, IconRequest object, Collection<KitInfo> collection) {
        object = this.buildAppRequest((IconRequest)object, collection);
        return new UpdateAppSpiCall(this, this.getOverridenSpiEndpoint(), appSettingsData.url, this.requestFactory).invoke((AppRequestData)object);
    }

    private boolean performUpdateApp(String string, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        return this.performUpdateApp(appSettingsData, IconRequest.build(this.getContext(), string), collection);
    }

    private SettingsData retrieveSettingsData() {
        try {
            Settings.getInstance().initialize(this, this.idManager, this.requestFactory, this.versionCode, this.versionName, this.getOverridenSpiEndpoint()).loadSettingsData();
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            return settingsData;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Error dealing with settings", exception);
            return null;
        }
    }

    @Override
    protected Boolean doInBackground() {
        boolean bl;
        block3 : {
            String string = CommonUtils.getAppIconHashOrNull(this.getContext());
            SettingsData settingsData = this.retrieveSettingsData();
            if (settingsData != null) {
                try {
                    Map<Object, Object> map = this.kitsFinder != null ? this.kitsFinder.get() : new HashMap();
                    map = this.mergeKits(map, this.providedKits);
                    bl = this.performAutoConfigure(string, settingsData.appData, map.values());
                    break block3;
                }
                catch (Exception exception) {
                    Fabric.getLogger().e("Fabric", "Error performing auto configuration.", exception);
                }
            }
            bl = false;
        }
        return bl;
    }

    @Override
    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(this.getContext(), CRASHLYTICS_API_ENDPOINT);
    }

    @Override
    public String getVersion() {
        return "1.4.2.22";
    }

    Map<String, KitInfo> mergeKits(Map<String, KitInfo> map, Collection<Kit> object) {
        object = object.iterator();
        while (object.hasNext()) {
            Kit kit = (Kit)object.next();
            if (map.containsKey(kit.getIdentifier())) continue;
            map.put(kit.getIdentifier(), new KitInfo(kit.getIdentifier(), kit.getVersion(), BINARY_BUILD_TYPE));
        }
        return map;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean onPreExecute() {
        try {
            this.installerPackageName = this.getIdManager().getInstallerPackageName();
            this.packageManager = this.getContext().getPackageManager();
            this.packageName = this.getContext().getPackageName();
            this.packageInfo = this.packageManager.getPackageInfo(this.packageName, 0);
            this.versionCode = Integer.toString(this.packageInfo.versionCode);
            String string = this.packageInfo.versionName == null ? "0.0" : this.packageInfo.versionName;
            this.versionName = string;
            this.applicationLabel = this.packageManager.getApplicationLabel(this.getContext().getApplicationInfo()).toString();
            this.targetAndroidSdkVersion = Integer.toString(this.getContext().getApplicationInfo().targetSdkVersion);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Fabric.getLogger().e("Fabric", "Failed init", (Throwable)nameNotFoundException);
            return false;
        }
    }
}
