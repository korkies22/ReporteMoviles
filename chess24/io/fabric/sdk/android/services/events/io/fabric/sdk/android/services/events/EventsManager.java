/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.events;

public interface EventsManager<T> {
    public void deleteAllEvents();

    public void recordEvent(T var1);

    public void sendEvents();
}
