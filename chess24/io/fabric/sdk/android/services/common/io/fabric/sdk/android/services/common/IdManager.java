/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 */
package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AdvertisingInfo;
import io.fabric.sdk.android.services.common.AdvertisingInfoProvider;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeviceIdentifierProvider;
import io.fabric.sdk.android.services.common.FirebaseInfo;
import io.fabric.sdk.android.services.common.InstallerPackageNameProvider;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdManager {
    private static final String BAD_ANDROID_ID = "9774d56d682e549c";
    public static final String COLLECT_DEVICE_IDENTIFIERS = "com.crashlytics.CollectDeviceIdentifiers";
    public static final String COLLECT_USER_IDENTIFIERS = "com.crashlytics.CollectUserIdentifiers";
    public static final String DEFAULT_VERSION_NAME = "0.0";
    private static final String FORWARD_SLASH_REGEX;
    private static final Pattern ID_PATTERN;
    static final String PREFKEY_ADVERTISING_ID = "crashlytics.advertising.id";
    private static final String PREFKEY_INSTALLATION_UUID = "crashlytics.installation.id";
    AdvertisingInfo advertisingInfo;
    AdvertisingInfoProvider advertisingInfoProvider;
    private final Context appContext;
    private final String appIdentifier;
    private final String appInstallIdentifier;
    private final boolean collectHardwareIds;
    private final boolean collectUserIds;
    boolean fetchedAdvertisingInfo;
    FirebaseInfo firebaseInfo;
    private final ReentrantLock installationIdLock = new ReentrantLock();
    private final InstallerPackageNameProvider installerPackageNameProvider;
    private final Collection<Kit> kits;

    static {
        ID_PATTERN = Pattern.compile("[^\\p{Alnum}]");
        FORWARD_SLASH_REGEX = Pattern.quote("/");
    }

    public IdManager(Context context, String object, String charSequence, Collection<Kit> collection) {
        if (context == null) {
            throw new IllegalArgumentException("appContext must not be null");
        }
        if (object == null) {
            throw new IllegalArgumentException("appIdentifier must not be null");
        }
        if (collection == null) {
            throw new IllegalArgumentException("kits must not be null");
        }
        this.appContext = context;
        this.appIdentifier = object;
        this.appInstallIdentifier = charSequence;
        this.kits = collection;
        this.installerPackageNameProvider = new InstallerPackageNameProvider();
        this.advertisingInfoProvider = new AdvertisingInfoProvider(context);
        this.firebaseInfo = new FirebaseInfo();
        this.collectHardwareIds = CommonUtils.getBooleanResourceValue(context, COLLECT_DEVICE_IDENTIFIERS, true);
        if (!this.collectHardwareIds) {
            object = Fabric.getLogger();
            charSequence = new StringBuilder();
            charSequence.append("Device ID collection disabled for ");
            charSequence.append(context.getPackageName());
            object.d("Fabric", charSequence.toString());
        }
        this.collectUserIds = CommonUtils.getBooleanResourceValue(context, COLLECT_USER_IDENTIFIERS, true);
        if (!this.collectUserIds) {
            object = Fabric.getLogger();
            charSequence = new StringBuilder();
            charSequence.append("User information collection disabled for ");
            charSequence.append(context.getPackageName());
            object.d("Fabric", charSequence.toString());
        }
    }

    private void checkAdvertisingIdRotation(SharedPreferences sharedPreferences) {
        AdvertisingInfo advertisingInfo = this.getAdvertisingInfo();
        if (advertisingInfo != null) {
            this.flushInstallationIdIfNecessary(sharedPreferences, advertisingInfo.advertisingId);
        }
    }

    @SuppressLint(value={"CommitPrefEdits"})
    private String createInstallationUUID(SharedPreferences sharedPreferences) {
        String string;
        block4 : {
            String string2;
            this.installationIdLock.lock();
            string = string2 = sharedPreferences.getString(PREFKEY_INSTALLATION_UUID, null);
            if (string2 != null) break block4;
            string = this.formatId(UUID.randomUUID().toString());
            sharedPreferences.edit().putString(PREFKEY_INSTALLATION_UUID, string).commit();
        }
        return string;
        finally {
            this.installationIdLock.unlock();
        }
    }

    private Boolean explicitCheckLimitAdTracking() {
        AdvertisingInfo advertisingInfo = this.getAdvertisingInfo();
        if (advertisingInfo != null) {
            return advertisingInfo.limitAdTrackingEnabled;
        }
        return null;
    }

    @SuppressLint(value={"CommitPrefEdits"})
    private void flushInstallationIdIfNecessary(SharedPreferences sharedPreferences, String string) {
        block7 : {
            this.installationIdLock.lock();
            boolean bl = TextUtils.isEmpty((CharSequence)string);
            if (!bl) break block7;
            this.installationIdLock.unlock();
            return;
        }
        try {
            String string2 = sharedPreferences.getString(PREFKEY_ADVERTISING_ID, null);
            if (TextUtils.isEmpty((CharSequence)string2)) {
                sharedPreferences.edit().putString(PREFKEY_ADVERTISING_ID, string).commit();
            } else if (!string2.equals(string)) {
                sharedPreferences.edit().remove(PREFKEY_INSTALLATION_UUID).putString(PREFKEY_ADVERTISING_ID, string).commit();
            }
            return;
        }
        finally {
            this.installationIdLock.unlock();
        }
    }

    private String formatId(String string) {
        if (string == null) {
            return null;
        }
        return ID_PATTERN.matcher(string).replaceAll("").toLowerCase(Locale.US);
    }

    private void putNonNullIdInto(Map<DeviceIdentifierType, String> map, DeviceIdentifierType deviceIdentifierType, String string) {
        if (string != null) {
            map.put(deviceIdentifierType, string);
        }
    }

    private String removeForwardSlashesIn(String string) {
        return string.replaceAll(FORWARD_SLASH_REGEX, "");
    }

    public boolean canCollectUserIds() {
        return this.collectUserIds;
    }

    @Deprecated
    public String createIdHeaderValue(String string, String string2) {
        return "";
    }

    public String getAdvertisingId() {
        AdvertisingInfo advertisingInfo;
        if (this.shouldCollectHardwareIds() && (advertisingInfo = this.getAdvertisingInfo()) != null && !advertisingInfo.limitAdTrackingEnabled) {
            return advertisingInfo.advertisingId;
        }
        return null;
    }

    AdvertisingInfo getAdvertisingInfo() {
        synchronized (this) {
            if (!this.fetchedAdvertisingInfo) {
                this.advertisingInfo = this.advertisingInfoProvider.getAdvertisingInfo();
                this.fetchedAdvertisingInfo = true;
            }
            AdvertisingInfo advertisingInfo = this.advertisingInfo;
            return advertisingInfo;
        }
    }

    public String getAndroidId() {
        String string;
        boolean bl = Boolean.TRUE.equals(this.explicitCheckLimitAdTracking());
        if (this.shouldCollectHardwareIds() && !bl && !BAD_ANDROID_ID.equals(string = Settings.Secure.getString((ContentResolver)this.appContext.getContentResolver(), (String)"android_id"))) {
            return this.formatId(string);
        }
        return null;
    }

    public String getAppIdentifier() {
        return this.appIdentifier;
    }

    public String getAppInstallIdentifier() {
        String string;
        String string2 = string = this.appInstallIdentifier;
        if (string == null) {
            string = CommonUtils.getSharedPrefs(this.appContext);
            this.checkAdvertisingIdRotation((SharedPreferences)string);
            string2 = string.getString(PREFKEY_INSTALLATION_UUID, null);
            if (string2 == null) {
                return this.createInstallationUUID((SharedPreferences)string);
            }
        }
        return string2;
    }

    @Deprecated
    public String getBluetoothMacAddress() {
        return null;
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        HashMap<DeviceIdentifierType, String> hashMap = new HashMap<DeviceIdentifierType, String>();
        for (Kit kit : this.kits) {
            if (!(kit instanceof DeviceIdentifierProvider)) continue;
            for (Map.Entry<DeviceIdentifierType, String> entry : ((DeviceIdentifierProvider)((Object)kit)).getDeviceIdentifiers().entrySet()) {
                this.putNonNullIdInto(hashMap, entry.getKey(), entry.getValue());
            }
        }
        String string = this.getAdvertisingId();
        if (TextUtils.isEmpty((CharSequence)string)) {
            this.putNonNullIdInto(hashMap, DeviceIdentifierType.ANDROID_ID, this.getAndroidId());
        } else {
            this.putNonNullIdInto(hashMap, DeviceIdentifierType.ANDROID_ADVERTISING_ID, string);
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public String getInstallerPackageName() {
        return this.installerPackageNameProvider.getInstallerPackageName(this.appContext);
    }

    public String getModelName() {
        return String.format(Locale.US, "%s/%s", this.removeForwardSlashesIn(Build.MANUFACTURER), this.removeForwardSlashesIn(Build.MODEL));
    }

    public String getOsBuildVersionString() {
        return this.removeForwardSlashesIn(Build.VERSION.INCREMENTAL);
    }

    public String getOsDisplayVersionString() {
        return this.removeForwardSlashesIn(Build.VERSION.RELEASE);
    }

    public String getOsVersionString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getOsDisplayVersionString());
        stringBuilder.append("/");
        stringBuilder.append(this.getOsBuildVersionString());
        return stringBuilder.toString();
    }

    @Deprecated
    public String getSerialNumber() {
        return null;
    }

    @Deprecated
    public String getTelephonyId() {
        return null;
    }

    @Deprecated
    public String getWifiMacAddress() {
        return null;
    }

    public Boolean isLimitAdTrackingEnabled() {
        if (this.shouldCollectHardwareIds()) {
            return this.explicitCheckLimitAdTracking();
        }
        return null;
    }

    protected boolean shouldCollectHardwareIds() {
        if (this.collectHardwareIds && !this.firebaseInfo.isFirebaseCrashlyticsEnabled(this.appContext)) {
            return true;
        }
        return false;
    }

    public static enum DeviceIdentifierType {
        WIFI_MAC_ADDRESS(1),
        BLUETOOTH_MAC_ADDRESS(2),
        FONT_TOKEN(53),
        ANDROID_ID(100),
        ANDROID_DEVICE_ID(101),
        ANDROID_SERIAL(102),
        ANDROID_ADVERTISING_ID(103);
        
        public final int protobufIndex;

        private DeviceIdentifierType(int n2) {
            this.protobufIndex = n2;
        }
    }

}
