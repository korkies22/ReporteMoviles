/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import android.text.TextUtils;
import com.crashlytics.android.core.AppData;
import com.crashlytics.android.core.CrashlyticsController;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CrashlyticsController
implements CrashlyticsController.FileOutputStreamWriteAction {
    final /* synthetic */ String val$appIdentifier;
    final /* synthetic */ int val$deliveryMechanism;
    final /* synthetic */ String val$installUuid;
    final /* synthetic */ String val$versionCode;
    final /* synthetic */ String val$versionName;

    CrashlyticsController(String string, String string2, String string3, String string4, int n) {
        this.val$appIdentifier = string;
        this.val$versionCode = string2;
        this.val$versionName = string3;
        this.val$installUuid = string4;
        this.val$deliveryMechanism = n;
    }

    @Override
    public void writeTo(FileOutputStream fileOutputStream) throws Exception {
        fileOutputStream.write(new JSONObject((Map)new HashMap<String, Object>(){
            {
                this.put("app_identifier", 20.this.val$appIdentifier);
                this.put("api_key", com.crashlytics.android.core.CrashlyticsController.access$1900((com.crashlytics.android.core.CrashlyticsController)CrashlyticsController.this).apiKey);
                this.put("version_code", 20.this.val$versionCode);
                this.put("version_name", 20.this.val$versionName);
                this.put("install_uuid", 20.this.val$installUuid);
                this.put("delivery_mechanism", 20.this.val$deliveryMechanism);
                20.this = TextUtils.isEmpty((CharSequence)CrashlyticsController.this.unityVersion) ? "" : CrashlyticsController.this.unityVersion;
                this.put("unity_version", 20.this);
            }
        }).toString().getBytes());
    }

}
