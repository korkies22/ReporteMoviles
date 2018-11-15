/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.appevents;

import com.facebook.appevents.FacebookTimeSpentData;
import java.io.Serializable;

private static class FacebookTimeSpentData.SerializationProxyV2
implements Serializable {
    private static final long serialVersionUID = 6L;
    private final String firstOpenSourceApplication;
    private final int interruptionCount;
    private final long lastResumeTime;
    private final long lastSuspendTime;
    private final long millisecondsSpentInSession;

    FacebookTimeSpentData.SerializationProxyV2(long l, long l2, long l3, int n, String string) {
        this.lastResumeTime = l;
        this.lastSuspendTime = l2;
        this.millisecondsSpentInSession = l3;
        this.interruptionCount = n;
        this.firstOpenSourceApplication = string;
    }

    private Object readResolve() {
        return new FacebookTimeSpentData(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount, this.firstOpenSourceApplication, null);
    }
}
