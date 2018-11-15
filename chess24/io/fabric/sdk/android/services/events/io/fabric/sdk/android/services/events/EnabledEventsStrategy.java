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
import io.fabric.sdk.android.services.events.EventsStrategy;
import io.fabric.sdk.android.services.events.FileRollOverManager;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.events.TimeBasedFileRollOverRunnable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class EnabledEventsStrategy<T>
implements EventsStrategy<T> {
    static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
    protected final Context context;
    final ScheduledExecutorService executorService;
    protected final EventsFilesManager<T> filesManager;
    volatile int rolloverIntervalSeconds = -1;
    final AtomicReference<ScheduledFuture<?>> scheduledRolloverFutureRef;

    public EnabledEventsStrategy(Context context, ScheduledExecutorService scheduledExecutorService, EventsFilesManager<T> eventsFilesManager) {
        this.context = context;
        this.executorService = scheduledExecutorService;
        this.filesManager = eventsFilesManager;
        this.scheduledRolloverFutureRef = new AtomicReference();
    }

    @Override
    public void cancelTimeBasedFileRollOver() {
        if (this.scheduledRolloverFutureRef.get() != null) {
            CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
            this.scheduledRolloverFutureRef.get().cancel(false);
            this.scheduledRolloverFutureRef.set(null);
        }
    }

    protected void configureRollover(int n) {
        this.rolloverIntervalSeconds = n;
        this.scheduleTimeBasedFileRollOver(0L, this.rolloverIntervalSeconds);
    }

    @Override
    public void deleteAllEvents() {
        this.filesManager.deleteAllEventsFiles();
    }

    public int getRollover() {
        return this.rolloverIntervalSeconds;
    }

    @Override
    public void recordEvent(T t) {
        CommonUtils.logControlled(this.context, t.toString());
        try {
            this.filesManager.writeEvent(t);
        }
        catch (IOException iOException) {
            CommonUtils.logControlledError(this.context, "Failed to write event.", iOException);
        }
        this.scheduleTimeBasedRollOverIfNeeded();
    }

    @Override
    public boolean rollFileOver() {
        try {
            boolean bl = this.filesManager.rollFileOver();
            return bl;
        }
        catch (IOException iOException) {
            CommonUtils.logControlledError(this.context, "Failed to roll file over.", iOException);
            return false;
        }
    }

    void scheduleTimeBasedFileRollOver(long l, long l2) {
        boolean bl = this.scheduledRolloverFutureRef.get() == null;
        if (bl) {
            TimeBasedFileRollOverRunnable timeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
            Context context = this.context;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scheduling time based file roll over every ");
            stringBuilder.append(l2);
            stringBuilder.append(" seconds");
            CommonUtils.logControlled(context, stringBuilder.toString());
            try {
                this.scheduledRolloverFutureRef.set(this.executorService.scheduleAtFixedRate(timeBasedFileRollOverRunnable, l, l2, TimeUnit.SECONDS));
                return;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", rejectedExecutionException);
            }
        }
    }

    @Override
    public void scheduleTimeBasedRollOverIfNeeded() {
        boolean bl = this.rolloverIntervalSeconds != -1;
        if (bl) {
            this.scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void sendAndCleanUpIfSuccess() {
        int n;
        FilesSender filesSender = this.getFilesSender();
        if (filesSender == null) {
            CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
            return;
        }
        CommonUtils.logControlled(this.context, "Sending all files");
        List<File> list = this.filesManager.getBatchOfFilesToSend();
        int n2 = 0;
        do {
            int n3;
            boolean bl;
            block5 : {
                n3 = n2;
                n = n2;
                try {
                    if (list.size() <= 0) break;
                    n3 = n2;
                    CommonUtils.logControlled(this.context, String.format(Locale.US, "attempt to send batch of %d files", list.size()));
                    n3 = n2;
                    bl = filesSender.send(list);
                    n = n2;
                    if (!bl) break block5;
                    n3 = n2;
                    n3 = n = n2 + list.size();
                    this.filesManager.deleteSentFiles(list);
                }
                catch (Exception exception) {
                    filesSender = this.context;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to send batch of analytics files to server: ");
                    stringBuilder.append(exception.getMessage());
                    CommonUtils.logControlledError((Context)filesSender, stringBuilder.toString(), exception);
                    n = n3;
                    break;
                }
            }
            if (!bl) break;
            n3 = n;
            list = this.filesManager.getBatchOfFilesToSend();
            n2 = n;
        } while (true);
        if (n == 0) {
            this.filesManager.deleteOldestInRollOverIfOverMax();
        }
    }

    @Override
    public void sendEvents() {
        this.sendAndCleanUpIfSuccess();
    }
}
