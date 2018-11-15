/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.events;

import io.fabric.sdk.android.services.events.EventsStrategy;
import io.fabric.sdk.android.services.events.FilesSender;

public class DisabledEventsStrategy<T>
implements EventsStrategy<T> {
    @Override
    public void cancelTimeBasedFileRollOver() {
    }

    @Override
    public void deleteAllEvents() {
    }

    @Override
    public FilesSender getFilesSender() {
        return null;
    }

    @Override
    public void recordEvent(T t) {
    }

    @Override
    public boolean rollFileOver() {
        return false;
    }

    @Override
    public void scheduleTimeBasedRollOverIfNeeded() {
    }

    @Override
    public void sendEvents() {
    }
}
