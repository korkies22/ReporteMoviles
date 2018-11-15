/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package com.crashlytics.android.answers;

import android.content.Context;
import android.os.Bundle;
import com.crashlytics.android.answers.EventLogger;
import java.lang.reflect.Method;

public class AppMeasurementEventLogger
implements EventLogger {
    private static final String ANALYTIC_CLASS = "com.google.android.gms.measurement.AppMeasurement";
    private static final String GET_INSTANCE_METHOD = "getInstance";
    private static final String LOG_METHOD = "logEventInternal";
    private final Object logEventInstance;
    private final Method logEventMethod;

    public AppMeasurementEventLogger(Object object, Method method) {
        this.logEventInstance = object;
        this.logEventMethod = method;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Class getClass(Context object) {
        try {
            return object.getClassLoader().loadClass(ANALYTIC_CLASS);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static EventLogger getEventLogger(Context object) {
        Class class_ = AppMeasurementEventLogger.getClass(object);
        if (class_ == null) {
            return null;
        }
        Object object2 = AppMeasurementEventLogger.getInstance(object, class_);
        if (object2 == null) {
            return null;
        }
        if ((object = AppMeasurementEventLogger.getLogEventMethod(object, class_)) == null) {
            return null;
        }
        return new AppMeasurementEventLogger(object2, (Method)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object getInstance(Context object, Class class_) {
        try {
            void var1_3;
            return var1_3.getDeclaredMethod(GET_INSTANCE_METHOD, Context.class).invoke(var1_3, object);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Method getLogEventMethod(Context object, Class class_) {
        try {
            void var1_3;
            return var1_3.getDeclaredMethod(LOG_METHOD, String.class, String.class, Bundle.class);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public void logEvent(String string, Bundle bundle) {
        this.logEvent("fab", string, bundle);
    }

    @Override
    public void logEvent(String string, String string2, Bundle bundle) {
        try {
            this.logEventMethod.invoke(this.logEventInstance, new Object[]{string, string2, bundle});
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}
