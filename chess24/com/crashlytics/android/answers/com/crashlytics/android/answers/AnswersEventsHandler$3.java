/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import io.fabric.sdk.android.Fabric;

class AnswersEventsHandler
implements Runnable {
    AnswersEventsHandler() {
    }

    @Override
    public void run() {
        try {
            AnswersEventsHandler.this.strategy.sendEvents();
            return;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Answers", "Failed to send events files", exception);
            return;
        }
    }
}
