/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.ServiceConnection
 *  android.content.pm.PackageManager
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  org.json.JSONException
 */
package com.example.android.trivialdrivesample.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.vending.billing.IInAppBillingService;
import com.example.android.trivialdrivesample.util.IabException;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import com.example.android.trivialdrivesample.util.Security;
import com.example.android.trivialdrivesample.util.SkuDetails;
import de.cisha.chess.util.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class IabHelper {
    public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    public static final int BILLING_RESPONSE_RESULT_ERROR = 6;
    public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
    public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;
    public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    public static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
    public static final String GET_SKU_DETAILS_ITEM_TYPE_LIST = "ITEM_TYPE_LIST";
    public static final int IABHELPER_BAD_RESPONSE = -1002;
    public static final int IABHELPER_ERROR_BASE = -1000;
    public static final int IABHELPER_INVALID_CONSUMPTION = -1010;
    public static final int IABHELPER_MISSING_TOKEN = -1007;
    public static final int IABHELPER_REMOTE_EXCEPTION = -1001;
    public static final int IABHELPER_SEND_INTENT_FAILED = -1004;
    public static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;
    public static final int IABHELPER_UNKNOWN_ERROR = -1008;
    public static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;
    public static final int IABHELPER_USER_CANCELLED = -1005;
    public static final int IABHELPER_VERIFICATION_FAILED = -1003;
    public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    public static final String ITEM_TYPE_INAPP = "inapp";
    public static final String ITEM_TYPE_SUBS = "subs";
    public static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
    public static final String RESPONSE_CODE = "RESPONSE_CODE";
    public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
    public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
    public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
    boolean mAsyncInProgress = false;
    String mAsyncOperation = "";
    Context mContext;
    boolean mDebugLog = true;
    String mDebugTag = "IabHelper";
    boolean mDisposed = false;
    OnIabPurchaseFinishedListener mPurchaseListener;
    String mPurchasingItemType;
    int mRequestCode;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    boolean mSetupDone = false;
    String mSignatureBase64 = null;
    boolean mSubscriptionsSupported = false;

    public IabHelper(Context context, String string) {
        this.mContext = context.getApplicationContext();
        this.mSignatureBase64 = string;
        this.logDebug("IAB helper created.");
    }

    private void checkNotDisposed() {
        if (this.mDisposed) {
            throw new IllegalStateException("IabHelper was disposed of, so it cannot be used.");
        }
    }

    public static String getResponseDesc(int n) {
        Object object = "0:OK/1:User Canceled/2:Unknown/3:Billing Unavailable/4:Item unavailable/5:Developer Error/6:Error/7:Item Already Owned/8:Item not owned".split("/");
        String[] arrstring = "0:OK/-1001:Remote exception during initialization/-1002:Bad response received/-1003:Purchase signature verification failed/-1004:Send intent failed/-1005:User cancelled/-1006:Unknown purchase response/-1007:Missing token/-1008:Unknown error/-1009:Subscriptions not available/-1010:Invalid consumption attempt".split("/");
        if (n <= -1000) {
            int n2 = -1000 - n;
            if (n2 >= 0 && n2 < arrstring.length) {
                return arrstring[n2];
            }
            object = new StringBuilder();
            object.append(String.valueOf(n));
            object.append(":Unknown IAB Helper Error");
            return object.toString();
        }
        if (n >= 0 && n < ((String[])object).length) {
            return object[n];
        }
        object = new StringBuilder();
        object.append(String.valueOf(n));
        object.append(":Unknown");
        return object.toString();
    }

    void checkSetupDone(String string) {
        if (!this.mSetupDone) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal state for operation (");
            stringBuilder.append(string);
            stringBuilder.append("): IAB helper is not set up.");
            this.logError(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("IAB helper is not set up. Can't perform operation: ");
            stringBuilder.append(string);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void consume(Purchase purchase) throws IabException {
        this.checkNotDisposed();
        this.checkSetupDone("consume");
        if (!purchase.mItemType.equals(ITEM_TYPE_INAPP)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Items of type '");
            stringBuilder.append(purchase.mItemType);
            stringBuilder.append("' can't be consumed.");
            throw new IabException(-1010, stringBuilder.toString());
        }
        try {
            CharSequence charSequence = purchase.getToken();
            String string = purchase.getSku();
            if (charSequence != null && !charSequence.equals("")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Consuming sku: ");
                stringBuilder.append(string);
                stringBuilder.append(", token: ");
                stringBuilder.append((String)charSequence);
                this.logDebug(stringBuilder.toString());
                int n = this.mService.consumePurchase(3, this.mContext.getPackageName(), (String)charSequence);
                if (n == 0) {
                    charSequence = new StringBuilder();
                    charSequence.append("Successfully consumed sku: ");
                    charSequence.append(string);
                    this.logDebug(charSequence.toString());
                    return;
                }
                charSequence = new StringBuilder();
                charSequence.append("Error consuming consuming sku ");
                charSequence.append(string);
                charSequence.append(". ");
                charSequence.append(IabHelper.getResponseDesc(n));
                this.logDebug(charSequence.toString());
                charSequence = new StringBuilder();
                charSequence.append("Error consuming sku ");
                charSequence.append(string);
                throw new IabException(n, charSequence.toString());
            }
            charSequence = new StringBuilder();
            charSequence.append("Can't consume ");
            charSequence.append(string);
            charSequence.append(". No token.");
            this.logError(charSequence.toString());
            charSequence = new StringBuilder();
            charSequence.append("PurchaseInfo is missing token for sku: ");
            charSequence.append(string);
            charSequence.append(" ");
            charSequence.append(purchase);
            throw new IabException(-1007, charSequence.toString());
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Remote exception while consuming. PurchaseInfo: ");
            stringBuilder.append(purchase);
            throw new IabException(-1001, stringBuilder.toString(), (Exception)remoteException);
        }
    }

    public void consumeAsync(Purchase purchase, OnConsumeFinishedListener onConsumeFinishedListener) {
        this.checkNotDisposed();
        this.checkSetupDone("consume");
        ArrayList<Purchase> arrayList = new ArrayList<Purchase>();
        arrayList.add(purchase);
        this.consumeAsyncInternal(arrayList, onConsumeFinishedListener, null);
    }

    public void consumeAsync(List<Purchase> list, OnConsumeMultiFinishedListener onConsumeMultiFinishedListener) {
        this.checkNotDisposed();
        this.checkSetupDone("consume");
        this.consumeAsyncInternal(list, null, onConsumeMultiFinishedListener);
    }

    void consumeAsyncInternal(final List<Purchase> list, final OnConsumeFinishedListener onConsumeFinishedListener, final OnConsumeMultiFinishedListener onConsumeMultiFinishedListener) {
        final Handler handler = new Handler();
        this.flagStartAsync("consume");
        new Thread(new Runnable(){

            @Override
            public void run() {
                final ArrayList<IabResult> arrayList = new ArrayList<IabResult>();
                for (Purchase purchase : list) {
                    try {
                        IabHelper.this.consume(purchase);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Successful consume of sku ");
                        stringBuilder.append(purchase.getSku());
                        arrayList.add(new IabResult(0, stringBuilder.toString()));
                    }
                    catch (IabException iabException) {
                        arrayList.add(iabException.getResult());
                    }
                }
                IabHelper.this.flagEndAsync();
                if (!IabHelper.this.mDisposed && onConsumeFinishedListener != null) {
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                            onConsumeFinishedListener.onConsumeFinished((Purchase)list.get(0), (IabResult)arrayList.get(0));
                        }
                    });
                }
                if (!IabHelper.this.mDisposed && onConsumeMultiFinishedListener != null) {
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                            onConsumeMultiFinishedListener.onConsumeMultiFinished(list, arrayList);
                        }
                    });
                }
            }

        }).start();
    }

    public void dispose() {
        this.logDebug("Disposing.");
        this.mSetupDone = false;
        if (this.mServiceConn != null) {
            this.logDebug("Unbinding from service.");
            if (this.mContext != null) {
                this.mContext.unbindService(this.mServiceConn);
            }
        }
        this.mDisposed = true;
        this.mContext = null;
        this.mServiceConn = null;
        this.mService = null;
        this.mPurchaseListener = null;
    }

    public void enableDebugLogging(boolean bl) {
        this.checkNotDisposed();
        this.mDebugLog = bl;
    }

    public void enableDebugLogging(boolean bl, String string) {
        this.checkNotDisposed();
        this.mDebugLog = bl;
        this.mDebugTag = string;
    }

    public void flagEndAsync() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ending async operation: ");
        stringBuilder.append(this.mAsyncOperation);
        this.logDebug(stringBuilder.toString());
        this.mAsyncOperation = "";
        this.mAsyncInProgress = false;
    }

    void flagStartAsync(String string) {
        if (this.mAsyncInProgress) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't start async operation (");
            stringBuilder.append(string);
            stringBuilder.append(") because another async operation(");
            stringBuilder.append(this.mAsyncOperation);
            stringBuilder.append(") is in progress.");
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.mAsyncOperation = string;
        this.mAsyncInProgress = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Starting async operation: ");
        stringBuilder.append(string);
        this.logDebug(stringBuilder.toString());
    }

    int getResponseCodeFromBundle(Bundle object) {
        if ((object = object.get(RESPONSE_CODE)) == null) {
            this.logDebug("Bundle with null response code, assuming OK (known issue)");
            return 0;
        }
        if (object instanceof Integer) {
            return (Integer)object;
        }
        if (object instanceof Long) {
            return (int)((Long)object).longValue();
        }
        this.logError("Unexpected type for bundle response code.");
        this.logError(object.getClass().getName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected type for bundle response code: ");
        stringBuilder.append(object.getClass().getName());
        throw new RuntimeException(stringBuilder.toString());
    }

    int getResponseCodeFromIntent(Intent object) {
        if ((object = object.getExtras().get(RESPONSE_CODE)) == null) {
            this.logError("Intent with no response code, assuming OK (known issue)");
            return 0;
        }
        if (object instanceof Integer) {
            return (Integer)object;
        }
        if (object instanceof Long) {
            return (int)((Long)object).longValue();
        }
        this.logError("Unexpected type for intent response code.");
        this.logError(object.getClass().getName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected type for intent response code: ");
        stringBuilder.append(object.getClass().getName());
        throw new RuntimeException(stringBuilder.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean handleActivityResult(int n, int n2, Intent object) {
        if (n != this.mRequestCode) {
            return false;
        }
        this.checkNotDisposed();
        this.checkSetupDone("handleActivityResult");
        this.flagEndAsync();
        if (object == null) {
            this.logError("Null data in IAB activity result.");
            object = new IabResult(-1002, "Null data in IAB result");
            if (this.mPurchaseListener == null) return true;
            this.mPurchaseListener.onIabPurchaseFinished((IabResult)object, null);
            return true;
        }
        n = this.getResponseCodeFromIntent((Intent)object);
        Object object2 = object.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);
        String string = object.getStringExtra(RESPONSE_INAPP_SIGNATURE);
        if (n2 == -1 && n == 0) {
            this.logDebug("Successful resultcode from purchase activity.");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Purchase data: ");
            stringBuilder.append((String)object2);
            this.logDebug(stringBuilder.toString());
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Data signature: ");
            stringBuilder2.append(string);
            this.logDebug(stringBuilder2.toString());
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Extras: ");
            stringBuilder3.append((Object)object.getExtras());
            this.logDebug(stringBuilder3.toString());
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("Expected item type: ");
            stringBuilder4.append(this.mPurchasingItemType);
            this.logDebug(stringBuilder4.toString());
            if (object2 != null && string != null) {
                block9 : {
                    try {
                        object = new Purchase(this.mPurchasingItemType, (String)object2, string);
                        String string2 = object.getSku();
                        if (Security.verifyPurchase(this.mSignatureBase64, (String)object2, string)) break block9;
                        object2 = new StringBuilder();
                        object2.append("Purchase signature verification FAILED for sku ");
                        object2.append(string2);
                        this.logError(object2.toString());
                        object2 = new StringBuilder();
                        object2.append("Signature verification failed for sku ");
                        object2.append(string2);
                        object2 = new IabResult(-1003, object2.toString());
                        if (this.mPurchaseListener == null) return true;
                        this.mPurchaseListener.onIabPurchaseFinished((IabResult)object2, (Purchase)object);
                        return true;
                    }
                    catch (JSONException jSONException) {
                        this.logError("Failed to parse purchase data.");
                        jSONException.printStackTrace();
                        IabResult iabResult = new IabResult(-1002, "Failed to parse purchase data.");
                        if (this.mPurchaseListener == null) return true;
                        this.mPurchaseListener.onIabPurchaseFinished(iabResult, null);
                        return true;
                    }
                }
                this.logDebug("Purchase signature successfully verified.");
                if (this.mPurchaseListener == null) return true;
                this.mPurchaseListener.onIabPurchaseFinished(new IabResult(0, "Success"), (Purchase)object);
                return true;
            }
            this.logError("BUG: either purchaseData or dataSignature is null.");
            object2 = new StringBuilder();
            object2.append("Extras: ");
            object2.append(object.getExtras().toString());
            this.logDebug(object2.toString());
            object = new IabResult(-1008, "IAB returned null purchaseData or dataSignature");
            if (this.mPurchaseListener == null) return true;
            this.mPurchaseListener.onIabPurchaseFinished((IabResult)object, null);
            return true;
        }
        if (n2 == -1) {
            object = new StringBuilder();
            object.append("Result code was OK but in-app billing response was not OK: ");
            object.append(IabHelper.getResponseDesc(n));
            this.logDebug(object.toString());
            if (this.mPurchaseListener == null) return true;
            object = new IabResult(n, "Problem purchashing item.");
            this.mPurchaseListener.onIabPurchaseFinished((IabResult)object, null);
            return true;
        }
        if (n2 == 0) {
            object = new StringBuilder();
            object.append("Purchase canceled - Response: ");
            object.append(IabHelper.getResponseDesc(n));
            this.logDebug(object.toString());
            object = new IabResult(-1005, "User canceled.");
            if (this.mPurchaseListener == null) return true;
            this.mPurchaseListener.onIabPurchaseFinished((IabResult)object, null);
            return true;
        }
        object = new StringBuilder();
        object.append("Purchase failed. Result code: ");
        object.append(Integer.toString(n2));
        object.append(". Response: ");
        object.append(IabHelper.getResponseDesc(n));
        this.logError(object.toString());
        object = new IabResult(-1006, "Unknown purchase response.");
        if (this.mPurchaseListener == null) return true;
        this.mPurchaseListener.onIabPurchaseFinished((IabResult)object, null);
        return true;
    }

    public void launchPurchaseFlow(Activity activity, String string, int n, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        this.launchPurchaseFlow(activity, string, n, onIabPurchaseFinishedListener, "");
    }

    public void launchPurchaseFlow(Activity activity, String string, int n, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String string2) {
        this.launchPurchaseFlow(activity, string, ITEM_TYPE_INAPP, n, onIabPurchaseFinishedListener, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void launchPurchaseFlow(Activity object, String string, String charSequence, int n, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String string2) {
        this.checkNotDisposed();
        this.checkSetupDone("launchPurchaseFlow");
        this.flagStartAsync("launchPurchaseFlow");
        if (charSequence.equals(ITEM_TYPE_SUBS) && !this.mSubscriptionsSupported) {
            object = new IabResult(-1009, "Subscriptions are not available.");
            this.flagEndAsync();
            if (onIabPurchaseFinishedListener == null) return;
            onIabPurchaseFinishedListener.onIabPurchaseFinished((IabResult)object, null);
            return;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Constructing buy intent for ");
            stringBuilder.append(string);
            stringBuilder.append(", item type: ");
            stringBuilder.append((String)charSequence);
            this.logDebug(stringBuilder.toString());
            string2 = this.mService.getBuyIntent(3, this.mContext.getPackageName(), string, (String)charSequence, string2);
            int n2 = this.getResponseCodeFromBundle((Bundle)string2);
            if (n2 != 0) {
                object = new StringBuilder();
                object.append("Unable to buy item, Error response: ");
                object.append(IabHelper.getResponseDesc(n2));
                this.logError(object.toString());
                this.flagEndAsync();
                object = new IabResult(n2, "Unable to buy item");
                if (onIabPurchaseFinishedListener == null) return;
                onIabPurchaseFinishedListener.onIabPurchaseFinished((IabResult)object, null);
                return;
            }
            string2 = (PendingIntent)string2.getParcelable(RESPONSE_BUY_INTENT);
            stringBuilder = new StringBuilder();
            stringBuilder.append("Launching buy intent for ");
            stringBuilder.append(string);
            stringBuilder.append(". Request code: ");
            stringBuilder.append(n);
            this.logDebug(stringBuilder.toString());
            this.mRequestCode = n;
            this.mPurchaseListener = onIabPurchaseFinishedListener;
            this.mPurchasingItemType = charSequence;
            object.startIntentSenderForResult(string2.getIntentSender(), n, new Intent(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue());
            return;
        }
        catch (RemoteException remoteException) {
            charSequence = new StringBuilder();
            charSequence.append("RemoteException while launching purchase flow for sku ");
            charSequence.append(string);
            this.logError(charSequence.toString());
            remoteException.printStackTrace();
            this.flagEndAsync();
            IabResult iabResult = new IabResult(-1001, "Remote exception while starting purchase flow");
            if (onIabPurchaseFinishedListener == null) return;
            onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult, null);
            return;
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            charSequence = new StringBuilder();
            charSequence.append("SendIntentException while launching purchase flow for sku ");
            charSequence.append(string);
            this.logError(charSequence.toString());
            sendIntentException.printStackTrace();
            this.flagEndAsync();
            IabResult iabResult = new IabResult(-1004, "Failed to send intent.");
            if (onIabPurchaseFinishedListener == null) return;
            onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult, null);
        }
    }

    public void launchSubscriptionPurchaseFlow(Activity activity, String string, int n, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        this.launchSubscriptionPurchaseFlow(activity, string, n, onIabPurchaseFinishedListener, "");
    }

    public void launchSubscriptionPurchaseFlow(Activity activity, String string, int n, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String string2) {
        this.launchPurchaseFlow(activity, string, ITEM_TYPE_SUBS, n, onIabPurchaseFinishedListener, string2);
    }

    void logDebug(String string) {
        if (this.mDebugLog) {
            Logger.getInstance().debug(this.mDebugTag, string);
        }
    }

    void logError(String string) {
        Logger logger = Logger.getInstance();
        String string2 = this.mDebugTag;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("In-app billing error: ");
        stringBuilder.append(string);
        logger.error(string2, stringBuilder.toString());
    }

    void logWarn(String string) {
        Logger logger = Logger.getInstance();
        String string2 = this.mDebugTag;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("In-app billing warning: ");
        stringBuilder.append(string);
        logger.debug(string2, stringBuilder.toString());
    }

    public Inventory queryInventory(boolean bl, List<String> list) throws IabException {
        return this.queryInventory(bl, list, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Inventory queryInventory(boolean bl, List<String> list, List<String> object) throws IabException {
        this.checkNotDisposed();
        this.checkSetupDone("queryInventory");
        try {
            object = new Inventory();
            int n = this.queryPurchases((Inventory)object, ITEM_TYPE_INAPP);
            if (n != 0) {
                throw new IabException(n, "Error refreshing inventory (querying owned items).");
            }
            if (bl && (n = this.querySkuDetails(ITEM_TYPE_INAPP, (Inventory)object, list)) != 0) {
                throw new IabException(n, "Error refreshing inventory (querying prices of items).");
            }
            if (this.mSubscriptionsSupported) {
                n = this.queryPurchases((Inventory)object, ITEM_TYPE_SUBS);
                if (n != 0) {
                    throw new IabException(n, "Error refreshing inventory (querying owned subscriptions).");
                }
                if (bl && (n = this.querySkuDetails(ITEM_TYPE_SUBS, (Inventory)object, list)) != 0) {
                    throw new IabException(n, "Error refreshing inventory (querying prices of subscriptions).");
                }
            }
            return object;
        }
        catch (JSONException jSONException) {
            throw new IabException(-1002, "Error parsing JSON response while refreshing inventory.", (Exception)jSONException);
        }
        catch (RemoteException remoteException) {
            throw new IabException(-1001, "Remote exception while refreshing inventory.", (Exception)remoteException);
        }
    }

    public void queryInventoryAsync(QueryInventoryFinishedListener queryInventoryFinishedListener) {
        this.queryInventoryAsync(true, null, queryInventoryFinishedListener);
    }

    public void queryInventoryAsync(boolean bl, QueryInventoryFinishedListener queryInventoryFinishedListener) {
        this.queryInventoryAsync(bl, null, queryInventoryFinishedListener);
    }

    public void queryInventoryAsync(final boolean bl, final List<String> list, final QueryInventoryFinishedListener queryInventoryFinishedListener) {
        final Handler handler = new Handler();
        this.checkNotDisposed();
        this.checkSetupDone("queryInventory");
        this.flagStartAsync("refresh inventory");
        new Thread(new Runnable(){

            @Override
            public void run() {
                Inventory inventory;
                final IabResult iabResult = new IabResult(0, "Inventory refresh successful.");
                try {
                    inventory = IabHelper.this.queryInventory(bl, list);
                }
                catch (IabException iabException) {
                    iabResult = iabException.getResult();
                    inventory = null;
                }
                IabHelper.this.flagEndAsync();
                if (!IabHelper.this.mDisposed && queryInventoryFinishedListener != null) {
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                            queryInventoryFinishedListener.onQueryInventoryFinished(iabResult, inventory);
                        }
                    });
                }
            }

        }).start();
    }

    int queryPurchases(Inventory object, String string) throws JSONException, RemoteException {
        block6 : {
            int n;
            CharSequence charSequence = new StringBuilder();
            charSequence.append("Querying owned items, item type: ");
            charSequence.append(string);
            this.logDebug(charSequence.toString());
            charSequence = new StringBuilder();
            charSequence.append("Package name: ");
            charSequence.append(this.mContext.getPackageName());
            this.logDebug(charSequence.toString());
            int n2 = 0;
            charSequence = null;
            boolean bl = false;
            do {
                Serializable serializable = new StringBuilder();
                serializable.append("Calling getPurchases with continuation token: ");
                serializable.append((String)charSequence);
                this.logDebug(serializable.toString());
                charSequence = this.mService.getPurchases(3, this.mContext.getPackageName(), string, (String)charSequence);
                n = this.getResponseCodeFromBundle((Bundle)charSequence);
                serializable = new StringBuilder();
                serializable.append("Owned items response: ");
                serializable.append(String.valueOf(n));
                this.logDebug(serializable.toString());
                if (n != 0) {
                    object = new StringBuilder();
                    object.append("getPurchases() failed: ");
                    object.append(IabHelper.getResponseDesc(n));
                    this.logDebug(object.toString());
                    return n;
                }
                if (!charSequence.containsKey(RESPONSE_INAPP_ITEM_LIST) || !charSequence.containsKey(RESPONSE_INAPP_PURCHASE_DATA_LIST) || !charSequence.containsKey(RESPONSE_INAPP_SIGNATURE_LIST)) break block6;
                serializable = charSequence.getStringArrayList(RESPONSE_INAPP_ITEM_LIST);
                ArrayList arrayList = charSequence.getStringArrayList(RESPONSE_INAPP_PURCHASE_DATA_LIST);
                ArrayList arrayList2 = charSequence.getStringArrayList(RESPONSE_INAPP_SIGNATURE_LIST);
                for (n = 0; n < arrayList.size(); ++n) {
                    CharSequence charSequence2 = (String)arrayList.get(n);
                    Object object2 = (String)arrayList2.get(n);
                    CharSequence charSequence3 = (String)serializable.get(n);
                    if (Security.verifyPurchase(this.mSignatureBase64, (String)charSequence2, (String)object2)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Sku is owned: ");
                        stringBuilder.append((String)charSequence3);
                        this.logDebug(stringBuilder.toString());
                        object2 = new Purchase(string, (String)charSequence2, (String)object2);
                        if (TextUtils.isEmpty((CharSequence)object2.getToken())) {
                            this.logWarn("BUG: empty/null token!");
                            charSequence3 = new StringBuilder();
                            charSequence3.append("Purchase data: ");
                            charSequence3.append((String)charSequence2);
                            this.logDebug(charSequence3.toString());
                        }
                        object.addPurchase((Purchase)object2);
                        continue;
                    }
                    this.logWarn("Purchase signature verification **FAILED**. Not adding item.");
                    charSequence3 = new StringBuilder();
                    charSequence3.append("   Purchase data: ");
                    charSequence3.append((String)charSequence2);
                    this.logDebug(charSequence3.toString());
                    charSequence2 = new StringBuilder();
                    charSequence2.append("   Signature: ");
                    charSequence2.append((String)object2);
                    this.logDebug(charSequence2.toString());
                    bl = true;
                }
                charSequence = charSequence.getString(INAPP_CONTINUATION_TOKEN);
                serializable = new StringBuilder();
                serializable.append("Continuation token: ");
                serializable.append((String)charSequence);
                this.logDebug(serializable.toString());
            } while (!TextUtils.isEmpty((CharSequence)charSequence));
            n = n2;
            if (bl) {
                n = -1003;
            }
            return n;
        }
        this.logError("Bundle returned from getPurchases() doesn't contain required fields.");
        return -1002;
    }

    int querySkuDetails(String charSequence, Inventory inventory, List<String> object) throws RemoteException, JSONException {
        Object object2;
        int n;
        Object object3;
        this.logDebug("Querying SKU details.");
        Iterator iterator = new ArrayList<String>();
        iterator.addAll(inventory.getAllOwnedSkus((String)charSequence));
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                object3 = (String)object.next();
                if (iterator.contains(object3)) continue;
                iterator.add(object3);
            }
        }
        if (iterator.size() == 0) {
            this.logDebug("queryPrices: nothing to do because there are no SKUs.");
            return 0;
        }
        object = new ArrayList<Bundle>();
        int n2 = iterator.size() / 20;
        int n3 = iterator.size() % 20;
        for (n = 0; n < n2; ++n) {
            object3 = new ArrayList();
            int n4 = n * 20;
            object2 = iterator.subList(n4, n4 + 20).iterator();
            while (object2.hasNext()) {
                object3.add((String)object2.next());
            }
            object.add(object3);
        }
        if (n3 != 0) {
            object3 = new Bundle();
            n = n2 * 20;
            iterator = iterator.subList(n, n3 + n).iterator();
            while (iterator.hasNext()) {
                object3.add((String)iterator.next());
            }
            object.add(object3);
            object = object.iterator();
            while (object.hasNext()) {
                iterator = (ArrayList)object.next();
                object3 = new Bundle();
                object3.putStringArrayList(GET_SKU_DETAILS_ITEM_LIST, (ArrayList)((Object)iterator));
                iterator = this.mService.getSkuDetails(3, this.mContext.getPackageName(), (String)charSequence, (Bundle)object3);
                if (!iterator.containsKey(RESPONSE_GET_SKU_DETAILS_LIST)) {
                    n = this.getResponseCodeFromBundle((Bundle)iterator);
                    if (n != 0) {
                        charSequence = new StringBuilder();
                        charSequence.append("getSkuDetails() failed: ");
                        charSequence.append(IabHelper.getResponseDesc(n));
                        this.logDebug(charSequence.toString());
                        return n;
                    }
                    this.logError("getSkuDetails() returned a bundle with neither an error nor a detail list.");
                    return -1002;
                }
                iterator = iterator.getStringArrayList(RESPONSE_GET_SKU_DETAILS_LIST).iterator();
                while (iterator.hasNext()) {
                    object3 = new SkuDetails((String)charSequence, (String)iterator.next());
                    object2 = new StringBuilder();
                    object2.append("Got sku details: ");
                    object2.append(object3);
                    this.logDebug(object2.toString());
                    inventory.addSkuDetails((SkuDetails)object3);
                }
            }
        }
        return 0;
    }

    public void startSetup(final OnIabSetupFinishedListener onIabSetupFinishedListener) {
        this.checkNotDisposed();
        if (this.mSetupDone) {
            throw new IllegalStateException("IAB helper is already set up.");
        }
        this.logDebug("Starting in-app billing setup.");
        this.mServiceConn = new ServiceConnection(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceConnected(ComponentName object, IBinder object2) {
                block8 : {
                    if (IabHelper.this.mDisposed) {
                        return;
                    }
                    IabHelper.this.logDebug("Billing service connected.");
                    IabHelper.this.mService = IInAppBillingService.Stub.asInterface((IBinder)object2);
                    object = IabHelper.this.mContext.getPackageName();
                    try {
                        IabHelper.this.logDebug("Checking for in-app billing 3 support.");
                        int n = IabHelper.this.mService.isBillingSupported(3, (String)object, IabHelper.ITEM_TYPE_INAPP);
                        if (n != 0) {
                            if (onIabSetupFinishedListener != null) {
                                onIabSetupFinishedListener.onIabSetupFinished(new IabResult(n, "Error checking for billing v3 support."));
                            }
                            IabHelper.this.mSubscriptionsSupported = false;
                            return;
                        }
                        object2 = IabHelper.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("In-app billing version 3 supported for ");
                        stringBuilder.append((String)object);
                        object2.logDebug(stringBuilder.toString());
                        n = IabHelper.this.mService.isBillingSupported(3, (String)object, IabHelper.ITEM_TYPE_SUBS);
                        if (n == 0) {
                            IabHelper.this.logDebug("Subscriptions AVAILABLE.");
                            IabHelper.this.mSubscriptionsSupported = true;
                        } else {
                            object = IabHelper.this;
                            object2 = new StringBuilder();
                            object2.append("Subscriptions NOT AVAILABLE. Response: ");
                            object2.append(n);
                            object.logDebug(object2.toString());
                        }
                        IabHelper.this.mSetupDone = true;
                        if (onIabSetupFinishedListener == null) break block8;
                    }
                    catch (RemoteException remoteException) {
                        if (onIabSetupFinishedListener != null) {
                            onIabSetupFinishedListener.onIabSetupFinished(new IabResult(-1001, "RemoteException while setting up in-app billing."));
                        }
                        remoteException.printStackTrace();
                        return;
                    }
                    onIabSetupFinishedListener.onIabSetupFinished(new IabResult(0, "Setup successful."));
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                IabHelper.this.logDebug("Billing service disconnected.");
                IabHelper.this.mService = null;
            }
        };
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        if (this.mContext.getPackageManager() != null && this.mContext.getPackageManager().queryIntentServices(intent, 0) != null && !this.mContext.getPackageManager().queryIntentServices(intent, 0).isEmpty()) {
            this.mContext.bindService(intent, this.mServiceConn, 1);
            return;
        }
        if (onIabSetupFinishedListener != null) {
            onIabSetupFinishedListener.onIabSetupFinished(new IabResult(3, "Billing service unavailable on device."));
        }
    }

    public boolean subscriptionsSupported() {
        this.checkNotDisposed();
        return this.mSubscriptionsSupported;
    }

    public static interface OnConsumeFinishedListener {
        public void onConsumeFinished(Purchase var1, IabResult var2);
    }

    public static interface OnConsumeMultiFinishedListener {
        public void onConsumeMultiFinished(List<Purchase> var1, List<IabResult> var2);
    }

    public static interface OnIabPurchaseFinishedListener {
        public void onIabPurchaseFinished(IabResult var1, Purchase var2);
    }

    public static interface OnIabSetupFinishedListener {
        public void onIabSetupFinished(IabResult var1);
    }

    public static interface QueryInventoryFinishedListener {
        public void onQueryInventoryFinished(IabResult var1, Inventory var2);
    }

}
