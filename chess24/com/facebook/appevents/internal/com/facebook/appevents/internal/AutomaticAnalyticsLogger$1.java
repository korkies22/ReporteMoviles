/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.appevents.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.internal.InAppPurchaseEventManager;
import com.facebook.internal.Utility;
import java.math.BigDecimal;
import java.util.Currency;
import org.json.JSONException;
import org.json.JSONObject;

static final class AutomaticAnalyticsLogger
implements ServiceConnection {
    final /* synthetic */ Context val$context;
    final /* synthetic */ String val$purchaseData;

    AutomaticAnalyticsLogger(Context context, String string) {
        this.val$context = context;
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
                    inAppBillingObj = InAppPurchaseEventManager.getServiceInterface(this.val$context, (IBinder)object);
                    componentName = new JSONObject(this.val$purchaseData);
                    object = componentName.getString("productId");
                    boolean bl = componentName.has("autoRenewing");
                    string = InAppPurchaseEventManager.getPurchaseDetails(this.val$context, (String)object, inAppBillingObj, bl);
                    bl = string.equals("");
                    if (!bl) break block5;
                    this.val$context.unbindService((ServiceConnection)this);
                    return;
                }
                try {
                    string = new JSONObject(string);
                    AppEventsLogger appEventsLogger = AppEventsLogger.newLogger(this.val$context);
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
            this.val$context.unbindService((ServiceConnection)this);
            return;
        }
        this.val$context.unbindService((ServiceConnection)this);
        throw throwable22222;
    }

    public void onServiceDisconnected(ComponentName componentName) {
        inAppBillingObj = null;
        Utility.logd(TAG, "In-app billing service disconnected");
    }
}
