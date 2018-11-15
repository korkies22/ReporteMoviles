/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionAnalyticsManagerStrategy;
import com.crashlytics.android.answers.SessionEvent;
import io.fabric.sdk.android.Fabric;

class AnswersEventsHandler
implements Runnable {
    final /* synthetic */ SessionEvent.Builder val$eventBuilder;
    final /* synthetic */ boolean val$flush;

    AnswersEventsHandler(SessionEvent.Builder builder, boolean bl) {
        this.val$eventBuilder = builder;
        this.val$flush = bl;
    }

    @Override
    public void run() {
        try {
            AnswersEventsHandler.this.strategy.processEvent(this.val$eventBuilder);
            if (this.val$flush) {
                AnswersEventsHandler.this.strategy.rollFileOver();
                return;
            }
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Answers", "Failed to process event", exception);
        }
    }
}
