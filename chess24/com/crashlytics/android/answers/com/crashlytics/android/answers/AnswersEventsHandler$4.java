/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.answers;

import android.content.Context;
import com.crashlytics.android.answers.AnswersFilesManagerProvider;
import com.crashlytics.android.answers.EnabledSessionAnalyticsManagerStrategy;
import com.crashlytics.android.answers.FirebaseAnalyticsApiAdapter;
import com.crashlytics.android.answers.SessionAnalyticsFilesManager;
import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import com.crashlytics.android.answers.SessionEventMetadata;
import com.crashlytics.android.answers.SessionMetadataCollector;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.events.EventsStorageListener;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.concurrent.ScheduledExecutorService;

class AnswersEventsHandler
implements Runnable {
    AnswersEventsHandler() {
    }

    @Override
    public void run() {
        try {
            SessionEventMetadata sessionEventMetadata = AnswersEventsHandler.this.metadataCollector.getMetadata();
            SessionAnalyticsFilesManager sessionAnalyticsFilesManager = AnswersEventsHandler.this.filesManagerProvider.getAnalyticsFilesManager();
            sessionAnalyticsFilesManager.registerRollOverListener(AnswersEventsHandler.this);
            AnswersEventsHandler.this.strategy = new EnabledSessionAnalyticsManagerStrategy(AnswersEventsHandler.this.kit, AnswersEventsHandler.this.context, AnswersEventsHandler.this.executor, sessionAnalyticsFilesManager, AnswersEventsHandler.this.requestFactory, sessionEventMetadata, AnswersEventsHandler.this.firebaseAnalyticsApiAdapter);
            return;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Answers", "Failed to enable events", exception);
            return;
        }
    }
}
