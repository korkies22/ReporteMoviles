/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEventQueue;
import com.facebook.internal.FetchedAppSettingsManager;
import java.util.HashSet;
import java.util.Iterator;

static final class AppEventsLogger
implements Runnable {
    AppEventsLogger() {
    }

    @Override
    public void run() {
        Object object = new HashSet<String>();
        Iterator<AccessTokenAppIdPair> iterator = AppEventQueue.getKeySet().iterator();
        while (iterator.hasNext()) {
            object.add(iterator.next().getApplicationId());
        }
        object = object.iterator();
        while (object.hasNext()) {
            FetchedAppSettingsManager.queryAppSettings((String)object.next(), true);
        }
    }
}
