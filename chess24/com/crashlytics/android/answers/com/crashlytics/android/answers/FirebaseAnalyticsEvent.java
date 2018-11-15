/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.crashlytics.android.answers;

import android.os.Bundle;

public class FirebaseAnalyticsEvent {
    private final String eventName;
    private final Bundle eventParams;

    FirebaseAnalyticsEvent(String string, Bundle bundle) {
        this.eventName = string;
        this.eventParams = bundle;
    }

    public String getEventName() {
        return this.eventName;
    }

    public Bundle getEventParams() {
        return this.eventParams;
    }
}
