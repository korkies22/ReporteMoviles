/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package com.facebook.appevents.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.facebook.internal.Utility;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class InAppPurchaseEventManager {
    private static final String DETAILS_LIST = "DETAILS_LIST";
    private static final String GET_INTERFACE_METHOD = "iap_get_interface";
    private static final String GET_SKU_DETAILS_METHOD = "iap_get_sku_details";
    private static final String IN_APP_BILLING_SERVICE = "com.android.vending.billing.IInAppBillingService";
    private static final String IN_APP_BILLING_SERVICE_STUB = "com.android.vending.billing.IInAppBillingService$Stub";
    private static final String ITEM_ID_LIST = "ITEM_ID_LIST";
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final String TAG;
    private static final HashMap<String, Class<?>> classMap;
    private static final HashMap<String, Method> methodMap;

    static {
        methodMap = new HashMap();
        classMap = new HashMap();
        TAG = InAppPurchaseEventManager.class.getCanonicalName();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getPurchaseDetails(Context object, String string, Object object2, boolean bl) {
        if (object2 == null) return "";
        if (string == "") {
            return "";
        }
        try {
            Serializable serializable;
            Object object3;
            Method method;
            block10 : {
                block9 : {
                    method = methodMap.get(GET_SKU_DETAILS_METHOD);
                    serializable = classMap.get(IN_APP_BILLING_SERVICE);
                    if (method == null) break block9;
                    object3 = serializable;
                    if (serializable != null) break block10;
                }
                object3 = object.getClassLoader().loadClass(IN_APP_BILLING_SERVICE);
                method = object3.getDeclaredMethod("getSkuDetails", Integer.TYPE, String.class, String.class, Bundle.class);
                methodMap.put(GET_SKU_DETAILS_METHOD, method);
                classMap.put(IN_APP_BILLING_SERVICE, (Class<?>)object3);
            }
            serializable = new ArrayList();
            serializable.add((String)string);
            string = new Bundle();
            string.putStringArrayList(ITEM_ID_LIST, (ArrayList)serializable);
            object2 = object3.cast(object2);
            object3 = object.getPackageName();
            object = bl ? "subs" : "inapp";
            Object[] arrobject = new Object[]{3, object3, object, string};
            object = (Bundle)method.invoke(object2, arrobject);
            if (object.getInt(RESPONSE_CODE) != 0) return "";
            if ((object = object.getStringArrayList(DETAILS_LIST)).size() >= 1) return (String)object.get(0);
            return "";
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Illegal access to method com.android.vending.billing.IInAppBillingService.getSkuDetails", (Throwable)illegalAccessException);
            return "";
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)TAG, (String)"Invocation target exception in com.android.vending.billing.IInAppBillingService.getSkuDetails", (Throwable)invocationTargetException);
            return "";
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"com.android.vending.billing.IInAppBillingService.getSkuDetails method is not available", (Throwable)noSuchMethodException);
            return "";
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e((String)TAG, (String)"com.android.vending.billing.IInAppBillingService is not available, please add com.android.vending.billing.IInAppBillingService to the project, and import the IInAppBillingService.aidl file into this package", (Throwable)classNotFoundException);
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object getServiceInterface(Context object, IBinder iBinder) {
        try {
            Method method;
            void var1_6;
            Method method2 = method = methodMap.get(GET_INTERFACE_METHOD);
            if (method == null) {
                method2 = object.getClassLoader().loadClass(IN_APP_BILLING_SERVICE_STUB).getDeclaredMethod("asInterface", IBinder.class);
                methodMap.put(GET_INTERFACE_METHOD, method2);
            }
            Utility.logd(TAG, "In-app billing service connected");
            return method2.invoke(null, var1_6);
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)TAG, (String)"Invocation target exception in com.android.vending.billing.IInAppBillingService$Stub.asInterface", (Throwable)invocationTargetException);
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Illegal access to method com.android.vending.billing.IInAppBillingService$Stub.asInterface", (Throwable)illegalAccessException);
            return null;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"com.android.vending.billing.IInAppBillingService$Stub.asInterface method not found", (Throwable)noSuchMethodException);
            return null;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e((String)TAG, (String)"com.android.vending.billing.IInAppBillingService$Stub is not available, please add com.android.vending.billing.IInAppBillingService to the project.", (Throwable)classNotFoundException);
            return null;
        }
    }
}
