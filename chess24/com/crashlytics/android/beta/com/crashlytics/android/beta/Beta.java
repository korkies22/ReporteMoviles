/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Application
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.text.TextUtils;
import com.crashlytics.android.beta.ActivityLifecycleCheckForUpdatesController;
import com.crashlytics.android.beta.BuildProperties;
import com.crashlytics.android.beta.DeviceTokenLoader;
import com.crashlytics.android.beta.ImmediateCheckForUpdatesController;
import com.crashlytics.android.beta.UpdatesController;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.DeviceIdentifierProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class Beta
extends Kit<Boolean>
implements DeviceIdentifierProvider {
    private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
    private static final String CRASHLYTICS_BUILD_PROPERTIES = "crashlytics-build.properties";
    static final String NO_DEVICE_TOKEN = "";
    public static final String TAG = "Beta";
    private final MemoryValueCache<String> deviceTokenCache = new MemoryValueCache();
    private final DeviceTokenLoader deviceTokenLoader = new DeviceTokenLoader();
    private UpdatesController updatesController;

    private String getBetaDeviceToken(Context object, String object2) {
        object2 = null;
        try {
            object = this.deviceTokenCache.get((Context)object, this.deviceTokenLoader);
            boolean bl = NO_DEVICE_TOKEN.equals(object);
            if (bl) {
                object = object2;
            }
        }
        catch (Exception exception) {
            Fabric.getLogger().e(TAG, "Failed to load the Beta device token", exception);
            object = object2;
        }
        object2 = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Beta device token present: ");
        stringBuilder.append(TextUtils.isEmpty((CharSequence)object) ^ true);
        object2.d(TAG, stringBuilder.toString());
        return object;
    }

    private BetaSettingsData getBetaSettingsData() {
        SettingsData settingsData = Settings.getInstance().awaitSettingsData();
        if (settingsData != null) {
            return settingsData.betaSettingsData;
        }
        return null;
    }

    public static Beta getInstance() {
        return Fabric.getKit(Beta.class);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private BuildProperties loadBuildProperties(Context object) {
        void var1_4;
        Object inputStream;
        block18 : {
            void var3_14;
            Object inputStream2;
            block17 : {
                block16 : {
                    inputStream = null;
                    inputStream2 = object.getAssets().open(CRASHLYTICS_BUILD_PROPERTIES);
                    object = inputStream;
                    if (inputStream2 == null) break block16;
                    inputStream = inputStream2;
                    try {
                        object = BuildProperties.fromPropertiesStream((InputStream)inputStream2);
                        inputStream = inputStream2;
                    }
                    catch (Exception exception) {
                        object = null;
                        break block17;
                    }
                    try {
                        Logger logger = Fabric.getLogger();
                        inputStream = inputStream2;
                        StringBuilder stringBuilder = new StringBuilder();
                        inputStream = inputStream2;
                        stringBuilder.append(object.packageName);
                        inputStream = inputStream2;
                        stringBuilder.append(" build properties: ");
                        inputStream = inputStream2;
                        stringBuilder.append(object.versionName);
                        inputStream = inputStream2;
                        stringBuilder.append(" (");
                        inputStream = inputStream2;
                        stringBuilder.append(object.versionCode);
                        inputStream = inputStream2;
                        stringBuilder.append(") - ");
                        inputStream = inputStream2;
                        stringBuilder.append(object.buildId);
                        inputStream = inputStream2;
                        logger.d(TAG, stringBuilder.toString());
                    }
                    catch (Exception exception) {
                        break block17;
                    }
                }
                inputStream = object;
                if (inputStream2 == null) return inputStream;
                try {
                    inputStream2.close();
                    return object;
                }
                catch (IOException iOException) {
                    Fabric.getLogger().e(TAG, "Error closing Beta build properties asset", iOException);
                    return object;
                }
                catch (Throwable throwable) {
                    inputStream = null;
                    break block18;
                }
                catch (Exception exception) {
                    inputStream2 = object = null;
                }
            }
            inputStream = inputStream2;
            Fabric.getLogger().e(TAG, "Error reading Beta build properties", (Throwable)var3_14);
            if (inputStream2 == null) return object;
            try {
                inputStream2.close();
                return object;
            }
            catch (IOException iOException) {
                Fabric.getLogger().e(TAG, "Error closing Beta build properties asset", iOException);
            }
            return object;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (inputStream == null) throw var1_4;
        try {
            inputStream.close();
            throw var1_4;
        }
        catch (IOException iOException) {
            Fabric.getLogger().e(TAG, "Error closing Beta build properties asset", iOException);
        }
        throw var1_4;
    }

    boolean canCheckForUpdates(BetaSettingsData betaSettingsData, BuildProperties buildProperties) {
        if (betaSettingsData != null && !TextUtils.isEmpty((CharSequence)betaSettingsData.updateUrl) && buildProperties != null) {
            return true;
        }
        return false;
    }

    @TargetApi(value=14)
    UpdatesController createUpdatesController(int n, Application application) {
        if (n >= 14) {
            return new ActivityLifecycleCheckForUpdatesController(this.getFabric().getActivityLifecycleManager(), this.getFabric().getExecutorService());
        }
        return new ImmediateCheckForUpdatesController();
    }

    @Override
    protected Boolean doInBackground() {
        Fabric.getLogger().d(TAG, "Beta kit initializing...");
        Context context = this.getContext();
        IdManager idManager = this.getIdManager();
        if (TextUtils.isEmpty((CharSequence)this.getBetaDeviceToken(context, idManager.getInstallerPackageName()))) {
            Fabric.getLogger().d(TAG, "A Beta device token was not found for this app");
            return false;
        }
        Fabric.getLogger().d(TAG, "Beta device token is present, checking for app updates.");
        BetaSettingsData betaSettingsData = this.getBetaSettingsData();
        BuildProperties buildProperties = this.loadBuildProperties(context);
        if (this.canCheckForUpdates(betaSettingsData, buildProperties)) {
            this.updatesController.initialize(context, this, idManager, betaSettingsData, buildProperties, new PreferenceStoreImpl(this), new SystemCurrentTimeProvider(), new DefaultHttpRequestFactory(Fabric.getLogger()));
        }
        return true;
    }

    @Override
    public Map<IdManager.DeviceIdentifierType, String> getDeviceIdentifiers() {
        String string = this.getIdManager().getInstallerPackageName();
        string = this.getBetaDeviceToken(this.getContext(), string);
        HashMap<IdManager.DeviceIdentifierType, String> hashMap = new HashMap<IdManager.DeviceIdentifierType, String>();
        if (!TextUtils.isEmpty((CharSequence)string)) {
            hashMap.put(IdManager.DeviceIdentifierType.FONT_TOKEN, string);
        }
        return hashMap;
    }

    @Override
    public String getIdentifier() {
        return "com.crashlytics.sdk.android:beta";
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(this.getContext(), CRASHLYTICS_API_ENDPOINT);
    }

    @Override
    public String getVersion() {
        return "1.2.7.19";
    }

    @TargetApi(value=14)
    @Override
    protected boolean onPreExecute() {
        Application application = (Application)this.getContext().getApplicationContext();
        this.updatesController = this.createUpdatesController(Build.VERSION.SDK_INT, application);
        return true;
    }
}
