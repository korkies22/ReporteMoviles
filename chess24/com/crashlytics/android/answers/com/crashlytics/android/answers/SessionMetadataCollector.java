/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.answers;

import android.content.Context;
import com.crashlytics.android.answers.SessionEventMetadata;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import java.util.Map;
import java.util.UUID;

class SessionMetadataCollector {
    private final Context context;
    private final IdManager idManager;
    private final String versionCode;
    private final String versionName;

    public SessionMetadataCollector(Context context, IdManager idManager, String string, String string2) {
        this.context = context;
        this.idManager = idManager;
        this.versionCode = string;
        this.versionName = string2;
    }

    public SessionEventMetadata getMetadata() {
        Object object = this.idManager.getDeviceIdentifiers();
        String string = this.idManager.getAppIdentifier();
        String string2 = this.idManager.getAppInstallIdentifier();
        String string3 = object.get((Object)IdManager.DeviceIdentifierType.ANDROID_ID);
        String string4 = object.get((Object)IdManager.DeviceIdentifierType.ANDROID_ADVERTISING_ID);
        Boolean bl = this.idManager.isLimitAdTrackingEnabled();
        object = object.get((Object)IdManager.DeviceIdentifierType.FONT_TOKEN);
        String string5 = CommonUtils.resolveBuildId(this.context);
        String string6 = this.idManager.getOsVersionString();
        String string7 = this.idManager.getModelName();
        return new SessionEventMetadata(string, UUID.randomUUID().toString(), string2, string3, string4, bl, (String)object, string5, string6, string7, this.versionCode, this.versionName);
    }
}
