/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;

class CrashlyticsCore
extends PriorityCallable<Void> {
    CrashlyticsCore() {
    }

    @Override
    public Void call() throws Exception {
        return CrashlyticsCore.this.doInBackground();
    }

    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}
