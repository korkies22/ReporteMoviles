/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents.internal;

import com.facebook.appevents.internal.AppEventsLoggerUtility;
import java.util.HashMap;

static final class AppEventsLoggerUtility
extends HashMap<AppEventsLoggerUtility.GraphAPIActivityType, String> {
    AppEventsLoggerUtility() {
        this.put(AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT, "MOBILE_APP_INSTALL");
        this.put(AppEventsLoggerUtility.GraphAPIActivityType.CUSTOM_APP_EVENTS, "CUSTOM_APP_EVENTS");
    }
}
