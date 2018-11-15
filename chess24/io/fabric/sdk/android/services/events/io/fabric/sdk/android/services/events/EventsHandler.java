/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.events.EventsStorageListener;
import io.fabric.sdk.android.services.events.EventsStrategy;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public abstract class EventsHandler<T>
implements EventsStorageListener {
    protected final Context context;
    protected final ScheduledExecutorService executor;
    protected EventsStrategy<T> strategy;

    public EventsHandler(Context context, EventsStrategy<T> eventsStrategy, EventsFilesManager eventsFilesManager, ScheduledExecutorService scheduledExecutorService) {
        this.context = context.getApplicationContext();
        this.executor = scheduledExecutorService;
        this.strategy = eventsStrategy;
        eventsFilesManager.registerRollOverListener(this);
    }

    public void disable() {
        this.executeAsync(new Runnable(){

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
        });
    }

    protected void executeAsync(Runnable runnable) {
        try {
            this.executor.submit(runnable);
            return;
        }
        catch (Exception exception) {
            CommonUtils.logControlledError(this.context, "Failed to submit events task", exception);
            return;
        }
    }

    protected void executeSync(Runnable runnable) {
        try {
            this.executor.submit(runnable).get();
            return;
        }
        catch (Exception exception) {
            CommonUtils.logControlledError(this.context, "Failed to run events task", exception);
            return;
        }
    }

    protected abstract EventsStrategy<T> getDisabledEventsStrategy();

    @Override
    public void onRollOver(String string) {
        this.executeAsync(new Runnable(){

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
        });
    }

    public void recordEventAsync(final T t, final boolean bl) {
        this.executeAsync(new Runnable(){

            @Override
            public void run() {
                try {
                    EventsHandler.this.strategy.recordEvent(t);
                    if (bl) {
                        EventsHandler.this.strategy.rollFileOver();
                        return;
                    }
                }
                catch (Exception exception) {
                    CommonUtils.logControlledError(EventsHandler.this.context, "Failed to record event.", exception);
                }
            }
        });
    }

    public void recordEventSync(final T t) {
        this.executeSync(new Runnable(){

            @Override
            public void run() {
                try {
                    EventsHandler.this.strategy.recordEvent(t);
                    return;
                }
                catch (Exception exception) {
                    CommonUtils.logControlledError(EventsHandler.this.context, "Crashlytics failed to record event", exception);
                    return;
                }
            }
        });
    }

}
