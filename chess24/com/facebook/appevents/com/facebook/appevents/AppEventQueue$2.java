/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AppEventCollection;
import com.facebook.appevents.AppEventStore;

static final class AppEventQueue
implements Runnable {
    AppEventQueue() {
    }

    @Override
    public void run() {
        AppEventStore.persistEvents(appEventCollection);
        com.facebook.appevents.AppEventQueue.appEventCollection = new AppEventCollection();
    }
}
