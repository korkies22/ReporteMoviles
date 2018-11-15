/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.MetaDataStore;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

class CrashlyticsController
implements Callable<Void> {
    final /* synthetic */ Map val$keyData;

    CrashlyticsController(Map map) {
        this.val$keyData = map;
    }

    @Override
    public Void call() throws Exception {
        String string = CrashlyticsController.this.getCurrentSessionId();
        new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeKeyData(string, this.val$keyData);
        return null;
    }
}
