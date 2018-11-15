/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.crashlytics.android.answers;

import android.content.Context;
import android.os.Bundle;
import com.crashlytics.android.answers.AppMeasurementEventLogger;
import com.crashlytics.android.answers.EventLogger;
import com.crashlytics.android.answers.FirebaseAnalyticsEvent;
import com.crashlytics.android.answers.FirebaseAnalyticsEventMapper;
import com.crashlytics.android.answers.SessionEvent;
import io.fabric.sdk.android.Fabric;

class FirebaseAnalyticsApiAdapter {
    private final Context context;
    private EventLogger eventLogger;
    private final FirebaseAnalyticsEventMapper eventMapper;

    public FirebaseAnalyticsApiAdapter(Context context) {
        this(context, new FirebaseAnalyticsEventMapper());
    }

    public FirebaseAnalyticsApiAdapter(Context context, FirebaseAnalyticsEventMapper firebaseAnalyticsEventMapper) {
        this.context = context;
        this.eventMapper = firebaseAnalyticsEventMapper;
    }

    public EventLogger getFirebaseAnalytics() {
        if (this.eventLogger == null) {
            this.eventLogger = AppMeasurementEventLogger.getEventLogger(this.context);
        }
        return this.eventLogger;
    }

    public void processEvent(SessionEvent sessionEvent) {
        Object object = this.getFirebaseAnalytics();
        if (object == null) {
            Fabric.getLogger().d("Answers", "Firebase analytics logging was enabled, but not available...");
            return;
        }
        Object object2 = this.eventMapper.mapEvent(sessionEvent);
        if (object2 == null) {
            object = Fabric.getLogger();
            object2 = new StringBuilder();
            object2.append("Fabric event was not mappable to Firebase event: ");
            object2.append(sessionEvent);
            object.d("Answers", object2.toString());
            return;
        }
        object.logEvent(object2.getEventName(), object2.getEventParams());
        if ("levelEnd".equals(sessionEvent.predefinedType)) {
            object.logEvent("post_score", object2.getEventParams());
        }
    }
}
