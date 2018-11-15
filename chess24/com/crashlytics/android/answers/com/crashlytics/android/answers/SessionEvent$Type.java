/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionEvent;

static enum SessionEvent.Type {
    START,
    RESUME,
    PAUSE,
    STOP,
    CRASH,
    INSTALL,
    CUSTOM,
    PREDEFINED;
    

    private SessionEvent.Type() {
    }
}
