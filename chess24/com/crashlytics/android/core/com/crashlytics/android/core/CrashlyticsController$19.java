/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.AppData;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.SessionProtobufHelper;

class CrashlyticsController
implements CrashlyticsController.CodedOutputStreamWriteAction {
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
    public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
        SessionProtobufHelper.writeSessionApp(codedOutputStream, this.val$appIdentifier, com.crashlytics.android.core.CrashlyticsController.access$1900((com.crashlytics.android.core.CrashlyticsController)CrashlyticsController.this).apiKey, this.val$versionCode, this.val$versionName, this.val$installUuid, this.val$deliveryMechanism, CrashlyticsController.this.unityVersion);
    }
}
