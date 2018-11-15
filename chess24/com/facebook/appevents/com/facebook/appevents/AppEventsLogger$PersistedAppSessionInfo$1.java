/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

static final class AppEventsLogger.PersistedAppSessionInfo
implements Runnable {
    AppEventsLogger.PersistedAppSessionInfo() {
    }

    @Override
    public void run() {
        AppEventsLogger.PersistedAppSessionInfo.saveAppSessionInformation(FacebookSdk.getApplicationContext());
    }
}
