/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.appevents;

import android.os.Bundle;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.Logger;
import java.io.Serializable;
import java.util.Locale;

class FacebookTimeSpentData
implements Serializable {
    private static final long APP_ACTIVATE_SUPPRESSION_PERIOD_IN_MILLISECONDS = 300000L;
    private static final long FIRST_TIME_LOAD_RESUME_TIME = -1L;
    private static final long[] INACTIVE_SECONDS_QUANTA;
    private static final long INTERRUPTION_THRESHOLD_MILLISECONDS = 1000L;
    private static final long NUM_MILLISECONDS_IDLE_TO_BE_NEW_SESSION = 60000L;
    private static final String TAG;
    private static final long serialVersionUID = 1L;
    private String firstOpenSourceApplication;
    private int interruptionCount;
    private boolean isAppActive;
    private boolean isWarmLaunch;
    private long lastActivateEventLoggedTime;
    private long lastResumeTime;
    private long lastSuspendTime;
    private long millisecondsSpentInSession;

    static {
        TAG = FacebookTimeSpentData.class.getCanonicalName();
        INACTIVE_SECONDS_QUANTA = new long[]{300000L, 900000L, 1800000L, 3600000L, 21600000L, 43200000L, 86400000L, 172800000L, 259200000L, 604800000L, 1209600000L, 1814400000L, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
    }

    FacebookTimeSpentData() {
        this.resetSession();
    }

    private FacebookTimeSpentData(long l, long l2, long l3, int n) {
        this.resetSession();
        this.lastResumeTime = l;
        this.lastSuspendTime = l2;
        this.millisecondsSpentInSession = l3;
        this.interruptionCount = n;
    }

    private FacebookTimeSpentData(long l, long l2, long l3, int n, String string) {
        this.resetSession();
        this.lastResumeTime = l;
        this.lastSuspendTime = l2;
        this.millisecondsSpentInSession = l3;
        this.interruptionCount = n;
        this.firstOpenSourceApplication = string;
    }

    private static int getQuantaIndex(long l) {
        int n;
        for (n = 0; n < INACTIVE_SECONDS_QUANTA.length && INACTIVE_SECONDS_QUANTA[n] < l; ++n) {
        }
        return n;
    }

    private boolean isColdLaunch() {
        boolean bl = this.isWarmLaunch;
        this.isWarmLaunch = true;
        return bl ^ true;
    }

    private void logAppDeactivatedEvent(AppEventsLogger appEventsLogger, long l) {
        Bundle bundle = new Bundle();
        bundle.putInt("fb_mobile_app_interruptions", this.interruptionCount);
        bundle.putString("fb_mobile_time_between_sessions", String.format(Locale.ROOT, "session_quanta_%d", FacebookTimeSpentData.getQuantaIndex(l)));
        bundle.putString("fb_mobile_launch_source", this.firstOpenSourceApplication);
        appEventsLogger.logEvent("fb_mobile_deactivate_app", this.millisecondsSpentInSession / 1000L, bundle);
        this.resetSession();
    }

    private void resetSession() {
        this.isAppActive = false;
        this.lastResumeTime = -1L;
        this.lastSuspendTime = -1L;
        this.interruptionCount = 0;
        this.millisecondsSpentInSession = 0L;
    }

    private boolean wasSuspendedEver() {
        if (this.lastSuspendTime != -1L) {
            return true;
        }
        return false;
    }

    private Object writeReplace() {
        return new SerializationProxyV2(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount, this.firstOpenSourceApplication);
    }

    void onResume(AppEventsLogger appEventsLogger, long l, String string) {
        if (this.isColdLaunch() || l - this.lastActivateEventLoggedTime > 300000L) {
            Bundle bundle = new Bundle();
            bundle.putString("fb_mobile_launch_source", string);
            appEventsLogger.logEvent("fb_mobile_activate_app", bundle);
            this.lastActivateEventLoggedTime = l;
            if (AppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
                appEventsLogger.flush();
            }
        }
        if (this.isAppActive) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Resume for active app");
            return;
        }
        boolean bl = this.wasSuspendedEver();
        long l2 = 0L;
        long l3 = bl ? l - this.lastSuspendTime : 0L;
        if (l3 < 0L) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
            l3 = l2;
        }
        if (l3 > 60000L) {
            this.logAppDeactivatedEvent(appEventsLogger, l3);
        } else if (l3 > 1000L) {
            ++this.interruptionCount;
        }
        if (this.interruptionCount == 0) {
            this.firstOpenSourceApplication = string;
        }
        this.lastResumeTime = l;
        this.isAppActive = true;
    }

    void onSuspend(AppEventsLogger appEventsLogger, long l) {
        if (!this.isAppActive) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Suspend for inactive app");
            return;
        }
        long l2 = l - this.lastResumeTime;
        long l3 = 0L;
        if (l2 < 0L) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
            l2 = l3;
        }
        this.millisecondsSpentInSession += l2;
        this.lastSuspendTime = l;
        this.isAppActive = false;
    }

    private static class SerializationProxyV1
    implements Serializable {
        private static final long serialVersionUID = 6L;
        private final int interruptionCount;
        private final long lastResumeTime;
        private final long lastSuspendTime;
        private final long millisecondsSpentInSession;

        SerializationProxyV1(long l, long l2, long l3, int n) {
            this.lastResumeTime = l;
            this.lastSuspendTime = l2;
            this.millisecondsSpentInSession = l3;
            this.interruptionCount = n;
        }

        private Object readResolve() {
            return new FacebookTimeSpentData(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount);
        }
    }

    private static class SerializationProxyV2
    implements Serializable {
        private static final long serialVersionUID = 6L;
        private final String firstOpenSourceApplication;
        private final int interruptionCount;
        private final long lastResumeTime;
        private final long lastSuspendTime;
        private final long millisecondsSpentInSession;

        SerializationProxyV2(long l, long l2, long l3, int n, String string) {
            this.lastResumeTime = l;
            this.lastSuspendTime = l2;
            this.millisecondsSpentInSession = l3;
            this.interruptionCount = n;
            this.firstOpenSourceApplication = string;
        }

        private Object readResolve() {
            return new FacebookTimeSpentData(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount, this.firstOpenSourceApplication);
        }
    }

}
