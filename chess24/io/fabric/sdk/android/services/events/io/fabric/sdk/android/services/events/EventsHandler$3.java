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
            EventsHandler.this.strategy.sendEvents();
            return;
        }
        catch (Exception exception) {
            CommonUtils.logControlledError(EventsHandler.this.context, "Failed to send events files.", exception);
            return;
        }
    }
}
