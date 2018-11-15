/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;
import com.crashlytics.android.core.PinningInfoProvider;

public static class CrashlyticsCore.Builder {
    private float delay = -1.0f;
    private boolean disabled = false;
    private CrashlyticsListener listener;
    private PinningInfoProvider pinningInfoProvider;

    public CrashlyticsCore build() {
        if (this.delay < 0.0f) {
            this.delay = 1.0f;
        }
        return new CrashlyticsCore(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
    }

    public CrashlyticsCore.Builder delay(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("delay must be greater than 0");
        }
        if (this.delay > 0.0f) {
            throw new IllegalStateException("delay already set.");
        }
        this.delay = f;
        return this;
    }

    public CrashlyticsCore.Builder disabled(boolean bl) {
        this.disabled = bl;
        return this;
    }

    public CrashlyticsCore.Builder listener(CrashlyticsListener crashlyticsListener) {
        if (crashlyticsListener == null) {
            throw new IllegalArgumentException("listener must not be null.");
        }
        if (this.listener != null) {
            throw new IllegalStateException("listener already set.");
        }
        this.listener = crashlyticsListener;
        return this;
    }

    @Deprecated
    public CrashlyticsCore.Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
        if (pinningInfoProvider == null) {
            throw new IllegalArgumentException("pinningInfoProvider must not be null.");
        }
        if (this.pinningInfoProvider != null) {
            throw new IllegalStateException("pinningInfoProvider already set.");
        }
        this.pinningInfoProvider = pinningInfoProvider;
        return this;
    }
}
