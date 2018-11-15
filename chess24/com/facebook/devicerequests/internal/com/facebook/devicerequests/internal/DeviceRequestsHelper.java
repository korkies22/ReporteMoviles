/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.net.nsd.NsdManager
 *  android.net.nsd.NsdManager$RegistrationListener
 *  android.net.nsd.NsdServiceInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.devicerequests.internal;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import com.facebook.FacebookSdk;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.SmartLoginOption;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceRequestsHelper {
    static final String DEVICE_INFO_DEVICE = "device";
    static final String DEVICE_INFO_MODEL = "model";
    public static final String DEVICE_INFO_PARAM = "device_info";
    static final String SDK_FLAVOR = "android";
    static final String SDK_HEADER = "fbsdk";
    static final String SERVICE_TYPE = "_fb._tcp.";
    private static HashMap<String, NsdManager.RegistrationListener> deviceRequestsListeners = new HashMap();

    public static void cleanUpAdvertisementService(String string) {
        DeviceRequestsHelper.cleanUpAdvertisementServiceImpl(string);
    }

    @TargetApi(value=16)
    private static void cleanUpAdvertisementServiceImpl(String string) {
        NsdManager.RegistrationListener registrationListener = deviceRequestsListeners.get(string);
        if (registrationListener != null) {
            ((NsdManager)FacebookSdk.getApplicationContext().getSystemService("servicediscovery")).unregisterService(registrationListener);
            deviceRequestsListeners.remove(string);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Bitmap generateQRCode(String var0) {
        var6_3 = new EnumMap<EncodeHintType, Integer>(EncodeHintType.class);
        var6_3.put((EncodeHintType)EncodeHintType.MARGIN, 2);
        try {
            var6_3 = new MultiFormatWriter().encode((String)var0, BarcodeFormat.QR_CODE, 200, 200, (Map<EncodeHintType, ?>)var6_3);
            var4_4 = var6_3.getHeight();
            var5_5 = var6_3.getWidth();
            var0 = new int[var4_4 * var5_5];
            var1_6 = 0;
            ** GOTO lbl18
        }
        catch (WriterException var0_1) {
            return null;
        }
lbl-1000: // 2 sources:
        {
            if (var2_7 < var5_5) {
                var3_8 = var6_3.get(var2_7, var1_6) != false ? -16777216 : -1;
                var0[var1_6 * var5_5 + var2_7] = var3_8;
                ++var2_7;
                continue;
            }
            ++var1_6;
lbl18: // 2 sources:
            if (var1_6 >= var4_4) break;
            var2_7 = 0;
            ** while (true)
        }
        var6_3 = Bitmap.createBitmap((int)var5_5, (int)var4_4, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        try {
            var6_3.setPixels(var0, 0, var5_5, 0, 0, var5_5, var4_4);
            return var6_3;
        }
        catch (WriterException var0_2) {
            return var6_3;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getDeviceInfo() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(DEVICE_INFO_DEVICE, (Object)Build.DEVICE);
            jSONObject.put(DEVICE_INFO_MODEL, (Object)Build.MODEL);
        }
        catch (JSONException jSONException) {
            return jSONObject.toString();
        }
        do {
            return jSONObject.toString();
            break;
        } while (true);
    }

    public static boolean isAvailable() {
        if (Build.VERSION.SDK_INT >= 16 && FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId()).getSmartLoginOptions().contains((Object)SmartLoginOption.Enabled)) {
            return true;
        }
        return false;
    }

    public static boolean startAdvertisementService(String string) {
        if (DeviceRequestsHelper.isAvailable()) {
            return DeviceRequestsHelper.startAdvertisementServiceImpl(string);
        }
        return false;
    }

    @TargetApi(value=16)
    private static boolean startAdvertisementServiceImpl(String string) {
        if (deviceRequestsListeners.containsKey(string)) {
            return true;
        }
        Object object = String.format("%s_%s_%s", SDK_HEADER, String.format("%s-%s", SDK_FLAVOR, FacebookSdk.getSdkVersion().replace('.', '|')), string);
        NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
        nsdServiceInfo.setServiceType(SERVICE_TYPE);
        nsdServiceInfo.setServiceName((String)object);
        nsdServiceInfo.setPort(80);
        NsdManager nsdManager = (NsdManager)FacebookSdk.getApplicationContext().getSystemService("servicediscovery");
        object = new NsdManager.RegistrationListener((String)object, string){
            final /* synthetic */ String val$nsdServiceName;
            final /* synthetic */ String val$userCode;
            {
                this.val$nsdServiceName = string;
                this.val$userCode = string2;
            }

            public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int n) {
                DeviceRequestsHelper.cleanUpAdvertisementService(this.val$userCode);
            }

            public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
                if (!this.val$nsdServiceName.equals(nsdServiceInfo.getServiceName())) {
                    DeviceRequestsHelper.cleanUpAdvertisementService(this.val$userCode);
                }
            }

            public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
            }

            public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int n) {
            }
        };
        deviceRequestsListeners.put(string, (NsdManager.RegistrationListener)object);
        nsdManager.registerService(nsdServiceInfo, 1, (NsdManager.RegistrationListener)object);
        return true;
    }

}
