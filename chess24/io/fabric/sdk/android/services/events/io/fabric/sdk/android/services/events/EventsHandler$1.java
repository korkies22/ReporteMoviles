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
    final /* synthetic */ Object val$event;
    final /* synthetic */ boolean val$sendImmediately;

    EventsHandler(Object object, boolean bl) {
        this.val$event = object;
        this.val$sendImmediately = bl;
    }

    @Override
    public void run() {
        try {
            EventsHandler.this.strategy.recordEvent(this.val$event);
            if (this.val$sendImmediately) {
                EventsHandler.this.strategy.rollFileOver();
                return;
            }
        }
        catch (Exception exception) {
            CommonUtils.logControlledError(EventsHandler.this.context, "Failed to record event.", exception);
        }
    }
}
