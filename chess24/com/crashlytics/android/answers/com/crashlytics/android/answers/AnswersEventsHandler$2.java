/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.DisabledSessionAnalyticsManagerStrategy;
import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import io.fabric.sdk.android.Fabric;

class AnswersEventsHandler
implements Runnable {
    AnswersEventsHandler() {
    }

    @Override
    public void run() {
        try {
            SessionAnalyticsManagerStrategy sessionAnalyticsManagerStrategy = AnswersEventsHandler.this.strategy;
            AnswersEventsHandler.this.strategy = new DisabledSessionAnalyticsManagerStrategy();
            sessionAnalyticsManagerStrategy.deleteAllEvents();
            return;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Answers", "Failed to disable events", exception);
            return;
        }
    }
}
