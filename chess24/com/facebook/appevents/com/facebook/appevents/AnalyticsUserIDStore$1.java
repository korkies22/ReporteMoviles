/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

static final class AnalyticsUserIDStore
implements Runnable {
    AnalyticsUserIDStore() {
    }

    @Override
    public void run() {
        com.facebook.appevents.AnalyticsUserIDStore.initAndWait();
    }
}
