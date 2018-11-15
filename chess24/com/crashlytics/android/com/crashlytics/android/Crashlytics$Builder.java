/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;
import com.crashlytics.android.core.PinningInfoProvider;

public static class Crashlytics.Builder {
    private Answers answers;
    private Beta beta;
    private CrashlyticsCore core;
    private CrashlyticsCore.Builder coreBuilder;

    private CrashlyticsCore.Builder getCoreBuilder() {
        synchronized (this) {
            if (this.coreBuilder == null) {
                this.coreBuilder = new CrashlyticsCore.Builder();
            }
            CrashlyticsCore.Builder builder = this.coreBuilder;
            return builder;
        }
    }

    public Crashlytics.Builder answers(Answers answers) {
        if (answers == null) {
            throw new NullPointerException("Answers Kit must not be null.");
        }
        if (this.answers != null) {
            throw new IllegalStateException("Answers Kit already set.");
        }
        this.answers = answers;
        return this;
    }

    public Crashlytics.Builder beta(Beta beta) {
        if (beta == null) {
            throw new NullPointerException("Beta Kit must not be null.");
        }
        if (this.beta != null) {
            throw new IllegalStateException("Beta Kit already set.");
        }
        this.beta = beta;
        return this;
    }

    public Crashlytics build() {
        if (this.coreBuilder != null) {
            if (this.core != null) {
                throw new IllegalStateException("Must not use Deprecated methods delay(), disabled(), listener(), pinningInfoProvider() with core()");
            }
            this.core = this.coreBuilder.build();
        }
        if (this.answers == null) {
            this.answers = new Answers();
        }
        if (this.beta == null) {
            this.beta = new Beta();
        }
        if (this.core == null) {
            this.core = new CrashlyticsCore();
        }
        return new Crashlytics(this.answers, this.beta, this.core);
    }

    public Crashlytics.Builder core(CrashlyticsCore crashlyticsCore) {
        if (crashlyticsCore == null) {
            throw new NullPointerException("CrashlyticsCore Kit must not be null.");
        }
        if (this.core != null) {
            throw new IllegalStateException("CrashlyticsCore Kit already set.");
        }
        this.core = crashlyticsCore;
        return this;
    }

    @Deprecated
    public Crashlytics.Builder delay(float f) {
        this.getCoreBuilder().delay(f);
        return this;
    }

    @Deprecated
    public Crashlytics.Builder disabled(boolean bl) {
        this.getCoreBuilder().disabled(bl);
        return this;
    }

    @Deprecated
    public Crashlytics.Builder listener(CrashlyticsListener crashlyticsListener) {
        this.getCoreBuilder().listener(crashlyticsListener);
        return this;
    }

    @Deprecated
    public Crashlytics.Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
        this.getCoreBuilder().pinningInfo(pinningInfoProvider);
        return this;
    }
}
