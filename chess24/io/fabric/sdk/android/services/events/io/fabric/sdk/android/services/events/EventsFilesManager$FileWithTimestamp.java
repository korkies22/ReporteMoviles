/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.events;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;

static class EventsFilesManager.FileWithTimestamp {
    final File file;
    final long timestamp;

    public EventsFilesManager.FileWithTimestamp(File file, long l) {
        this.file = file;
        this.timestamp = l;
    }
}
