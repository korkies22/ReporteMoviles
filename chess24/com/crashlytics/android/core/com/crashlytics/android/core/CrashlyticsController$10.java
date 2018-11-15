/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.MetaDataStore;
import com.crashlytics.android.core.UserMetaData;
import java.io.File;
import java.util.concurrent.Callable;

class CrashlyticsController
implements Callable<Void> {
    final /* synthetic */ String val$userEmail;
    final /* synthetic */ String val$userId;
    final /* synthetic */ String val$userName;

    CrashlyticsController(String string, String string2, String string3) {
        this.val$userId = string;
        this.val$userName = string2;
        this.val$userEmail = string3;
    }

    @Override
    public Void call() throws Exception {
        String string = CrashlyticsController.this.getCurrentSessionId();
        new MetaDataStore(CrashlyticsController.this.getFilesDir()).writeUserData(string, new UserMetaData(this.val$userId, this.val$userName, this.val$userEmail));
        return null;
    }
}
