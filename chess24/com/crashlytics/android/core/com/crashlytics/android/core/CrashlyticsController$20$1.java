/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.crashlytics.android.core;

import android.text.TextUtils;
import com.crashlytics.android.core.AppData;
import com.crashlytics.android.core.CrashlyticsController;
import java.util.HashMap;

class CrashlyticsController
extends HashMap<String, Object> {
    CrashlyticsController() {
        this.put("app_identifier", 20.this.val$appIdentifier);
        this.put("api_key", com.crashlytics.android.core.CrashlyticsController.access$1900((com.crashlytics.android.core.CrashlyticsController)20.this.this$0).apiKey);
        this.put("version_code", 20.this.val$versionCode);
        this.put("version_name", 20.this.val$versionName);
        this.put("install_uuid", 20.this.val$installUuid);
        this.put("delivery_mechanism", 20.this.val$deliveryMechanism);
        20.this = TextUtils.isEmpty((CharSequence)20.this.this$0.unityVersion) ? "" : 20.this.this$0.unityVersion;
        this.put("unity_version", 20.this);
    }
}
