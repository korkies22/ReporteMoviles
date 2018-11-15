/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.crashlytics.android.core;

import android.os.Build;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.SessionProtobufHelper;

class CrashlyticsController
implements CrashlyticsController.CodedOutputStreamWriteAction {
    final /* synthetic */ boolean val$isRooted;

    CrashlyticsController(boolean bl) {
        this.val$isRooted = bl;
    }

    @Override
    public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
        SessionProtobufHelper.writeSessionOS(codedOutputStream, Build.VERSION.RELEASE, Build.VERSION.CODENAME, this.val$isRooted);
    }
}
