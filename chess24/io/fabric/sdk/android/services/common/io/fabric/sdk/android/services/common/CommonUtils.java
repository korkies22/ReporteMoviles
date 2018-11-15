/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.ActivityManager$MemoryInfo
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Resources
 *  android.hardware.Sensor
 *  android.hardware.SensorManager
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Debug
 *  android.os.IBinder
 *  android.os.StatFs
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 */
package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.crypto.Cipher;

public class CommonUtils {
    static final int BYTES_IN_A_GIGABYTE = 1073741824;
    static final int BYTES_IN_A_KILOBYTE = 1024;
    static final int BYTES_IN_A_MEGABYTE = 1048576;
    private static final String CLS_SHARED_PREFERENCES_NAME = "com.crashlytics.prefs";
    static final boolean CLS_TRACE_DEFAULT = false;
    static final String CLS_TRACE_PREFERENCE_NAME = "com.crashlytics.Trace";
    static final String CRASHLYTICS_BUILD_ID = "com.crashlytics.android.build_id";
    public static final int DEVICE_STATE_BETAOS = 8;
    public static final int DEVICE_STATE_COMPROMISEDLIBRARIES = 32;
    public static final int DEVICE_STATE_DEBUGGERATTACHED = 4;
    public static final int DEVICE_STATE_ISSIMULATOR = 1;
    public static final int DEVICE_STATE_JAILBROKEN = 2;
    public static final int DEVICE_STATE_VENDORINTERNAL = 16;
    static final String FABRIC_BUILD_ID = "io.fabric.android.build_id";
    public static final Comparator<File> FILE_MODIFIED_COMPARATOR;
    public static final String GOOGLE_SDK = "google_sdk";
    private static final char[] HEX_VALUES;
    private static final String LOG_PRIORITY_NAME_ASSERT = "A";
    private static final String LOG_PRIORITY_NAME_DEBUG = "D";
    private static final String LOG_PRIORITY_NAME_ERROR = "E";
    private static final String LOG_PRIORITY_NAME_INFO = "I";
    private static final String LOG_PRIORITY_NAME_UNKNOWN = "?";
    private static final String LOG_PRIORITY_NAME_VERBOSE = "V";
    private static final String LOG_PRIORITY_NAME_WARN = "W";
    public static final String SDK = "sdk";
    public static final String SHA1_INSTANCE = "SHA-1";
    public static final String SHA256_INSTANCE = "SHA-256";
    private static final long UNCALCULATED_TOTAL_RAM = -1L;
    private static Boolean clsTrace;
    private static long totalRamInBytes = -1L;

    static {
        HEX_VALUES = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        FILE_MODIFIED_COMPARATOR = new Comparator<File>(){

            @Override
            public int compare(File file, File file2) {
                return (int)(file.lastModified() - file2.lastModified());
            }
        };
    }

    public static long calculateFreeRamInBytes(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager)context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static long calculateUsedDiskSpaceInBytes(String string) {
        string = new StatFs(string);
        long l = string.getBlockSize();
        return (long)string.getBlockCount() * l - l * (long)string.getAvailableBlocks();
    }

    @SuppressLint(value={"MissingPermission"})
    public static boolean canTryConnection(Context context) {
        if (CommonUtils.checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            if ((context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && context.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static boolean checkPermission(Context context, String string) {
        if (context.checkCallingOrSelfPermission(string) == 0) {
            return true;
        }
        return false;
    }

    public static void closeOrLog(Closeable closeable, String string) {
        if (closeable != null) {
            try {
                closeable.close();
                return;
            }
            catch (IOException iOException) {
                Fabric.getLogger().e("Fabric", string, iOException);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    static long convertMemInfoToBytes(String string, String string2, int n) {
        return Long.parseLong(string.split(string2)[0].trim()) * (long)n;
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream, byte[] arrby) throws IOException {
        int n;
        while ((n = inputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n);
        }
    }

    @Deprecated
    public static Cipher createCipher(int n, String string) throws InvalidKeyException {
        throw new InvalidKeyException("This method is deprecated");
    }

    public static /* varargs */ String createInstanceIdFrom(String ... object) {
        Object var3_1 = null;
        if (object != null) {
            if (((String[])object).length == 0) {
                return null;
            }
            Object object2 = new ArrayList<String>();
            for (String string : object) {
                if (string == null) continue;
                object2.add(string.replace("-", "").toLowerCase(Locale.US));
            }
            Collections.sort(object2);
            object = new StringBuilder();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                object.append((String)object2.next());
            }
            object2 = object.toString();
            object = var3_1;
            if (object2.length() > 0) {
                object = CommonUtils.sha1((String)object2);
            }
            return object;
        }
        return null;
    }

    public static byte[] dehexify(String string) {
        int n = string.length();
        byte[] arrby = new byte[n / 2];
        for (int i = 0; i < n; i += 2) {
            arrby[i / 2] = (byte)((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16));
        }
        return arrby;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String extractFieldFromSystemFile(File var0, String var1_4) {
        block8 : {
            block10 : {
                block9 : {
                    var2_5 = var0.exists();
                    var6_6 = null;
                    if (var2_5 == false) return null;
                    try {
                        var4_7 = new BufferedReader(new FileReader(var0), 1024);
                    }
                    catch (Throwable var0_1) {
                        var3_9 = null;
                        break block8;
                    }
                    catch (Exception var4_8) {
                        var1_4 = null;
                        break block9;
                    }
                    do lbl-1000: // 3 sources:
                    {
                        var3_9 = var4_7;
                        var7_17 = var4_7.readLine();
                        var3_9 = var4_7;
                        var5_12 = var6_6;
                        if (var7_17 == null) break block10;
                        var3_9 = var4_7;
                        var5_11 = Pattern.compile("\\s*:\\s*").split(var7_17, 2);
                        var3_9 = var4_7;
                        if (var5_11.length <= 1) ** GOTO lbl-1000
                        var3_9 = var4_7;
                        if (!var5_11[0].equals(var1_4)) continue;
                        break;
                    } while (true);
                    var5_13 = var5_11[1];
                    var3_9 = var4_7;
                    break block10;
                    catch (Exception var3_10) {
                        var1_4 = var4_7;
                        var4_7 = var3_10;
                    }
                }
                var3_9 = var1_4;
                try {
                    var5_15 = Fabric.getLogger();
                    var3_9 = var1_4;
                    var7_17 = new StringBuilder();
                    var3_9 = var1_4;
                    var7_17.append("Error parsing ");
                    var3_9 = var1_4;
                    var7_17.append(var0);
                    var3_9 = var1_4;
                    var5_15.e("Fabric", var7_17.toString(), (Throwable)var4_7);
                    var3_9 = var1_4;
                    var5_16 = var6_6;
                }
                catch (Throwable var0_2) {
                    // empty catch block
                }
            }
            CommonUtils.closeOrLog((Closeable)var3_9, "Failed to close system file reader.");
            return var5_14;
        }
        CommonUtils.closeOrLog((Closeable)var3_9, "Failed to close system file reader.");
        throw var0_3;
    }

    @TargetApi(value=16)
    public static void finishAffinity(Activity activity, int n) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            activity.finishAffinity();
            return;
        }
        activity.setResult(n);
        activity.finish();
    }

    @TargetApi(value=16)
    public static void finishAffinity(Context context, int n) {
        if (context instanceof Activity) {
            CommonUtils.finishAffinity((Activity)context, n);
        }
    }

    public static void flushOrLog(Flushable flushable, String string) {
        if (flushable != null) {
            try {
                flushable.flush();
                return;
            }
            catch (IOException iOException) {
                Fabric.getLogger().e("Fabric", string, iOException);
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String getAppIconHashOrNull(Context object) {
        Object inputStream;
        block8 : {
            Throwable throwable;
            block7 : {
                throwable = null;
                object = inputStream = object.getResources().openRawResource(CommonUtils.getAppIconResourceId((Context)object));
                try {
                    String string = CommonUtils.sha1((InputStream)inputStream);
                    object = inputStream;
                    boolean bl = CommonUtils.isNullOrEmpty(string);
                    object = bl ? throwable : string;
                }
                catch (Exception exception) {
                    break block7;
                }
                CommonUtils.closeOrLog((Closeable)inputStream, "Failed to close icon input stream.");
                return object;
                catch (Throwable throwable2) {
                    inputStream = null;
                    break block8;
                }
                catch (Exception exception) {
                    inputStream = null;
                }
            }
            object = inputStream;
            try {
                Fabric.getLogger().e("Fabric", "Could not calculate hash for app icon.", throwable);
            }
            catch (Throwable throwable3) {
                inputStream = object;
                object = throwable3;
            }
            CommonUtils.closeOrLog((Closeable)inputStream, "Failed to close icon input stream.");
            return null;
        }
        CommonUtils.closeOrLog((Closeable)inputStream, "Failed to close icon input stream.");
        throw object;
    }

    public static int getAppIconResourceId(Context context) {
        return context.getApplicationContext().getApplicationInfo().icon;
    }

    public static ActivityManager.RunningAppProcessInfo getAppProcessInfo(String string, Context object) {
        if ((object = ((ActivityManager)object.getSystemService("activity")).getRunningAppProcesses()) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)object.next();
                if (!runningAppProcessInfo.processName.equals(string)) continue;
                return runningAppProcessInfo;
            }
        }
        return null;
    }

    public static Float getBatteryLevel(Context context) {
        if ((context = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) == null) {
            return null;
        }
        int n = context.getIntExtra("level", -1);
        int n2 = context.getIntExtra("scale", -1);
        return Float.valueOf((float)n / (float)n2);
    }

    public static int getBatteryVelocity(Context object, boolean bl) {
        object = CommonUtils.getBatteryLevel((Context)object);
        if (bl && object != null) {
            if ((double)object.floatValue() >= 99.0) {
                return 3;
            }
            if ((double)object.floatValue() < 99.0) {
                return 2;
            }
            return 0;
        }
        return 1;
    }

    public static boolean getBooleanResourceValue(Context context, String string, boolean bl) {
        Resources resources;
        if (context != null && (resources = context.getResources()) != null) {
            int n = CommonUtils.getResourcesIdentifier(context, string, "bool");
            if (n > 0) {
                return resources.getBoolean(n);
            }
            n = CommonUtils.getResourcesIdentifier(context, string, "string");
            if (n > 0) {
                return Boolean.parseBoolean(context.getString(n));
            }
        }
        return bl;
    }

    public static int getCpuArchitectureInt() {
        return Architecture.getValue().ordinal();
    }

    public static int getDeviceState(Context context) {
        int n = CommonUtils.isEmulator(context) ? 1 : 0;
        int n2 = n;
        if (CommonUtils.isRooted(context)) {
            n2 = n | 2;
        }
        n = n2;
        if (CommonUtils.isDebuggerAttached()) {
            n = n2 | 4;
        }
        return n;
    }

    public static boolean getProximitySensorEnabled(Context context) {
        boolean bl = CommonUtils.isEmulator(context);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (((SensorManager)context.getSystemService("sensor")).getDefaultSensor(8) != null) {
            bl2 = true;
        }
        return bl2;
    }

    public static String getResourcePackageName(Context context) {
        int n = context.getApplicationContext().getApplicationInfo().icon;
        if (n > 0) {
            return context.getResources().getResourcePackageName(n);
        }
        return context.getPackageName();
    }

    public static int getResourcesIdentifier(Context context, String string, String string2) {
        return context.getResources().getIdentifier(string, string2, CommonUtils.getResourcePackageName(context));
    }

    public static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(CLS_SHARED_PREFERENCES_NAME, 0);
    }

    public static String getStringsFileValue(Context context, String string) {
        int n = CommonUtils.getResourcesIdentifier(context, string, "string");
        if (n > 0) {
            return context.getString(n);
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long getTotalRamInBytes() {
        synchronized (CommonUtils.class) {
            if (totalRamInBytes != -1L) return totalRamInBytes;
            long l = 0L;
            String string = CommonUtils.extractFieldFromSystemFile(new File("/proc/meminfo"), "MemTotal");
            long l2 = l;
            if (!TextUtils.isEmpty((CharSequence)string)) {
                string = string.toUpperCase(Locale.US);
                try {
                    if (string.endsWith("KB")) {
                        l2 = CommonUtils.convertMemInfoToBytes(string, "KB", 1024);
                    } else if (string.endsWith("MB")) {
                        l2 = CommonUtils.convertMemInfoToBytes(string, "MB", 1048576);
                    } else if (string.endsWith("GB")) {
                        l2 = CommonUtils.convertMemInfoToBytes(string, "GB", 1073741824);
                    } else {
                        Logger logger = Fabric.getLogger();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected meminfo format while computing RAM: ");
                        stringBuilder.append(string);
                        logger.d("Fabric", stringBuilder.toString());
                        l2 = l;
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    Logger logger = Fabric.getLogger();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected meminfo format while computing RAM: ");
                    stringBuilder.append(string);
                    logger.e("Fabric", stringBuilder.toString(), numberFormatException);
                    l2 = l;
                }
            }
            totalRamInBytes = l2;
            return totalRamInBytes;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String hash(InputStream object, String object2) {
        try {
            object2 = MessageDigest.getInstance((String)object2);
            byte[] arrby = new byte[1024];
            do {
                int n;
                if ((n = object.read(arrby)) == -1) {
                    return CommonUtils.hexify(object2.digest());
                }
                object2.update(arrby, 0, n);
            } while (true);
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Could not calculate hash for app icon.", exception);
            return "";
        }
    }

    private static String hash(String string, String string2) {
        return CommonUtils.hash(string.getBytes(), string2);
    }

    private static String hash(byte[] arrby, String string) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(string);
            messageDigest.update(arrby);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not create hashing algorithm: ");
            stringBuilder.append(string);
            stringBuilder.append(", returning empty string.");
            logger.e("Fabric", stringBuilder.toString(), noSuchAlgorithmException);
            return "";
        }
        return CommonUtils.hexify(messageDigest.digest());
    }

    public static String hexify(byte[] arrby) {
        char[] arrc = new char[arrby.length * 2];
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i] & 255;
            int n2 = i * 2;
            arrc[n2] = HEX_VALUES[n >>> 4];
            arrc[n2 + 1] = HEX_VALUES[n & 15];
        }
        return new String(arrc);
    }

    public static void hideKeyboard(Context context, View view) {
        if ((context = (InputMethodManager)context.getSystemService("input_method")) != null) {
            context.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isAppDebuggable(Context context) {
        if ((context.getApplicationInfo().flags & 2) != 0) {
            return true;
        }
        return false;
    }

    public static boolean isClsTrace(Context context) {
        if (clsTrace == null) {
            clsTrace = CommonUtils.getBooleanResourceValue(context, CLS_TRACE_PREFERENCE_NAME, false);
        }
        return clsTrace;
    }

    public static boolean isDebuggerAttached() {
        if (!Debug.isDebuggerConnected() && !Debug.waitingForDebugger()) {
            return false;
        }
        return true;
    }

    public static boolean isEmulator(Context object) {
        object = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)"android_id");
        if (!SDK.equals(Build.PRODUCT) && !GOOGLE_SDK.equals(Build.PRODUCT) && object != null) {
            return false;
        }
        return true;
    }

    @Deprecated
    public static boolean isLoggingEnabled(Context context) {
        return false;
    }

    public static boolean isNullOrEmpty(String string) {
        if (string != null && string.length() != 0) {
            return false;
        }
        return true;
    }

    public static boolean isRooted(Context object) {
        boolean bl = CommonUtils.isEmulator((Context)object);
        object = Build.TAGS;
        if (!bl && object != null && object.contains("test-keys")) {
            return true;
        }
        if (new File("/system/app/Superuser.apk").exists()) {
            return true;
        }
        object = new File("/system/xbin/su");
        if (!bl && object.exists()) {
            return true;
        }
        return false;
    }

    public static void logControlled(Context context, int n, String string, String string2) {
        if (CommonUtils.isClsTrace(context)) {
            Fabric.getLogger().log(n, "Fabric", string2);
        }
    }

    public static void logControlled(Context context, String string) {
        if (CommonUtils.isClsTrace(context)) {
            Fabric.getLogger().d("Fabric", string);
        }
    }

    public static void logControlledError(Context context, String string, Throwable throwable) {
        if (CommonUtils.isClsTrace(context)) {
            Fabric.getLogger().e("Fabric", string);
        }
    }

    public static void logOrThrowIllegalArgumentException(String string, String string2) {
        if (Fabric.isDebuggable()) {
            throw new IllegalArgumentException(string2);
        }
        Fabric.getLogger().w(string, string2);
    }

    public static void logOrThrowIllegalStateException(String string, String string2) {
        if (Fabric.isDebuggable()) {
            throw new IllegalStateException(string2);
        }
        Fabric.getLogger().w(string, string2);
    }

    public static String logPriorityToString(int n) {
        switch (n) {
            default: {
                return LOG_PRIORITY_NAME_UNKNOWN;
            }
            case 7: {
                return LOG_PRIORITY_NAME_ASSERT;
            }
            case 6: {
                return LOG_PRIORITY_NAME_ERROR;
            }
            case 5: {
                return LOG_PRIORITY_NAME_WARN;
            }
            case 4: {
                return LOG_PRIORITY_NAME_INFO;
            }
            case 3: {
                return LOG_PRIORITY_NAME_DEBUG;
            }
            case 2: 
        }
        return LOG_PRIORITY_NAME_VERBOSE;
    }

    public static void openKeyboard(Context context, View view) {
        if ((context = (InputMethodManager)context.getSystemService("input_method")) != null) {
            context.showSoftInputFromInputMethod(view.getWindowToken(), 0);
        }
    }

    public static String padWithZerosToMaxIntWidth(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("value must be zero or greater");
        }
        return String.format(Locale.US, "%1$10s", n).replace(' ', '0');
    }

    public static String resolveBuildId(Context object) {
        int n;
        int n2 = n = CommonUtils.getResourcesIdentifier(object, FABRIC_BUILD_ID, "string");
        if (n == 0) {
            n2 = CommonUtils.getResourcesIdentifier(object, CRASHLYTICS_BUILD_ID, "string");
        }
        if (n2 != 0) {
            object = object.getResources().getString(n2);
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Build ID is: ");
            stringBuilder.append((String)object);
            logger.d("Fabric", stringBuilder.toString());
            return object;
        }
        return null;
    }

    public static String sha1(InputStream inputStream) {
        return CommonUtils.hash(inputStream, SHA1_INSTANCE);
    }

    public static String sha1(String string) {
        return CommonUtils.hash(string, SHA1_INSTANCE);
    }

    public static String sha256(String string) {
        return CommonUtils.hash(string, SHA256_INSTANCE);
    }

    public static String streamToString(InputStream closeable) throws IOException {
        if ((closeable = new Scanner((InputStream)closeable).useDelimiter("\\A")).hasNext()) {
            return closeable.next();
        }
        return "";
    }

    public static boolean stringsEqualIncludingNull(String string, String string2) {
        if (string == string2) {
            return true;
        }
        if (string != null) {
            return string.equals(string2);
        }
        return false;
    }

    static enum Architecture {
        X86_32,
        X86_64,
        ARM_UNKNOWN,
        PPC,
        PPC64,
        ARMV6,
        ARMV7,
        UNKNOWN,
        ARMV7S,
        ARM64;
        
        private static final Map<String, Architecture> matcher;

        static {
            matcher = new HashMap<String, Architecture>(4);
            matcher.put("armeabi-v7a", ARMV7);
            matcher.put("armeabi", ARMV6);
            matcher.put("arm64-v8a", ARM64);
            matcher.put("x86", X86_32);
        }

        private Architecture() {
        }

        static Architecture getValue() {
            Object object = Build.CPU_ABI;
            if (TextUtils.isEmpty((CharSequence)object)) {
                Fabric.getLogger().d("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
                return UNKNOWN;
            }
            object = object.toLowerCase(Locale.US);
            Architecture architecture = matcher.get(object);
            object = architecture;
            if (architecture == null) {
                object = UNKNOWN;
            }
            return object;
        }
    }

}
