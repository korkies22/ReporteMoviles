/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsFileMarker;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.concurrent.Callable;

class CrashlyticsCore
implements Callable<Boolean> {
    CrashlyticsCore() {
    }

    @Override
    public Boolean call() throws Exception {
        boolean bl;
        try {
            bl = CrashlyticsCore.this.initializationMarker.remove();
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Initialization marker file removed: ");
            stringBuilder.append(bl);
            logger.d(com.crashlytics.android.core.CrashlyticsCore.TAG, stringBuilder.toString());
        }
        catch (Exception exception) {
            Fabric.getLogger().e(com.crashlytics.android.core.CrashlyticsCore.TAG, "Problem encountered deleting Crashlytics initialization marker.", exception);
            return false;
        }
        return bl;
    }
}
