/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsStrategy;

class EventsHandler
implements Runnable {
    EventsHandler() {
    }

    @Override
    public void run() {
        try {
            EventsStrategy eventsStrategy = EventsHandler.this.strategy;
            EventsHandler.this.strategy = EventsHandler.this.getDisabledEventsStrategy();
            eventsStrategy.deleteAllEvents();
            return;
        }
        catch (Exception exception) {
            CommonUtils.logControlledError(EventsHandler.this.context, "Failed to disable events.", exception);
            return;
        }
    }
}
