/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.AdvertisingInfo;
import io.fabric.sdk.android.services.common.AdvertisingInfoStrategy;
import java.lang.reflect.Method;

class AdvertisingInfoReflectionStrategy
implements AdvertisingInfoStrategy {
    private static final String CLASS_NAME_ADVERTISING_ID_CLIENT = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
    private static final String CLASS_NAME_ADVERTISING_ID_CLIENT_INFO = "com.google.android.gms.ads.identifier.AdvertisingIdClient$Info";
    private static final String CLASS_NAME_GOOGLE_PLAY_SERVICES_UTILS = "com.google.android.gms.common.GooglePlayServicesUtil";
    private static final int GOOGLE_PLAY_SERVICES_SUCCESS_CODE = 0;
    private static final String METHOD_NAME_GET_ADVERTISING_ID_INFO = "getAdvertisingIdInfo";
    private static final String METHOD_NAME_GET_ID = "getId";
    private static final String METHOD_NAME_IS_GOOGLE_PLAY_SERVICES_AVAILABLE = "isGooglePlayServicesAvailable";
    private static final String METHOD_NAME_IS_LIMITED_AD_TRACKING_ENABLED = "isLimitAdTrackingEnabled";
    private final Context context;

    public AdvertisingInfoReflectionStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getAdvertisingId() {
        try {
            return (String)Class.forName(CLASS_NAME_ADVERTISING_ID_CLIENT_INFO).getMethod(METHOD_NAME_GET_ID, new Class[0]).invoke(this.getInfo(), new Object[0]);
        }
        catch (Exception exception) {}
        Fabric.getLogger().w("Fabric", "Could not call getId on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object getInfo() {
        try {
            return Class.forName(CLASS_NAME_ADVERTISING_ID_CLIENT).getMethod(METHOD_NAME_GET_ADVERTISING_ID_INFO, Context.class).invoke(null, new Object[]{this.context});
        }
        catch (Exception exception) {}
        Fabric.getLogger().w("Fabric", "Could not call getAdvertisingIdInfo on com.google.android.gms.ads.identifier.AdvertisingIdClient");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isLimitAdTrackingEnabled() {
        try {
            return (Boolean)Class.forName(CLASS_NAME_ADVERTISING_ID_CLIENT_INFO).getMethod(METHOD_NAME_IS_LIMITED_AD_TRACKING_ENABLED, new Class[0]).invoke(this.getInfo(), new Object[0]);
        }
        catch (Exception exception) {}
        Fabric.getLogger().w("Fabric", "Could not call isLimitAdTrackingEnabled on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
        return false;
    }

    @Override
    public AdvertisingInfo getAdvertisingInfo() {
        if (this.isGooglePlayServiceAvailable(this.context)) {
            return new AdvertisingInfo(this.getAdvertisingId(), this.isLimitAdTrackingEnabled());
        }
        return null;
    }

    boolean isGooglePlayServiceAvailable(Context context) {
        boolean bl = false;
        try {
            int n = (Integer)Class.forName(CLASS_NAME_GOOGLE_PLAY_SERVICES_UTILS).getMethod(METHOD_NAME_IS_GOOGLE_PLAY_SERVICES_AVAILABLE, Context.class).invoke(null, new Object[]{context});
            if (n == 0) {
                bl = true;
            }
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }
}
