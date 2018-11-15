/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.appevents;

import android.os.Bundle;

static final class AppEventsLogger
implements Runnable {
    final /* synthetic */ com.facebook.appevents.AppEventsLogger val$logger;

    AppEventsLogger(com.facebook.appevents.AppEventsLogger appEventsLogger) {
        this.val$logger = appEventsLogger;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Bundle bundle;
        bundle = new Bundle();
        try {
            Class.forName("com.facebook.core.Core");
            bundle.putInt("core_lib_included", 1);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        try {
            Class.forName("com.facebook.login.Login");
            bundle.putInt("login_lib_included", 1);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        try {
            Class.forName("com.facebook.share.Share");
            bundle.putInt("share_lib_included", 1);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        try {
            Class.forName("com.facebook.places.Places");
            bundle.putInt("places_lib_included", 1);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        try {
            Class.forName("com.facebook.messenger.Messenger");
            bundle.putInt("messenger_lib_included", 1);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        try {
            Class.forName("com.facebook.applinks.AppLinks");
            bundle.putInt("applinks_lib_included", 1);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        try {
            Class.forName("com.facebook.all.All");
            bundle.putInt("all_lib_included", 1);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        this.val$logger.logSdkEvent("fb_sdk_initialize", null, bundle);
    }
}
