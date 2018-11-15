/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package com.facebook.internal;

import android.content.Context;
import android.content.Intent;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.internal.FetchedAppSettingsManager;

class FetchedAppSettingsManager
implements Runnable {
    final /* synthetic */ Intent val$finalData;
    final /* synthetic */ int val$finalResultCode;

    FetchedAppSettingsManager(int n, Intent intent) {
        this.val$finalResultCode = n;
        this.val$finalData = intent;
    }

    @Override
    public void run() {
        AutomaticAnalyticsLogger.logInAppPurchaseEvent(2.this.val$context, this.val$finalResultCode, this.val$finalData);
    }
}
