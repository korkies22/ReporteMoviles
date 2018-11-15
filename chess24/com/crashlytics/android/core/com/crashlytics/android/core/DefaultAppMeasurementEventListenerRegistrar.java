/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.crashlytics.android.core;

import android.content.Context;
import android.os.Bundle;
import com.crashlytics.android.core.AppMeasurementEventListenerRegistrar;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultAppMeasurementEventListenerRegistrar
implements AppMeasurementEventListenerRegistrar {
    private static final String ANALYTIC_CLASS = "com.google.android.gms.measurement.AppMeasurement";
    private static final String ANALYTIC_CLASS_ON_EVENT_LISTENER = "com.google.android.gms.measurement.AppMeasurement$OnEventListener";
    private static final String CRASH_ORIGIN = "crash";
    private static final String GET_INSTANCE_METHOD = "getInstance";
    private static final String NAME = "name";
    private static final String PARAMETERS = "parameters";
    private static final String REGISTER_METHOD = "registerOnMeasurementEventListener";
    private final CrashlyticsCore crashlyticsCore;

    private DefaultAppMeasurementEventListenerRegistrar(CrashlyticsCore crashlyticsCore) {
        this.crashlyticsCore = crashlyticsCore;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Class<?> getClass(String class_) {
        try {
            return this.crashlyticsCore.getContext().getClassLoader().loadClass((String)((Object)class_));
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object getInstance(Class<?> object) {
        try {
            return object.getDeclaredMethod(GET_INSTANCE_METHOD, Context.class).invoke(object, new Object[]{this.crashlyticsCore.getContext()});
        }
        catch (Exception exception) {
            return null;
        }
    }

    static AppMeasurementEventListenerRegistrar instanceFrom(CrashlyticsCore crashlyticsCore) {
        return new DefaultAppMeasurementEventListenerRegistrar(crashlyticsCore);
    }

    private boolean invoke(Class<?> class_, Object object, String string) {
        Serializable serializable = this.getClass(ANALYTIC_CLASS_ON_EVENT_LISTENER);
        try {
            class_.getDeclaredMethod(string, new Class[]{serializable}).invoke(object, this.onEventListenerProxy((Class)serializable));
            return true;
        }
        catch (IllegalAccessException illegalAccessException) {
            object = Fabric.getLogger();
            serializable = new StringBuilder();
            serializable.append("Cannot access method: ");
            serializable.append(string);
            object.w("CrashlyticsCore", serializable.toString(), illegalAccessException);
            return false;
        }
        catch (InvocationTargetException invocationTargetException) {
            object = Fabric.getLogger();
            serializable = new StringBuilder();
            serializable.append("Cannot invoke method: ");
            serializable.append(string);
            object.w("CrashlyticsCore", serializable.toString(), invocationTargetException);
            return false;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object = Fabric.getLogger();
            serializable = new StringBuilder();
            serializable.append("Expected method missing: ");
            serializable.append(string);
            object.w("CrashlyticsCore", serializable.toString(), noSuchMethodException);
            return false;
        }
    }

    private Object onEventListenerProxy(Class class_) {
        ClassLoader classLoader = this.crashlyticsCore.getContext().getClassLoader();
        InvocationHandler invocationHandler = new InvocationHandler(){

            @Override
            public Object invoke(Object object, Method object2, Object[] bundle) throws Throwable {
                if (((Object[])bundle).length != 4) {
                    throw new RuntimeException("Unexpected AppMeasurement.OnEventListener signature");
                }
                object = (String)bundle[0];
                object2 = (String)bundle[1];
                bundle = (Bundle)bundle[2];
                if (object != null && !object.equals(DefaultAppMeasurementEventListenerRegistrar.CRASH_ORIGIN)) {
                    DefaultAppMeasurementEventListenerRegistrar.writeEventToUserLog(DefaultAppMeasurementEventListenerRegistrar.this.crashlyticsCore, (String)object2, bundle);
                }
                return null;
            }
        };
        return Proxy.newProxyInstance(classLoader, new Class[]{class_}, invocationHandler);
    }

    private static String serializeEvent(String string, Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        for (String string2 : bundle.keySet()) {
            jSONObject2.put(string2, bundle.get(string2));
        }
        jSONObject.put(NAME, (Object)string);
        jSONObject.put(PARAMETERS, (Object)jSONObject2);
        return jSONObject.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void writeEventToUserLog(CrashlyticsCore crashlyticsCore, String string, Bundle object) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("$A$:");
            stringBuilder.append(DefaultAppMeasurementEventListenerRegistrar.serializeEvent(string, (Bundle)object));
            crashlyticsCore.log(stringBuilder.toString());
            return;
        }
        catch (JSONException jSONException) {}
        Logger logger = Fabric.getLogger();
        object = new StringBuilder();
        object.append("Unable to serialize Firebase Analytics event; ");
        object.append(string);
        logger.w("CrashlyticsCore", object.toString());
    }

    @Override
    public boolean register() {
        Class<?> class_ = this.getClass(ANALYTIC_CLASS);
        if (class_ == null) {
            Fabric.getLogger().w("CrashlyticsCore", "Firebase Analytics is not present; you will not see automatic logging of events before a crash occurs.");
            return false;
        }
        Object object = this.getInstance(class_);
        if (object == null) {
            Fabric.getLogger().w("CrashlyticsCore", "Could not create an instance of Firebase Analytics.");
            return false;
        }
        return this.invoke(class_, object, REGISTER_METHOD);
    }

}
