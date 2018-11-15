/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.facebook;

import android.content.Context;

static final class FacebookSdk
implements Runnable {
    final /* synthetic */ Context val$applicationContext;
    final /* synthetic */ String val$applicationId;

    FacebookSdk(Context context, String string) {
        this.val$applicationContext = context;
        this.val$applicationId = string;
    }

    @Override
    public void run() {
        com.facebook.FacebookSdk.publishInstallAndWaitForResponse(this.val$applicationContext, this.val$applicationId);
    }
}
