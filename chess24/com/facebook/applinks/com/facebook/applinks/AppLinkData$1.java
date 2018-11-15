/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook.applinks;

import android.content.Context;
import com.facebook.applinks.AppLinkData;

static final class AppLinkData
implements Runnable {
    final /* synthetic */ Context val$applicationContext;
    final /* synthetic */ String val$applicationIdCopy;
    final /* synthetic */ AppLinkData.CompletionHandler val$completionHandler;

    AppLinkData(Context context, String string, AppLinkData.CompletionHandler completionHandler) {
        this.val$applicationContext = context;
        this.val$applicationIdCopy = string;
        this.val$completionHandler = completionHandler;
    }

    @Override
    public void run() {
        com.facebook.applinks.AppLinkData.fetchDeferredAppLinkFromServer(this.val$applicationContext, this.val$applicationIdCopy, this.val$completionHandler);
    }
}
