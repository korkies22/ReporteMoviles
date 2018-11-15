/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;

private static final class CrashlyticsCore.NoOpListener
implements CrashlyticsListener {
    private CrashlyticsCore.NoOpListener() {
    }

    @Override
    public void crashlyticsDidDetectCrashDuringPreviousExecution() {
    }
}
