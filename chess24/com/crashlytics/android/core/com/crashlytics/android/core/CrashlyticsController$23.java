/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 */
package com.crashlytics.android.core;

import android.os.Build;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.CrashlyticsController;
import com.crashlytics.android.core.SessionProtobufHelper;
import java.util.Map;

class CrashlyticsController
implements CrashlyticsController.CodedOutputStreamWriteAction {
    final /* synthetic */ int val$arch;
    final /* synthetic */ int val$availableProcessors;
    final /* synthetic */ long val$diskSpace;
    final /* synthetic */ Map val$ids;
    final /* synthetic */ boolean val$isEmulator;
    final /* synthetic */ int val$state;
    final /* synthetic */ long val$totalRam;

    CrashlyticsController(int n, int n2, long l, long l2, boolean bl, Map map, int n3) {
        this.val$arch = n;
        this.val$availableProcessors = n2;
        this.val$totalRam = l;
        this.val$diskSpace = l2;
        this.val$isEmulator = bl;
        this.val$ids = map;
        this.val$state = n3;
    }

    @Override
    public void writeTo(CodedOutputStream codedOutputStream) throws Exception {
        SessionProtobufHelper.writeSessionDevice(codedOutputStream, this.val$arch, Build.MODEL, this.val$availableProcessors, this.val$totalRam, this.val$diskSpace, this.val$isEmulator, this.val$ids, this.val$state, Build.MANUFACTURER, Build.PRODUCT);
    }
}
