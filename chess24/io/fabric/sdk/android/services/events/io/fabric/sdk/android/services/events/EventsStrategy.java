/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.events;

import io.fabric.sdk.android.services.events.EventsManager;
import io.fabric.sdk.android.services.events.FileRollOverManager;
import io.fabric.sdk.android.services.events.FilesSender;

public interface EventsStrategy<T>
extends FileRollOverManager,
EventsManager<T> {
    public FilesSender getFilesSender();
}
