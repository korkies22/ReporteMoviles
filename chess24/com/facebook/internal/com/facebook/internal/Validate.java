/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.os.Looper
 *  android.util.Log
 */
package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdkNotInitializedException;
import com.facebook.internal.Utility;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class Validate {
    private static final String CONTENT_PROVIDER_BASE = "com.facebook.app.FacebookContentProvider";
    private static final String CONTENT_PROVIDER_NOT_FOUND_REASON = "A ContentProvider for this app was not set up in the AndroidManifest.xml, please add %s as a provider to your AndroidManifest.xml file. See https://developers.facebook.com/docs/sharing/android for more info.";
    private static final String CUSTOM_TAB_REDIRECT_ACTIVITY_NOT_FOUND_REASON = "FacebookActivity is declared incorrectly in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.";
    private static final String FACEBOOK_ACTIVITY_NOT_FOUND_REASON = "FacebookActivity is not declared in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.";
    private static final String NO_INTERNET_PERMISSION_REASON = "No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.";
    private static final String TAG = "com.facebook.internal.Validate";

    public static void checkCustomTabRedirectActivity(Context context) {
        Validate.checkCustomTabRedirectActivity(context, true);
    }

    public static void checkCustomTabRedirectActivity(Context context, boolean bl) {
        if (!Validate.hasCustomTabRedirectActivity(context)) {
            if (bl) {
                throw new IllegalStateException(CUSTOM_TAB_REDIRECT_ACTIVITY_NOT_FOUND_REASON);
            }
            Log.w((String)TAG, (String)CUSTOM_TAB_REDIRECT_ACTIVITY_NOT_FOUND_REASON);
        }
    }

    public static void containsNoNullOrEmpty(Collection<String> object, String string) {
        Validate.notNull(object, string);
        object = object.iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            if (string2 == null) {
                object = new StringBuilder();
                object.append("Container '");
                object.append(string);
                object.append("' cannot contain null values");
                throw new NullPointerException(object.toString());
            }
            if (string2.length() != 0) continue;
            object = new StringBuilder();
            object.append("Container '");
            object.append(string);
            object.append("' cannot contain empty values");
            throw new IllegalArgumentException(object.toString());
        }
    }

    public static <T> void containsNoNulls(Collection<T> object, String string) {
        Validate.notNull(object, string);
        object = object.iterator();
        while (object.hasNext()) {
            if (object.next() != null) continue;
            object = new StringBuilder();
            object.append("Container '");
            object.append(string);
            object.append("' cannot contain null values");
            throw new NullPointerException(object.toString());
        }
    }

    public static String hasAppID() {
        String string = FacebookSdk.getApplicationId();
        if (string == null) {
            throw new IllegalStateException("No App ID found, please set the App ID.");
        }
        return string;
    }

    public static boolean hasBluetoothPermission(Context context) {
        if (Validate.hasPermission(context, "android.permission.BLUETOOTH") && Validate.hasPermission(context, "android.permission.BLUETOOTH_ADMIN")) {
            return true;
        }
        return false;
    }

    public static boolean hasChangeWifiStatePermission(Context context) {
        return Validate.hasPermission(context, "android.permission.CHANGE_WIFI_STATE");
    }

    public static String hasClientToken() {
        String string = FacebookSdk.getClientToken();
        if (string == null) {
            throw new IllegalStateException("No Client Token found, please set the Client Token.");
        }
        return string;
    }

    public static void hasContentProvider(Context context) {
        Validate.notNull((Object)context, "context");
        String string = Validate.hasAppID();
        context = context.getPackageManager();
        if (context != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CONTENT_PROVIDER_BASE);
            stringBuilder.append(string);
            string = stringBuilder.toString();
            if (context.resolveContentProvider(string, 0) == null) {
                throw new IllegalStateException(String.format(CONTENT_PROVIDER_NOT_FOUND_REASON, string));
            }
        }
    }

    public static boolean hasCustomTabRedirectActivity(Context context) {
        Intent intent;
        Validate.notNull((Object)context, "context");
        Object object = context.getPackageManager();
        if (object != null) {
            intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addCategory("android.intent.category.BROWSABLE");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fb");
            stringBuilder.append(FacebookSdk.getApplicationId());
            stringBuilder.append("://authorize");
            intent.setData(Uri.parse((String)stringBuilder.toString()));
            object = object.queryIntentActivities(intent, 64);
        } else {
            object = null;
        }
        boolean bl = false;
        if (object != null) {
            object = object.iterator();
            bl = false;
            while (object.hasNext()) {
                intent = ((ResolveInfo)object.next()).activityInfo;
                if (intent.name.equals("com.facebook.CustomTabActivity") && intent.packageName.equals(context.getPackageName())) {
                    bl = true;
                    continue;
                }
                return false;
            }
        }
        return bl;
    }

    public static void hasFacebookActivity(Context context) {
        Validate.hasFacebookActivity(context, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void hasFacebookActivity(Context context, boolean bl) {
        block5 : {
            Validate.notNull((Object)context, "context");
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                context = new ComponentName(context, "com.facebook.FacebookActivity");
                try {
                    context = packageManager.getActivityInfo((ComponentName)context, 1);
                    break block5;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {}
            }
            context = null;
        }
        if (context == null) {
            if (bl) {
                throw new IllegalStateException(FACEBOOK_ACTIVITY_NOT_FOUND_REASON);
            }
            Log.w((String)TAG, (String)FACEBOOK_ACTIVITY_NOT_FOUND_REASON);
        }
    }

    public static void hasInternetPermissions(Context context) {
        Validate.hasInternetPermissions(context, true);
    }

    public static void hasInternetPermissions(Context context, boolean bl) {
        Validate.notNull((Object)context, "context");
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") == -1) {
            if (bl) {
                throw new IllegalStateException(NO_INTERNET_PERMISSION_REASON);
            }
            Log.w((String)TAG, (String)NO_INTERNET_PERMISSION_REASON);
        }
    }

    public static boolean hasLocationPermission(Context context) {
        if (!Validate.hasPermission(context, "android.permission.ACCESS_COARSE_LOCATION") && !Validate.hasPermission(context, "android.permission.ACCESS_FINE_LOCATION")) {
            return false;
        }
        return true;
    }

    public static boolean hasPermission(Context context, String string) {
        if (context.checkCallingOrSelfPermission(string) == 0) {
            return true;
        }
        return false;
    }

    public static boolean hasWiFiPermission(Context context) {
        return Validate.hasPermission(context, "android.permission.ACCESS_WIFI_STATE");
    }

    public static <T> void notEmpty(Collection<T> object, String string) {
        if (object.isEmpty()) {
            object = new StringBuilder();
            object.append("Container '");
            object.append(string);
            object.append("' cannot be empty");
            throw new IllegalArgumentException(object.toString());
        }
    }

    public static <T> void notEmptyAndContainsNoNulls(Collection<T> collection, String string) {
        Validate.containsNoNulls(collection, string);
        Validate.notEmpty(collection, string);
    }

    public static void notNull(Object object, String string) {
        if (object == null) {
            object = new StringBuilder();
            object.append("Argument '");
            object.append(string);
            object.append("' cannot be null");
            throw new NullPointerException(object.toString());
        }
    }

    public static void notNullOrEmpty(String charSequence, String string) {
        if (Utility.isNullOrEmpty((String)charSequence)) {
            charSequence = new StringBuilder();
            charSequence.append("Argument '");
            charSequence.append(string);
            charSequence.append("' cannot be null or empty");
            throw new IllegalArgumentException(charSequence.toString());
        }
    }

    public static /* varargs */ void oneOf(Object object, String string, Object ... arrobject) {
        for (Object object2 : arrobject) {
            if (!(object2 != null ? object2.equals(object) : object == null)) continue;
            return;
        }
        object = new StringBuilder();
        object.append("Argument '");
        object.append(string);
        object.append("' was not one of the allowed values");
        throw new IllegalArgumentException(object.toString());
    }

    public static void runningOnUiThread() {
        if (!Looper.getMainLooper().equals((Object)Looper.myLooper())) {
            throw new FacebookException("This method should be called from the UI thread");
        }
    }

    public static void sdkInitialized() {
        if (!FacebookSdk.isInitialized()) {
            throw new FacebookSdkNotInitializedException("The SDK has not been initialized, make sure to call FacebookSdk.sdkInitialize() first.");
        }
    }
}
