/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.events;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Comparator;

class EventsFilesManager
implements Comparator<EventsFilesManager.FileWithTimestamp> {
    EventsFilesManager() {
    }

    @Override
    public int compare(EventsFilesManager.FileWithTimestamp fileWithTimestamp, EventsFilesManager.FileWithTimestamp fileWithTimestamp2) {
        return (int)(fileWithTimestamp.timestamp - fileWithTimestamp2.timestamp);
    }
}
