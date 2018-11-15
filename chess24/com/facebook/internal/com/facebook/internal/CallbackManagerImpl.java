/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.content.Intent;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.Validate;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class CallbackManagerImpl
implements CallbackManager {
    private static final String INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    private static final String TAG = "CallbackManagerImpl";
    private static Map<Integer, Callback> staticCallbacks = new HashMap<Integer, Callback>();
    private Map<Integer, Callback> callbacks = new HashMap<Integer, Callback>();

    private static Callback getStaticCallback(Integer object) {
        synchronized (CallbackManagerImpl.class) {
            object = staticCallbacks.get(object);
            return object;
        }
    }

    private static boolean isPurchaseIntent(Intent object) {
        boolean bl = false;
        if (object != null) {
            boolean bl2;
            block10 : {
                if ((object = object.getStringExtra(INAPP_PURCHASE_DATA)) == null) {
                    return false;
                }
                try {
                    object = new JSONObject((String)object);
                    bl2 = bl;
                }
                catch (JSONException jSONException) {
                    Log.e((String)TAG, (String)"Error parsing intent data.", (Throwable)jSONException);
                    return false;
                }
                if (!object.has("orderId")) break block10;
                bl2 = bl;
                if (!object.has("packageName")) break block10;
                bl2 = bl;
                if (!object.has("productId")) break block10;
                bl2 = bl;
                if (!object.has("purchaseTime")) break block10;
                bl2 = bl;
                if (!object.has("purchaseState")) break block10;
                bl2 = bl;
                if (!object.has("developerPayload")) break block10;
                boolean bl3 = object.has("purchaseToken");
                bl2 = bl;
                if (!bl3) break block10;
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public static void registerStaticCallback(int n, Callback callback) {
        synchronized (CallbackManagerImpl.class) {
            block5 : {
                Validate.notNull(callback, "callback");
                boolean bl = staticCallbacks.containsKey(n);
                if (!bl) break block5;
                return;
            }
            staticCallbacks.put(n, callback);
            return;
        }
    }

    private static boolean runStaticCallback(int n, int n2, Intent intent) {
        Callback callback = CallbackManagerImpl.getStaticCallback(n);
        if (callback != null) {
            return callback.onActivityResult(n2, intent);
        }
        return false;
    }

    @Override
    public boolean onActivityResult(int n, int n2, Intent intent) {
        Callback callback;
        if (CallbackManagerImpl.isPurchaseIntent(intent)) {
            n = RequestCodeOffset.InAppPurchase.toRequestCode();
        }
        if ((callback = this.callbacks.get(n)) != null) {
            return callback.onActivityResult(n2, intent);
        }
        return CallbackManagerImpl.runStaticCallback(n, n2, intent);
    }

    public void registerCallback(int n, Callback callback) {
        Validate.notNull(callback, "callback");
        this.callbacks.put(n, callback);
    }

    public void unregisterCallback(int n) {
        this.callbacks.remove(n);
    }

    public static interface Callback {
        public boolean onActivityResult(int var1, Intent var2);
    }

    public static enum RequestCodeOffset {
        Login(0),
        Share(1),
        Message(2),
        Like(3),
        GameRequest(4),
        AppGroupCreate(5),
        AppGroupJoin(6),
        AppInvite(7),
        DeviceShare(8),
        InAppPurchase(9);
        
        private final int offset;

        private RequestCodeOffset(int n2) {
            this.offset = n2;
        }

        public int toRequestCode() {
            return FacebookSdk.getCallbackRequestCodeOffset() + this.offset;
        }
    }

}
