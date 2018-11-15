/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.EventFilter;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import java.util.HashSet;
import java.util.Set;

class SamplingEventFilter
implements EventFilter {
    static final Set<SessionEvent.Type> EVENTS_TYPE_TO_SAMPLE = new HashSet<SessionEvent.Type>(){
        {
            this.add(SessionEvent.Type.START);
            this.add(SessionEvent.Type.RESUME);
            this.add(SessionEvent.Type.PAUSE);
            this.add(SessionEvent.Type.STOP);
        }
    };
    final int samplingRate;

    public SamplingEventFilter(int n) {
        this.samplingRate = n;
    }

    @Override
    public boolean skipEvent(SessionEvent sessionEvent) {
        boolean bl = EVENTS_TYPE_TO_SAMPLE.contains((Object)sessionEvent.type);
        boolean bl2 = false;
        boolean bl3 = bl && sessionEvent.sessionEventMetadata.betaDeviceToken == null;
        boolean bl4 = Math.abs(sessionEvent.sessionEventMetadata.installationId.hashCode() % this.samplingRate) != 0;
        bl = bl2;
        if (bl3) {
            bl = bl2;
            if (bl4) {
                bl = true;
            }
        }
        return bl;
    }

}
