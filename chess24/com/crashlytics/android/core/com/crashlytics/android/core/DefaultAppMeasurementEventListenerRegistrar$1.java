/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.crashlytics.android.core;

import android.os.Bundle;
import com.crashlytics.android.core.CrashlyticsCore;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class DefaultAppMeasurementEventListenerRegistrar
implements InvocationHandler {
    DefaultAppMeasurementEventListenerRegistrar() {
    }

    @Override
    public Object invoke(Object object, Method object2, Object[] bundle) throws Throwable {
        if (((Object[])bundle).length != 4) {
            throw new RuntimeException("Unexpected AppMeasurement.OnEventListener signature");
        }
        object = (String)bundle[0];
        object2 = (String)bundle[1];
        bundle = (Bundle)bundle[2];
        if (object != null && !object.equals(com.crashlytics.android.core.DefaultAppMeasurementEventListenerRegistrar.CRASH_ORIGIN)) {
            com.crashlytics.android.core.DefaultAppMeasurementEventListenerRegistrar.writeEventToUserLog(DefaultAppMeasurementEventListenerRegistrar.this.crashlyticsCore, (String)object2, bundle);
        }
        return null;
    }
}
