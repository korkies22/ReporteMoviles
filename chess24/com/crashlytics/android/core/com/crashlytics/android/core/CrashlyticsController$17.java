/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.SessionProtobufHelper;

class CrashlyticsController
implements CrashlyticsController.CodedOutputStreamWriteAction {
    final /* synthetic */ String val$generator;
    final /* synthetic */ String val$sessionId;
    final /* synthetic */ long val$startedAtSeconds;

    CrashlyticsController(String string, String string2, long l) {
        this.val$sessionId = string;
        this.val$generator = string2;
        this.val$startedAtSeconds = l;
    }

    @Override
    public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
        SessionProtobufHelper.writeBeginSession(codedOutputStream, this.val$sessionId, this.val$generator, this.val$startedAtSeconds);
    }
}
