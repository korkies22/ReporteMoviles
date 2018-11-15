/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.SessionProtobufHelper;
import com.crashlytics.android.core.UserMetaData;

class CrashlyticsController
implements CrashlyticsController.CodedOutputStreamWriteAction {
    final /* synthetic */ UserMetaData val$userMetaData;

    CrashlyticsController(UserMetaData userMetaData) {
        this.val$userMetaData = userMetaData;
    }

    @Override
    public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
        SessionProtobufHelper.writeSessionUser(codedOutputStream, this.val$userMetaData.id, this.val$userMetaData.name, this.val$userMetaData.email);
    }
}
