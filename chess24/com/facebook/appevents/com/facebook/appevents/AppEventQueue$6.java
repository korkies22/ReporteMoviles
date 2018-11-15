/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.AccessTokenAppIdPair;
import com.facebook.appevents.AppEventStore;
import com.facebook.appevents.SessionEventsState;

static final class AppEventQueue
implements Runnable {
    final /* synthetic */ AccessTokenAppIdPair val$accessTokenAppId;
    final /* synthetic */ SessionEventsState val$appEvents;

    AppEventQueue(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState) {
        this.val$accessTokenAppId = accessTokenAppIdPair;
        this.val$appEvents = sessionEventsState;
    }

    @Override
    public void run() {
        AppEventStore.persistEvents(this.val$accessTokenAppId, this.val$appEvents);
    }
}
