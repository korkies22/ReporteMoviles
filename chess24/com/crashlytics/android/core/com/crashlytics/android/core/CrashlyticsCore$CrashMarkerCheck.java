/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsFileMarker;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Callable;

private static final class CrashlyticsCore.CrashMarkerCheck
implements Callable<Boolean> {
    private final CrashlyticsFileMarker crashMarker;

    public CrashlyticsCore.CrashMarkerCheck(CrashlyticsFileMarker crashlyticsFileMarker) {
        this.crashMarker = crashlyticsFileMarker;
    }

    @Override
    public Boolean call() throws Exception {
        if (!this.crashMarker.isPresent()) {
            return Boolean.FALSE;
        }
        Fabric.getLogger().d(CrashlyticsCore.TAG, "Found previous crash marker.");
        this.crashMarker.remove();
        return Boolean.TRUE;
    }
}
