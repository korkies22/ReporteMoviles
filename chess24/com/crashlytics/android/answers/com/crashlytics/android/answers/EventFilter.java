/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionEvent;

interface EventFilter {
    public boolean skipEvent(SessionEvent var1);
}
