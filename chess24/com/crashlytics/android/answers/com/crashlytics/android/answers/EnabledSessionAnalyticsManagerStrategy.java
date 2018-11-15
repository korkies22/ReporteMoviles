/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.answers;

import android.content.Context;
import com.crashlytics.android.answers.AnswersRetryFilesSender;
import com.crashlytics.android.answers.EventFilter;
import com.crashlytics.android.answers.FirebaseAnalyticsApiAdapter;
import com.crashlytics.android.answers.KeepAllEventFilter;
import com.crashlytics.android.answers.SamplingEventFilter;
import com.crashlytics.android.answers.SessionAnalyticsFilesManager;
import com.crashlytics.android.answers.SessionAnalyticsFilesSender;
import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.FileRollOverManager;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.events.TimeBasedFileRollOverRunnable;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class EnabledSessionAnalyticsManagerStrategy
implements SessionAnalyticsManagerStrategy {
    static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
    ApiKey apiKey = new ApiKey();
    private final Context context;
    boolean customEventsEnabled = true;
    EventFilter eventFilter = new KeepAllEventFilter();
    private final ScheduledExecutorService executorService;
    private final SessionAnalyticsFilesManager filesManager;
    FilesSender filesSender;
    private final FirebaseAnalyticsApiAdapter firebaseAnalyticsApiAdapter;
    boolean forwardToFirebaseAnalyticsEnabled = false;
    private final HttpRequestFactory httpRequestFactory;
    boolean includePurchaseEventsInForwardedEvents = false;
    private final Kit kit;
    final SessionEventMetadata metadata;
    boolean predefinedEventsEnabled = true;
    private final AtomicReference<ScheduledFuture<?>> rolloverFutureRef = new AtomicReference();
    volatile int rolloverIntervalSeconds = -1;

    public EnabledSessionAnalyticsManagerStrategy(Kit kit, Context context, ScheduledExecutorService scheduledExecutorService, SessionAnalyticsFilesManager sessionAnalyticsFilesManager, HttpRequestFactory httpRequestFactory, SessionEventMetadata sessionEventMetadata, FirebaseAnalyticsApiAdapter firebaseAnalyticsApiAdapter) {
        this.kit = kit;
        this.context = context;
        this.executorService = scheduledExecutorService;
        this.filesManager = sessionAnalyticsFilesManager;
        this.httpRequestFactory = httpRequestFactory;
        this.metadata = sessionEventMetadata;
        this.firebaseAnalyticsApiAdapter = firebaseAnalyticsApiAdapter;
    }

    @Override
    public void cancelTimeBasedFileRollOver() {
        if (this.rolloverFutureRef.get() != null) {
            CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
            this.rolloverFutureRef.get().cancel(false);
            this.rolloverFutureRef.set(null);
        }
    }

    @Override
    public void deleteAllEvents() {
        this.filesManager.deleteAllEventsFiles();
    }

    @Override
    public void processEvent(SessionEvent.Builder object) {
        StringBuilder stringBuilder;
        Logger logger;
        object = object.build(this.metadata);
        if (!this.customEventsEnabled && SessionEvent.Type.CUSTOM.equals((Object)object.type)) {
            Logger logger2 = Fabric.getLogger();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Custom events tracking disabled - skipping event: ");
            stringBuilder2.append(object);
            logger2.d("Answers", stringBuilder2.toString());
            return;
        }
        if (!this.predefinedEventsEnabled && SessionEvent.Type.PREDEFINED.equals((Object)object.type)) {
            Logger logger3 = Fabric.getLogger();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Predefined events tracking disabled - skipping event: ");
            stringBuilder3.append(object);
            logger3.d("Answers", stringBuilder3.toString());
            return;
        }
        if (this.eventFilter.skipEvent((SessionEvent)object)) {
            Logger logger4 = Fabric.getLogger();
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Skipping filtered event: ");
            stringBuilder4.append(object);
            logger4.d("Answers", stringBuilder4.toString());
            return;
        }
        try {
            this.filesManager.writeEvent(object);
        }
        catch (IOException iOException) {
            logger = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to write event: ");
            stringBuilder.append(object);
            logger.e("Answers", stringBuilder.toString(), iOException);
        }
        this.scheduleTimeBasedRollOverIfNeeded();
        boolean bl = SessionEvent.Type.CUSTOM.equals((Object)object.type) || SessionEvent.Type.PREDEFINED.equals((Object)object.type);
        boolean bl2 = "purchase".equals(object.predefinedType);
        if (this.forwardToFirebaseAnalyticsEnabled) {
            if (!bl) {
                return;
            }
            if (bl2 && !this.includePurchaseEventsInForwardedEvents) {
                return;
            }
            try {
                this.firebaseAnalyticsApiAdapter.processEvent((SessionEvent)object);
                return;
            }
            catch (Exception exception) {
                logger = Fabric.getLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to map event to Firebase: ");
                stringBuilder.append(object);
                logger.e("Answers", stringBuilder.toString(), exception);
                return;
            }
        }
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
        boolean bl = this.rolloverFutureRef.get() == null;
        if (bl) {
            TimeBasedFileRollOverRunnable timeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
            Context context = this.context;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scheduling time based file roll over every ");
            stringBuilder.append(l2);
            stringBuilder.append(" seconds");
            CommonUtils.logControlled(context, stringBuilder.toString());
            try {
                this.rolloverFutureRef.set(this.executorService.scheduleAtFixedRate(timeBasedFileRollOverRunnable, l, l2, TimeUnit.SECONDS));
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
    @Override
    public void sendEvents() {
        int n;
        if (this.filesSender == null) {
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
                    bl = this.filesSender.send(list);
                    n = n2;
                    if (!bl) break block5;
                    n3 = n2;
                    n3 = n = n2 + list.size();
                    this.filesManager.deleteSentFiles(list);
                }
                catch (Exception exception) {
                    Context context = this.context;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to send batch of analytics files to server: ");
                    stringBuilder.append(exception.getMessage());
                    CommonUtils.logControlledError(context, stringBuilder.toString(), exception);
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
    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String string) {
        this.filesSender = AnswersRetryFilesSender.build(new SessionAnalyticsFilesSender(this.kit, string, analyticsSettingsData.analyticsURL, this.httpRequestFactory, this.apiKey.getValue(this.context)));
        this.filesManager.setAnalyticsSettingsData(analyticsSettingsData);
        this.forwardToFirebaseAnalyticsEnabled = analyticsSettingsData.forwardToFirebaseAnalytics;
        this.includePurchaseEventsInForwardedEvents = analyticsSettingsData.includePurchaseEventsInForwardedEvents;
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Firebase analytics forwarding ");
        string = this.forwardToFirebaseAnalyticsEnabled ? "enabled" : "disabled";
        stringBuilder.append(string);
        logger.d("Answers", stringBuilder.toString());
        logger = Fabric.getLogger();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Firebase analytics including purchase events ");
        string = this.includePurchaseEventsInForwardedEvents ? "enabled" : "disabled";
        stringBuilder.append(string);
        logger.d("Answers", stringBuilder.toString());
        this.customEventsEnabled = analyticsSettingsData.trackCustomEvents;
        logger = Fabric.getLogger();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Custom event tracking ");
        string = this.customEventsEnabled ? "enabled" : "disabled";
        stringBuilder.append(string);
        logger.d("Answers", stringBuilder.toString());
        this.predefinedEventsEnabled = analyticsSettingsData.trackPredefinedEvents;
        logger = Fabric.getLogger();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Predefined event tracking ");
        string = this.predefinedEventsEnabled ? "enabled" : "disabled";
        stringBuilder.append(string);
        logger.d("Answers", stringBuilder.toString());
        if (analyticsSettingsData.samplingRate > 1) {
            Fabric.getLogger().d("Answers", "Event sampling enabled");
            this.eventFilter = new SamplingEventFilter(analyticsSettingsData.samplingRate);
        }
        this.rolloverIntervalSeconds = analyticsSettingsData.flushIntervalSeconds;
        this.scheduleTimeBasedFileRollOver(0L, this.rolloverIntervalSeconds);
    }
}
