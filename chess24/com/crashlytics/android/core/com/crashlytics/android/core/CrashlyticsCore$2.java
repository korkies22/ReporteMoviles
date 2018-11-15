/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsFileMarker;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Callable;

class CrashlyticsCore
implements Callable<Void> {
    CrashlyticsCore() {
    }

    @Override
    public Void call() throws Exception {
        CrashlyticsCore.this.initializationMarker.create();
        Fabric.getLogger().d(com.crashlytics.android.core.CrashlyticsCore.TAG, "Initialization marker file created.");
        return null;
    }
}
