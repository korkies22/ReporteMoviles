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
import com.facebook.FacebookSdk;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.internal.CallbackManagerImpl;

static final class FetchedAppSettingsManager
implements CallbackManagerImpl.Callback {
    final /* synthetic */ Context val$context;

    FetchedAppSettingsManager(Context context) {
        this.val$context = context;
    }

    @Override
    public boolean onActivityResult(final int n, final Intent intent) {
        FacebookSdk.getExecutor().execute(new Runnable(){

            @Override
            public void run() {
                AutomaticAnalyticsLogger.logInAppPurchaseEvent(2.this.val$context, n, intent);
            }
        });
        return true;
    }

}
