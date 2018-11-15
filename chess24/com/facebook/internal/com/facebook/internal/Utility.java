/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Parcel
 *  android.os.StatFs
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.Display
 *  android.view.WindowManager
 *  android.view.autofill.AutofillManager
 *  android.webkit.CookieManager
 *  android.webkit.CookieSyncManager
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.json.JSONTokener
 */
package com.facebook.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.autofill.AutofillManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ProfileInformationCache;
import com.facebook.internal.Validate;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utility {
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
    private static final String EXTRA_APP_EVENTS_INFO_FORMAT_VERSION = "a2";
    private static final int GINGERBREAD_MR1 = 10;
    private static final String HASH_ALGORITHM_MD5 = "MD5";
    private static final String HASH_ALGORITHM_SHA1 = "SHA-1";
    static final String LOG_TAG = "FacebookSDK";
    private static final int REFRESH_TIME_FOR_EXTENDED_DEVICE_INFO_MILLIS = 1800000;
    private static final String URL_SCHEME = "https";
    private static final String UTF8 = "UTF-8";
    private static long availableExternalStorageGB = -1L;
    private static String carrierName = "NoCarrier";
    private static String deviceTimeZoneName = "";
    private static String deviceTimezoneAbbreviation = "";
    private static final String noCarrierConstant = "NoCarrier";
    private static int numCPUCores = 0;
    private static long timestampOfLastCheck = -1L;
    private static long totalExternalStorageGB = -1L;

    public static <T> boolean areObjectsEqual(T t, T t2) {
        if (t == null) {
            if (t2 == null) {
                return true;
            }
            return false;
        }
        return t.equals(t2);
    }

    public static /* varargs */ <T> ArrayList<T> arrayList(T ... arrT) {
        ArrayList<T> arrayList = new ArrayList<T>(arrT.length);
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrT[i]);
        }
        return arrayList;
    }

    public static /* varargs */ <T> List<T> asListNoNulls(T ... arrT) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (T t : arrT) {
            if (t == null) continue;
            arrayList.add(t);
        }
        return arrayList;
    }

    public static JSONObject awaitGetGraphMeRequestWithCache(String object) {
        JSONObject jSONObject = ProfileInformationCache.getProfileInformation((String)object);
        if (jSONObject != null) {
            return jSONObject;
        }
        if ((object = Utility.getGraphMeRequestWithCache((String)object).executeAndWait()).getError() != null) {
            return null;
        }
        return object.getJSONObject();
    }

    public static Uri buildUri(String object, String string2, Bundle bundle) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(URL_SCHEME);
        builder.authority((String)object);
        builder.path(string2);
        if (bundle != null) {
            for (String string2 : bundle.keySet()) {
                Object object2 = bundle.get(string2);
                if (!(object2 instanceof String)) continue;
                builder.appendQueryParameter(string2, (String)object2);
            }
        }
        return builder.build();
    }

    public static void clearCaches(Context context) {
        ImageDownloader.clearCache(context);
    }

    private static void clearCookiesForDomain(Context context, String string) {
        CookieSyncManager.createInstance((Context)context).sync();
        context = CookieManager.getInstance();
        String[] arrstring = context.getCookie(string);
        if (arrstring == null) {
            return;
        }
        arrstring = arrstring.split(";");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            String[] arrstring2 = arrstring[i].split("=");
            if (arrstring2.length <= 0) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(arrstring2[0].trim());
            stringBuilder.append("=;expires=Sat, 1 Jan 2000 00:00:01 UTC;");
            context.setCookie(string, stringBuilder.toString());
        }
        context.removeExpiredCookie();
    }

    public static void clearFacebookCookies(Context context) {
        Utility.clearCookiesForDomain(context, "facebook.com");
        Utility.clearCookiesForDomain(context, ".facebook.com");
        Utility.clearCookiesForDomain(context, "https://facebook.com");
        Utility.clearCookiesForDomain(context, "https://.facebook.com");
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
        catch (IOException iOException) {
            return;
        }
    }

    public static String coerceValueIfNullOrEmpty(String string, String string2) {
        if (Utility.isNullOrEmpty(string)) {
            return string2;
        }
        return string;
    }

    private static long convertBytesToGB(double d) {
        return Math.round(d / 1.073741824E9);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Map<String, Object> convertJSONObjectToHashMap(JSONObject jSONObject) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        JSONArray jSONArray = jSONObject.names();
        int n = 0;
        do {
            if (n >= jSONArray.length()) {
                return hashMap;
            }
            try {
                Map<String, Object> map;
                String string = jSONArray.getString(n);
                Map<String, Object> map2 = map = jSONObject.get(string);
                if (map instanceof JSONObject) {
                    map2 = Utility.convertJSONObjectToHashMap((JSONObject)map);
                }
                hashMap.put(string, map2);
            }
            catch (JSONException jSONException) {}
            ++n;
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static int copyAndCloseInputStream(InputStream inputStream, OutputStream closeable) throws IOException {
        int n;
        byte[] arrby;
        Object object;
        int n2;
        block7 : {
            block8 : {
                object = new BufferedInputStream(inputStream);
                try {
                    arrby = new byte[8192];
                    n = 0;
                    break block7;
                }
                catch (Throwable throwable) {
                    closeable = object;
                    object = throwable;
                    break block8;
                }
                catch (Throwable throwable) {
                    closeable = null;
                }
            }
            if (closeable != null) {
                ((BufferedInputStream)closeable).close();
            }
            if (inputStream == null) throw object;
            inputStream.close();
            throw object;
        }
        while ((n2 = object.read(arrby)) != -1) {
            closeable.write(arrby, 0, n2);
            n += n2;
        }
        if (object != null) {
            object.close();
        }
        if (inputStream == null) return n;
        inputStream.close();
        return n;
    }

    public static void deleteDirectory(File file) {
        File[] arrfile;
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory() && (arrfile = file.listFiles()) != null) {
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                Utility.deleteDirectory(arrfile[i]);
            }
        }
        file.delete();
    }

    public static void disconnectQuietly(URLConnection uRLConnection) {
        if (uRLConnection != null && uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection)uRLConnection).disconnect();
        }
    }

    private static boolean externalStorageExists() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static <T> List<T> filter(List<T> object, Predicate<T> predicate) {
        if (object == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            Object e = object.next();
            if (!predicate.apply(e)) continue;
            arrayList.add(e);
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public static String generateRandomString(int n) {
        return new BigInteger(n * 5, new Random()).toString(32);
    }

    public static String getActivityName(Context context) {
        if (context == null) {
            return "null";
        }
        if (context == context.getApplicationContext()) {
            return "unknown";
        }
        return context.getClass().getSimpleName();
    }

    public static Date getBundleLongAsDate(Bundle object, String string, Date date) {
        block9 : {
            long l;
            block8 : {
                block7 : {
                    if (object == null) {
                        return null;
                    }
                    if (!((object = object.get(string)) instanceof Long)) break block7;
                    l = (Long)object;
                    break block8;
                }
                if (!(object instanceof String)) break block9;
                try {
                    l = Long.parseLong((String)object);
                }
                catch (NumberFormatException numberFormatException) {
                    return null;
                }
            }
            if (l == 0L) {
                return new Date(Long.MAX_VALUE);
            }
            return new Date(date.getTime() + l * 1000L);
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static long getContentSize(Uri uri) {
        Cursor cursor;
        void var0_3;
        block4 : {
            long l;
            cursor = FacebookSdk.getApplicationContext().getContentResolver().query(uri, null, null, null, null);
            try {
                int n = cursor.getColumnIndex("_size");
                cursor.moveToFirst();
                l = cursor.getLong(n);
                if (cursor == null) return l;
            }
            catch (Throwable throwable) {}
            cursor.close();
            return l;
            break block4;
            catch (Throwable throwable) {
                cursor = null;
            }
        }
        if (cursor == null) throw var0_3;
        cursor.close();
        throw var0_3;
    }

    private static GraphRequest getGraphMeRequestWithCache(String string) {
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id,name,first_name,middle_name,last_name,link");
        bundle.putString("access_token", string);
        return new GraphRequest(null, "me", bundle, HttpMethod.GET, null);
    }

    public static void getGraphMeRequestWithCacheAsync(String object, GraphMeRequestWithCacheCallback object2) {
        JSONObject jSONObject = ProfileInformationCache.getProfileInformation((String)object);
        if (jSONObject != null) {
            object2.onSuccess(jSONObject);
            return;
        }
        object2 = new GraphRequest.Callback((GraphMeRequestWithCacheCallback)object2, (String)object){
            final /* synthetic */ String val$accessToken;
            final /* synthetic */ GraphMeRequestWithCacheCallback val$callback;
            {
                this.val$callback = graphMeRequestWithCacheCallback;
                this.val$accessToken = string;
            }

            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (graphResponse.getError() != null) {
                    this.val$callback.onFailure(graphResponse.getError().getException());
                    return;
                }
                ProfileInformationCache.putProfileInformation(this.val$accessToken, graphResponse.getJSONObject());
                this.val$callback.onSuccess(graphResponse.getJSONObject());
            }
        };
        object = Utility.getGraphMeRequestWithCache((String)object);
        object.setCallback((GraphRequest.Callback)object2);
        object.executeAsync();
    }

    public static String getMetadataApplicationId(Context context) {
        Validate.notNull((Object)context, "context");
        FacebookSdk.sdkInitialize(context);
        return FacebookSdk.getApplicationId();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static /* varargs */ Method getMethodQuietly(Class<?> genericDeclaration, String string, Class<?> ... arrclass) {
        try {
            void var1_3;
            void var2_4;
            return genericDeclaration.getMethod((String)var1_3, (Class<?>)var2_4);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static /* varargs */ Method getMethodQuietly(String object, String string, Class<?> ... arrclass) {
        try {
            return Utility.getMethodQuietly(Class.forName((String)object), string, arrclass);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static Object getStringPropertyAsJSON(JSONObject object, String object2, String string) throws JSONException {
        object2 = object.opt((String)object2);
        object = object2;
        if (object2 != null) {
            object = object2;
            if (object2 instanceof String) {
                object = new JSONTokener((String)object2).nextValue();
            }
        }
        if (object != null && !(object instanceof JSONObject) && !(object instanceof JSONArray)) {
            if (string != null) {
                object2 = new JSONObject();
                object2.putOpt(string, object);
                return object2;
            }
            throw new FacebookException("Got an unexpected non-JSON object.");
        }
        return object;
    }

    public static String getUriString(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public static PermissionsPair handlePermissionResponse(JSONObject jSONObject) throws JSONException {
        jSONObject = jSONObject.getJSONObject("permissions").getJSONArray("data");
        ArrayList<String> arrayList = new ArrayList<String>(jSONObject.length());
        ArrayList<String> arrayList2 = new ArrayList<String>(jSONObject.length());
        for (int i = 0; i < jSONObject.length(); ++i) {
            Object object = jSONObject.optJSONObject(i);
            String string = object.optString("permission");
            if (string == null || string.equals("installed") || (object = object.optString("status")) == null) continue;
            if (object.equals("granted")) {
                arrayList.add(string);
                continue;
            }
            if (!object.equals("declined")) continue;
            arrayList2.add(string);
        }
        return new PermissionsPair(arrayList, arrayList2);
    }

    public static boolean hasSameId(JSONObject object, JSONObject object2) {
        if (object != null && object2 != null && object.has("id")) {
            if (!object2.has("id")) {
                return false;
            }
            if (object.equals(object2)) {
                return true;
            }
            object = object.optString("id");
            object2 = object2.optString("id");
            if (object != null) {
                if (object2 == null) {
                    return false;
                }
                return object.equals(object2);
            }
            return false;
        }
        return false;
    }

    private static String hashBytes(MessageDigest arrby, byte[] object) {
        arrby.update((byte[])object);
        arrby = arrby.digest();
        object = new StringBuilder();
        for (byte by : arrby) {
            object.append(Integer.toHexString(by >> 4 & 15));
            object.append(Integer.toHexString(by >> 0 & 15));
        }
        return object.toString();
    }

    public static /* varargs */ <T> HashSet<T> hashSet(T ... arrT) {
        HashSet<T> hashSet = new HashSet<T>(arrT.length);
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            hashSet.add(arrT[i]);
        }
        return hashSet;
    }

    private static String hashWithAlgorithm(String string, String string2) {
        return Utility.hashWithAlgorithm(string, string2.getBytes());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String hashWithAlgorithm(String object, byte[] arrby) {
        try {
            object = MessageDigest.getInstance((String)object);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return null;
        }
        return Utility.hashBytes((MessageDigest)object, arrby);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static int[] intersectRanges(int[] var0, int[] var1_1) {
        if (var0 == null) {
            return var1_1;
        }
        if (var1_1 == null) {
            return var0;
        }
        var5_2 = 0;
        var9_3 = new int[var0.length + var1_1.length];
        var4_5 = var6_4 = 0;
        do {
            block11 : {
                block8 : {
                    block9 : {
                        block10 : {
                            var2_6 = var4_5;
                            if (var5_2 >= var0.length) return Arrays.copyOf(var9_3, var2_6);
                            var2_6 = var4_5;
                            if (var6_4 >= var1_1.length) return Arrays.copyOf(var9_3, var2_6);
                            var3_7 = var0[var5_2];
                            var7_8 = var1_1[var6_4];
                            var8_9 = var5_2 < var0.length - 1 ? var0[var5_2 + 1] : Integer.MAX_VALUE;
                            var2_6 = var6_4 < var1_1.length - 1 ? var1_1[var6_4 + 1] : Integer.MAX_VALUE;
                            if (var3_7 >= var7_8) break block8;
                            if (var8_9 <= var7_8) break block9;
                            if (var8_9 <= var2_6) break block10;
                            var3_7 = var7_8;
                            var7_8 = var6_4 += 2;
                            ** GOTO lbl38
                        }
                        var2_6 = var5_2 + 2;
                        var3_7 = var7_8;
                        var7_8 = var6_4;
                        break block11;
                    }
                    var2_6 = var5_2 + 2;
                    ** GOTO lbl43
                }
                if (var2_6 > var3_7) {
                    if (var2_6 > var8_9) {
                        var2_6 = var5_2 + 2;
                        var7_8 = var6_4;
                    } else {
                        var7_8 = var6_4 + 2;
lbl38: // 2 sources:
                        var8_9 = var2_6;
                        var2_6 = var5_2;
                    }
                } else {
                    var6_4 += 2;
                    var2_6 = var5_2;
lbl43: // 2 sources:
                    var8_9 = Integer.MAX_VALUE;
                    var3_7 = Integer.MIN_VALUE;
                    var7_8 = var6_4;
                }
            }
            var5_2 = var2_6;
            var6_4 = var7_8;
            if (var3_7 == Integer.MIN_VALUE) continue;
            var5_2 = var4_5 + 1;
            var9_3[var4_5] = var3_7;
            if (var8_9 == Integer.MAX_VALUE) {
                var2_6 = var5_2;
                return Arrays.copyOf(var9_3, var2_6);
            }
            var4_5 = var5_2 + 1;
            var9_3[var5_2] = var8_9;
            var5_2 = var2_6;
            var6_4 = var7_8;
        } while (true);
    }

    public static /* varargs */ Object invokeMethodQuietly(Object object, Method method, Object ... arrobject) {
        try {
            object = method.invoke(object, arrobject);
            return object;
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            return null;
        }
    }

    public static boolean isAutofillAvailable(Context context) {
        int n = Build.VERSION.SDK_INT;
        boolean bl = false;
        if (n < 26) {
            return false;
        }
        context = (AutofillManager)context.getSystemService(AutofillManager.class);
        boolean bl2 = bl;
        if (context != null) {
            bl2 = bl;
            if (context.isAutofillSupported()) {
                bl2 = bl;
                if (context.isEnabled()) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    public static boolean isContentUri(Uri uri) {
        if (uri != null && "content".equalsIgnoreCase(uri.getScheme())) {
            return true;
        }
        return false;
    }

    public static boolean isCurrentAccessToken(AccessToken accessToken) {
        if (accessToken != null) {
            return accessToken.equals(AccessToken.getCurrentAccessToken());
        }
        return false;
    }

    public static boolean isFileUri(Uri uri) {
        if (uri != null && "file".equalsIgnoreCase(uri.getScheme())) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(String string) {
        if (string != null && string.length() != 0) {
            return false;
        }
        return true;
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        if (collection != null && collection.size() != 0) {
            return false;
        }
        return true;
    }

    public static <T> boolean isSubset(Collection<T> object, Collection<T> collection) {
        boolean bl = true;
        if (collection != null && collection.size() != 0) {
            collection = new HashSet<T>(collection);
            object = object.iterator();
            while (object.hasNext()) {
                if (((HashSet)collection).contains(object.next())) continue;
                return false;
            }
            return true;
        }
        if (object != null) {
            if (object.size() == 0) {
                return true;
            }
            bl = false;
        }
        return bl;
    }

    public static boolean isWebUri(Uri uri) {
        if (uri != null && ("http".equalsIgnoreCase(uri.getScheme()) || URL_SCHEME.equalsIgnoreCase(uri.getScheme()) || "fbstaging".equalsIgnoreCase(uri.getScheme()))) {
            return true;
        }
        return false;
    }

    public static Set<String> jsonArrayToSet(JSONArray jSONArray) throws JSONException {
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < jSONArray.length(); ++i) {
            hashSet.add(jSONArray.getString(i));
        }
        return hashSet;
    }

    public static List<String> jsonArrayToStringList(JSONArray jSONArray) throws JSONException {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < jSONArray.length(); ++i) {
            arrayList.add(jSONArray.getString(i));
        }
        return arrayList;
    }

    public static void logd(String string, Exception exception) {
        if (FacebookSdk.isDebugEnabled() && string != null && exception != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(exception.getClass().getSimpleName());
            stringBuilder.append(": ");
            stringBuilder.append(exception.getMessage());
            Log.d((String)string, (String)stringBuilder.toString());
        }
    }

    public static void logd(String string, String string2) {
        if (FacebookSdk.isDebugEnabled() && string != null && string2 != null) {
            Log.d((String)string, (String)string2);
        }
    }

    public static void logd(String string, String string2, Throwable throwable) {
        if (FacebookSdk.isDebugEnabled() && !Utility.isNullOrEmpty(string)) {
            Log.d((String)string, (String)string2, (Throwable)throwable);
        }
    }

    public static <T, K> List<K> map(List<T> object, Mapper<T, K> mapper) {
        if (object == null) {
            return null;
        }
        ArrayList<K> arrayList = new ArrayList<K>();
        object = object.iterator();
        while (object.hasNext()) {
            K k = mapper.apply(object.next());
            if (k == null) continue;
            arrayList.add(k);
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public static String md5hash(String string) {
        return Utility.hashWithAlgorithm(HASH_ALGORITHM_MD5, string);
    }

    public static boolean mustFixWindowParamsForAutofill(Context context) {
        return Utility.isAutofillAvailable(context);
    }

    public static Bundle parseUrlQueryString(String arrstring) {
        Bundle bundle = new Bundle();
        if (!Utility.isNullOrEmpty((String)arrstring)) {
            arrstring = arrstring.split("&");
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                String[] arrstring2 = arrstring[i].split("=");
                try {
                    if (arrstring2.length == 2) {
                        bundle.putString(URLDecoder.decode(arrstring2[0], UTF8), URLDecoder.decode(arrstring2[1], UTF8));
                        continue;
                    }
                    if (arrstring2.length != 1) continue;
                    bundle.putString(URLDecoder.decode(arrstring2[0], UTF8), "");
                    continue;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    Utility.logd(LOG_TAG, unsupportedEncodingException);
                }
            }
        }
        return bundle;
    }

    public static void putCommaSeparatedStringList(Bundle bundle, String string, List<String> object) {
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            object = object.iterator();
            while (object.hasNext()) {
                stringBuilder.append((String)object.next());
                stringBuilder.append(",");
            }
            object = "";
            if (stringBuilder.length() > 0) {
                object = stringBuilder.substring(0, stringBuilder.length() - 1);
            }
            bundle.putString(string, (String)object);
        }
    }

    public static boolean putJSONValueInBundle(Bundle bundle, String string, Object object) {
        block14 : {
            block3 : {
                block13 : {
                    block12 : {
                        block11 : {
                            block10 : {
                                block9 : {
                                    block8 : {
                                        block7 : {
                                            block6 : {
                                                block5 : {
                                                    block4 : {
                                                        block2 : {
                                                            if (object != null) break block2;
                                                            bundle.remove(string);
                                                            break block3;
                                                        }
                                                        if (!(object instanceof Boolean)) break block4;
                                                        bundle.putBoolean(string, ((Boolean)object).booleanValue());
                                                        break block3;
                                                    }
                                                    if (!(object instanceof boolean[])) break block5;
                                                    bundle.putBooleanArray(string, (boolean[])object);
                                                    break block3;
                                                }
                                                if (!(object instanceof Double)) break block6;
                                                bundle.putDouble(string, ((Double)object).doubleValue());
                                                break block3;
                                            }
                                            if (!(object instanceof double[])) break block7;
                                            bundle.putDoubleArray(string, (double[])object);
                                            break block3;
                                        }
                                        if (!(object instanceof Integer)) break block8;
                                        bundle.putInt(string, ((Integer)object).intValue());
                                        break block3;
                                    }
                                    if (!(object instanceof int[])) break block9;
                                    bundle.putIntArray(string, (int[])object);
                                    break block3;
                                }
                                if (!(object instanceof Long)) break block10;
                                bundle.putLong(string, ((Long)object).longValue());
                                break block3;
                            }
                            if (!(object instanceof long[])) break block11;
                            bundle.putLongArray(string, (long[])object);
                            break block3;
                        }
                        if (!(object instanceof String)) break block12;
                        bundle.putString(string, (String)object);
                        break block3;
                    }
                    if (!(object instanceof JSONArray)) break block13;
                    bundle.putString(string, object.toString());
                    break block3;
                }
                if (!(object instanceof JSONObject)) break block14;
                bundle.putString(string, object.toString());
            }
            return true;
        }
        return false;
    }

    public static void putNonEmptyString(Bundle bundle, String string, String string2) {
        if (!Utility.isNullOrEmpty(string2)) {
            bundle.putString(string, string2);
        }
    }

    public static void putUri(Bundle bundle, String string, Uri uri) {
        if (uri != null) {
            Utility.putNonEmptyString(bundle, string, uri.toString());
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String readStreamToString(InputStream object) throws IOException {
        BufferedInputStream bufferedInputStream;
        void var0_4;
        Closeable closeable;
        block7 : {
            bufferedInputStream = new BufferedInputStream((InputStream)object);
            closeable = new InputStreamReader(bufferedInputStream);
            try {
                int n;
                object = new StringBuilder();
                char[] arrc = new char[2048];
                while ((n = closeable.read(arrc)) != -1) {
                    object.append(arrc, 0, n);
                }
                object = object.toString();
            }
            catch (Throwable throwable) {}
            Utility.closeQuietly(bufferedInputStream);
            Utility.closeQuietly(closeable);
            return object;
            break block7;
            catch (Throwable throwable) {
                closeable = null;
                break block7;
            }
            catch (Throwable throwable) {
                bufferedInputStream = null;
                closeable = bufferedInputStream;
            }
        }
        Utility.closeQuietly(bufferedInputStream);
        Utility.closeQuietly(closeable);
        throw var0_4;
    }

    public static Map<String, String> readStringMapFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        if (n < 0) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (int i = 0; i < n; ++i) {
            hashMap.put(parcel.readString(), parcel.readString());
        }
        return hashMap;
    }

    private static void refreshAvailableExternalStorage() {
        try {
            if (Utility.externalStorageExists()) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                availableExternalStorageGB = (long)statFs.getAvailableBlocks() * (long)statFs.getBlockSize();
            }
            availableExternalStorageGB = Utility.convertBytesToGB(availableExternalStorageGB);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int refreshBestGuessNumberOfCPUCores() {
        if (numCPUCores > 0) {
            return numCPUCores;
        }
        try {
            File[] arrfile = new File("/sys/devices/system/cpu/").listFiles(new FilenameFilter(){

                @Override
                public boolean accept(File file, String string) {
                    return Pattern.matches("cpu[0-9]+", string);
                }
            });
            if (arrfile != null) {
                numCPUCores = arrfile.length;
            }
        }
        catch (Exception exception) {}
        if (numCPUCores <= 0) {
            numCPUCores = Math.max(Runtime.getRuntime().availableProcessors(), 1);
        }
        return numCPUCores;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void refreshCarrierName(Context context) {
        if (!carrierName.equals(noCarrierConstant)) return;
        try {
            carrierName = ((TelephonyManager)context.getSystemService("phone")).getNetworkOperatorName();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static void refreshPeriodicExtendedDeviceInfo(Context context) {
        if (timestampOfLastCheck == -1L || System.currentTimeMillis() - timestampOfLastCheck >= 1800000L) {
            timestampOfLastCheck = System.currentTimeMillis();
            Utility.refreshTimezone();
            Utility.refreshCarrierName(context);
            Utility.refreshTotalExternalStorage();
            Utility.refreshAvailableExternalStorage();
        }
    }

    private static void refreshTimezone() {
        try {
            TimeZone timeZone = TimeZone.getDefault();
            deviceTimezoneAbbreviation = timeZone.getDisplayName(timeZone.inDaylightTime(new Date()), 0);
            deviceTimeZoneName = timeZone.getID();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static void refreshTotalExternalStorage() {
        try {
            if (Utility.externalStorageExists()) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                totalExternalStorageGB = (long)statFs.getBlockCount() * (long)statFs.getBlockSize();
            }
            totalExternalStorageGB = Utility.convertBytesToGB(totalExternalStorageGB);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static String safeGetStringFromResponse(JSONObject jSONObject, String string) {
        if (jSONObject != null) {
            return jSONObject.optString(string, "");
        }
        return "";
    }

    public static void setAppEventAttributionParameters(JSONObject jSONObject, AttributionIdentifiers attributionIdentifiers, String string, boolean bl) throws JSONException {
        if (attributionIdentifiers != null && attributionIdentifiers.getAttributionId() != null) {
            jSONObject.put("attribution", (Object)attributionIdentifiers.getAttributionId());
        }
        if (attributionIdentifiers != null && attributionIdentifiers.getAndroidAdvertiserId() != null) {
            jSONObject.put("advertiser_id", (Object)attributionIdentifiers.getAndroidAdvertiserId());
            jSONObject.put("advertiser_tracking_enabled", attributionIdentifiers.isTrackingLimited() ^ true);
        }
        if (attributionIdentifiers != null && attributionIdentifiers.getAndroidInstallerPackage() != null) {
            jSONObject.put("installer_package", (Object)attributionIdentifiers.getAndroidInstallerPackage());
        }
        jSONObject.put("anon_id", (Object)string);
        jSONObject.put("application_tracking_enabled", bl ^ true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setAppEventExtendedDeviceInfoParameters(JSONObject jSONObject, Context context) throws JSONException {
        JSONArray jSONArray;
        double d;
        int n;
        int n2;
        block14 : {
            block13 : {
                Object object;
                float f;
                Object object2;
                block12 : {
                    jSONArray = new JSONArray();
                    jSONArray.put((Object)EXTRA_APP_EVENTS_INFO_FORMAT_VERSION);
                    Utility.refreshPeriodicExtendedDeviceInfo(context);
                    String string = context.getPackageName();
                    object2 = "";
                    try {
                        object = context.getPackageManager().getPackageInfo(string, 0);
                        n = object.versionCode;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {}
                    try {
                        object2 = object = object.versionName;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {}
                    n = -1;
                    jSONArray.put((Object)string);
                    jSONArray.put(n);
                    jSONArray.put(object2);
                    jSONArray.put((Object)Build.VERSION.RELEASE);
                    jSONArray.put((Object)Build.MODEL);
                    try {
                        object2 = context.getResources().getConfiguration().locale;
                        break block12;
                    }
                    catch (Exception exception) {}
                    object2 = Locale.getDefault();
                }
                object = new StringBuilder();
                object.append(object2.getLanguage());
                object.append("_");
                object.append(object2.getCountry());
                jSONArray.put((Object)object.toString());
                jSONArray.put((Object)deviceTimezoneAbbreviation);
                jSONArray.put((Object)carrierName);
                d = 0.0;
                try {
                    context = (WindowManager)context.getSystemService("window");
                    if (context == null) break block13;
                    context = context.getDefaultDisplay();
                    object2 = new DisplayMetrics();
                    context.getMetrics((DisplayMetrics)object2);
                    n2 = object2.widthPixels;
                }
                catch (Exception exception) {}
                try {
                    n = object2.heightPixels;
                }
                catch (Exception exception) {}
                try {
                    f = object2.density;
                }
                catch (Exception exception) {}
                d = f;
                break block14;
            }
            n = n2 = 0;
            break block14;
            n = 0;
        }
        jSONArray.put(n2);
        jSONArray.put(n);
        jSONArray.put((Object)String.format("%.2f", d));
        jSONArray.put(Utility.refreshBestGuessNumberOfCPUCores());
        jSONArray.put(totalExternalStorageGB);
        jSONArray.put(availableExternalStorageGB);
        jSONArray.put((Object)deviceTimeZoneName);
        jSONObject.put("extinfo", (Object)jSONArray.toString());
    }

    public static String sha1hash(String string) {
        return Utility.hashWithAlgorithm(HASH_ALGORITHM_SHA1, string);
    }

    public static String sha1hash(byte[] arrby) {
        return Utility.hashWithAlgorithm(HASH_ALGORITHM_SHA1, arrby);
    }

    public static boolean stringsEqualOrEmpty(String string, String string2) {
        boolean bl = TextUtils.isEmpty((CharSequence)string);
        boolean bl2 = TextUtils.isEmpty((CharSequence)string2);
        if (bl && bl2) {
            return true;
        }
        if (!bl && !bl2) {
            return string.equals(string2);
        }
        return false;
    }

    public static JSONArray tryGetJSONArrayFromResponse(JSONObject jSONObject, String string) {
        if (jSONObject != null) {
            return jSONObject.optJSONArray(string);
        }
        return null;
    }

    public static JSONObject tryGetJSONObjectFromResponse(JSONObject jSONObject, String string) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(string);
        }
        return null;
    }

    public static /* varargs */ <T> Collection<T> unmodifiableCollection(T ... arrT) {
        return Collections.unmodifiableCollection(Arrays.asList(arrT));
    }

    public static void writeStringMapToParcel(Parcel parcel, Map<String, String> object) {
        if (object == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(object.size());
        for (Map.Entry entry : object.entrySet()) {
            parcel.writeString((String)entry.getKey());
            parcel.writeString((String)entry.getValue());
        }
    }

    public static interface GraphMeRequestWithCacheCallback {
        public void onFailure(FacebookException var1);

        public void onSuccess(JSONObject var1);
    }

    public static interface Mapper<T, K> {
        public K apply(T var1);
    }

    public static class PermissionsPair {
        List<String> declinedPermissions;
        List<String> grantedPermissions;

        public PermissionsPair(List<String> list, List<String> list2) {
            this.grantedPermissions = list;
            this.declinedPermissions = list2;
        }

        public List<String> getDeclinedPermissions() {
            return this.declinedPermissions;
        }

        public List<String> getGrantedPermissions() {
            return this.grantedPermissions;
        }
    }

    public static interface Predicate<T> {
        public boolean apply(T var1);
    }

}
