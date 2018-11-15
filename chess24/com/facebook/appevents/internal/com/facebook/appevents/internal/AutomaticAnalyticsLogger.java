/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.appevents.internal;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.InAppPurchaseEventManager;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.math.BigDecimal;
import java.util.Currency;
import org.json.JSONException;
import org.json.JSONObject;

public class AutomaticAnalyticsLogger {
    private static final String INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    private static final String TAG = AutomaticAnalyticsLogger.class.getCanonicalName();
    @Nullable
    private static Object inAppBillingObj;

    public static boolean isImplicitPurchaseLoggingEnabled() {
        FetchedAppSettings fetchedAppSettings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
        boolean bl = false;
        if (fetchedAppSettings == null) {
            return false;
        }
        boolean bl2 = bl;
        if (FacebookSdk.getAutoLogAppEventsEnabled()) {
            bl2 = bl;
            if (fetchedAppSettings.getIAPAutomaticLoggingEnabled()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static void logActivateAppEvent() {
        Context context = FacebookSdk.getApplicationContext();
        String string = FacebookSdk.getApplicationId();
        boolean bl = FacebookSdk.getAutoLogAppEventsEnabled();
        Validate.notNull((Object)context, "context");
        if (bl) {
            if (context instanceof Application) {
                AppEventsLogger.activateApp((Application)context, string);
                return;
            }
            Log.w((String)TAG, (String)"Automatic logging of basic events will not happen, because FacebookSdk.getApplicationContext() returns object that is not instance of android.app.Application. Make sure you call FacebookSdk.sdkInitialize() from Application class and pass application context.");
        }
    }

    public static void logActivityTimeSpentEvent(String string, long l) {
        Object object = FacebookSdk.getApplicationContext();
        Object object2 = FacebookSdk.getApplicationId();
        Validate.notNull(object, "context");
        object2 = FetchedAppSettingsManager.queryAppSettings((String)object2, false);
        if (object2 != null && object2.getAutomaticLoggingEnabled() && l > 0L) {
            object = AppEventsLogger.newLogger((Context)object);
            object2 = new Bundle(1);
            object2.putCharSequence("fb_aa_time_spent_view_name", (CharSequence)string);
            object.logEvent("fb_aa_time_spent_on_view", l, (Bundle)object2);
        }
    }

    public static boolean logInAppPurchaseEvent(final Context context, int n, Intent object) {
        if (object != null && AutomaticAnalyticsLogger.isImplicitPurchaseLoggingEnabled()) {
            object = object.getStringExtra(INAPP_PURCHASE_DATA);
            if (n == -1) {
                object = new ServiceConnection((String)object){
                    final /* synthetic */ String val$purchaseData;
                    {
                        this.val$purchaseData = string;
                    }

                    /*
                     * Loose catch block
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     * Lifted jumps to return sites
                     */
                    public void onServiceConnected(ComponentName componentName, IBinder object) {
                        Throwable throwable22222;
                        block7 : {
                            block6 : {
                                String string;
                                block5 : {
                                    inAppBillingObj = InAppPurchaseEventManager.getServiceInterface(context, (IBinder)object);
                                    componentName = new JSONObject(this.val$purchaseData);
                                    object = componentName.getString("productId");
                                    boolean bl = componentName.has("autoRenewing");
                                    string = InAppPurchaseEventManager.getPurchaseDetails(context, (String)object, inAppBillingObj, bl);
                                    bl = string.equals("");
                                    if (!bl) break block5;
                                    context.unbindService((ServiceConnection)this);
                                    return;
                                }
                                try {
                                    string = new JSONObject(string);
                                    AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(context);
                                    Bundle bundle = new Bundle(1);
                                    bundle.putCharSequence("fb_iap_product_id", (CharSequence)object);
                                    bundle.putCharSequence("fb_iap_purchase_time", (CharSequence)componentName.getString("purchaseTime"));
                                    bundle.putCharSequence("fb_iap_purchase_state", (CharSequence)componentName.getString("purchaseState"));
                                    bundle.putCharSequence("fb_iap_purchase_token", (CharSequence)componentName.getString("purchaseToken"));
                                    bundle.putCharSequence("fb_iap_package_name", (CharSequence)componentName.getString("packageName"));
                                    bundle.putCharSequence("fb_iap_product_type", (CharSequence)string.getString("type"));
                                    bundle.putCharSequence("fb_iap_product_title", (CharSequence)string.getString("title"));
                                    bundle.putCharSequence("fb_iap_product_description", (CharSequence)string.getString("description"));
                                    bundle.putCharSequence("fb_iap_subs_auto_renewing", (CharSequence)Boolean.toString(componentName.optBoolean("autoRenewing", false)));
                                    bundle.putCharSequence("fb_iap_subs_period", (CharSequence)string.optString("subscriptionPeriod"));
                                    bundle.putCharSequence("fb_free_trial_period", (CharSequence)string.optString("freeTrialPeriod"));
                                    bundle.putCharSequence("fb_intro_price_amount_micros", (CharSequence)string.optString("introductoryPriceAmountMicros"));
                                    bundle.putCharSequence("fb_intro_price_cycles", (CharSequence)string.optString("introductoryPriceCycles"));
                                    appEventsLogger.logPurchaseImplicitly(new BigDecimal((double)string.getLong("price_amount_micros") / 1000000.0), Currency.getInstance(string.getString("price_currency_code")), bundle);
                                    break block6;
                                }
                                catch (Throwable throwable22222) {
                                    break block7;
                                }
                                catch (JSONException jSONException) {
                                    Log.e((String)TAG, (String)"Error parsing in-app purchase data.", (Throwable)jSONException);
                                }
                            }
                            context.unbindService((ServiceConnection)this);
                            return;
                        }
                        context.unbindService((ServiceConnection)this);
                        throw throwable22222;
                    }

                    public void onServiceDisconnected(ComponentName componentName) {
                        inAppBillingObj = null;
                        Utility.logd(TAG, "In-app billing service disconnected");
                    }
                };
                Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
                intent.setPackage("com.android.vending");
                context.bindService(intent, (ServiceConnection)object, 1);
            }
            return true;
        }
        return false;
    }

}
