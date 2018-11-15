/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.facebook.appevents.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.InternalAppEventsLogger;
import com.facebook.appevents.internal.SessionInfo;
import com.facebook.appevents.internal.SourceApplicationInfo;
import com.facebook.internal.Logger;
import java.util.Locale;

class SessionLogger {
    private static final long[] INACTIVE_SECONDS_QUANTA;
    private static final String TAG;

    static {
        TAG = SessionLogger.class.getCanonicalName();
        INACTIVE_SECONDS_QUANTA = new long[]{300000L, 900000L, 1800000L, 3600000L, 21600000L, 43200000L, 86400000L, 172800000L, 259200000L, 604800000L, 1209600000L, 1814400000L, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
    }

    SessionLogger() {
    }

    private static int getQuantaIndex(long l) {
        int n;
        for (n = 0; n < INACTIVE_SECONDS_QUANTA.length && INACTIVE_SECONDS_QUANTA[n] < l; ++n) {
        }
        return n;
    }

    public static void logActivateApp(Context object, String string, SourceApplicationInfo sourceApplicationInfo, String string2) {
        object = sourceApplicationInfo != null ? sourceApplicationInfo.toString() : "Unclassified";
        sourceApplicationInfo = new Bundle();
        sourceApplicationInfo.putString("fb_mobile_launch_source", (String)object);
        object = new InternalAppEventsLogger(string, string2, null);
        object.logEvent("fb_mobile_activate_app", (Bundle)sourceApplicationInfo);
        if (AppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
            object.flush();
        }
    }

    private static void logClockSkewEvent() {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
    }

    public static void logDeactivateApp(Context object, String string, SessionInfo sessionInfo, String string2) {
        Object object2 = object = Long.valueOf(sessionInfo.getDiskRestoreTime() - sessionInfo.getSessionLastEventTime());
        if (object.longValue() < 0L) {
            object2 = 0L;
            SessionLogger.logClockSkewEvent();
        }
        Long l = sessionInfo.getSessionLength();
        object = l;
        if (l < 0L) {
            SessionLogger.logClockSkewEvent();
            object = 0L;
        }
        l = new Bundle();
        l.putInt("fb_mobile_app_interruptions", sessionInfo.getInterruptionCount());
        l.putString("fb_mobile_time_between_sessions", String.format(Locale.ROOT, "session_quanta_%d", SessionLogger.getQuantaIndex(object2.longValue())));
        object2 = sessionInfo.getSourceApplicationInfo();
        object2 = object2 != null ? object2.toString() : "Unclassified";
        l.putString("fb_mobile_launch_source", (String)object2);
        l.putLong("_logTime", sessionInfo.getSessionLastEventTime() / 1000L);
        new InternalAppEventsLogger(string, string2, null).logEvent("fb_mobile_deactivate_app", object.longValue() / 1000L, (Bundle)l);
    }
}
