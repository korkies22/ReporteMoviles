/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionEvent;
import java.util.HashSet;

static final class SamplingEventFilter
extends HashSet<SessionEvent.Type> {
    SamplingEventFilter() {
        this.add(SessionEvent.Type.START);
        this.add(SessionEvent.Type.RESUME);
        this.add(SessionEvent.Type.PAUSE);
        this.add(SessionEvent.Type.STOP);
    }
}
