/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.AccessTokenSource;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class LegacyTokenHelper {
    public static final String APPLICATION_ID_KEY = "com.facebook.TokenCachingStrategy.ApplicationId";
    public static final String DECLINED_PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.DeclinedPermissions";
    public static final String DEFAULT_CACHE_KEY = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
    public static final String EXPIRATION_DATE_KEY = "com.facebook.TokenCachingStrategy.ExpirationDate";
    private static final long INVALID_BUNDLE_MILLISECONDS = Long.MIN_VALUE;
    private static final String IS_SSO_KEY = "com.facebook.TokenCachingStrategy.IsSSO";
    private static final String JSON_VALUE = "value";
    private static final String JSON_VALUE_ENUM_TYPE = "enumType";
    private static final String JSON_VALUE_TYPE = "valueType";
    public static final String LAST_REFRESH_DATE_KEY = "com.facebook.TokenCachingStrategy.LastRefreshDate";
    public static final String PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.Permissions";
    private static final String TAG = "LegacyTokenHelper";
    public static final String TOKEN_KEY = "com.facebook.TokenCachingStrategy.Token";
    public static final String TOKEN_SOURCE_KEY = "com.facebook.TokenCachingStrategy.AccessTokenSource";
    private static final String TYPE_BOOLEAN = "bool";
    private static final String TYPE_BOOLEAN_ARRAY = "bool[]";
    private static final String TYPE_BYTE = "byte";
    private static final String TYPE_BYTE_ARRAY = "byte[]";
    private static final String TYPE_CHAR = "char";
    private static final String TYPE_CHAR_ARRAY = "char[]";
    private static final String TYPE_DOUBLE = "double";
    private static final String TYPE_DOUBLE_ARRAY = "double[]";
    private static final String TYPE_ENUM = "enum";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_FLOAT_ARRAY = "float[]";
    private static final String TYPE_INTEGER = "int";
    private static final String TYPE_INTEGER_ARRAY = "int[]";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_LONG_ARRAY = "long[]";
    private static final String TYPE_SHORT = "short";
    private static final String TYPE_SHORT_ARRAY = "short[]";
    private static final String TYPE_STRING = "string";
    private static final String TYPE_STRING_LIST = "stringList";
    private SharedPreferences cache;
    private String cacheKey;

    public LegacyTokenHelper(Context context) {
        this(context, null);
    }

    public LegacyTokenHelper(Context object, String string) {
        Validate.notNull(object, "context");
        String string2 = string;
        if (Utility.isNullOrEmpty(string)) {
            string2 = DEFAULT_CACHE_KEY;
        }
        this.cacheKey = string2;
        string = object.getApplicationContext();
        if (string != null) {
            object = string;
        }
        this.cache = object.getSharedPreferences(this.cacheKey, 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void deserializeKey(String string, Bundle bundle) throws JSONException {
        int n;
        Object object = new JSONObject(this.cache.getString(string, "{}"));
        JSONArray jSONArray = object.getString(JSON_VALUE_TYPE);
        if (jSONArray.equals(TYPE_BOOLEAN)) {
            bundle.putBoolean(string, object.getBoolean(JSON_VALUE));
            return;
        }
        boolean bl = jSONArray.equals(TYPE_BOOLEAN_ARRAY);
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        if (bl) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new boolean[object.length()];
            for (n = n8; n < ((boolean[])jSONArray).length; ++n) {
                jSONArray[n] = object.getBoolean(n);
            }
            bundle.putBooleanArray(string, (boolean[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_BYTE)) {
            bundle.putByte(string, (byte)object.getInt(JSON_VALUE));
            return;
        }
        if (jSONArray.equals(TYPE_BYTE_ARRAY)) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new byte[object.length()];
            for (n = n2; n < ((boolean[])jSONArray).length; ++n) {
                jSONArray[n] = (byte)object.getInt(n);
            }
            bundle.putByteArray(string, (byte[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_SHORT)) {
            bundle.putShort(string, (short)object.getInt(JSON_VALUE));
            return;
        }
        if (jSONArray.equals(TYPE_SHORT_ARRAY)) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new short[object.length()];
            for (n = n3; n < ((boolean[])jSONArray).length; ++n) {
                jSONArray[n] = (short)object.getInt(n);
            }
            bundle.putShortArray(string, (short[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_INTEGER)) {
            bundle.putInt(string, object.getInt(JSON_VALUE));
            return;
        }
        if (jSONArray.equals(TYPE_INTEGER_ARRAY)) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new int[object.length()];
            for (n = n4; n < ((boolean[])jSONArray).length; ++n) {
                jSONArray[n] = object.getInt(n);
            }
            bundle.putIntArray(string, (int[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_LONG)) {
            bundle.putLong(string, object.getLong(JSON_VALUE));
            return;
        }
        if (jSONArray.equals(TYPE_LONG_ARRAY)) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new long[object.length()];
            for (n = n5; n < ((boolean[])jSONArray).length; ++n) {
                jSONArray[n] = object.getLong(n);
            }
            bundle.putLongArray(string, (long[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_FLOAT)) {
            bundle.putFloat(string, (float)object.getDouble(JSON_VALUE));
            return;
        }
        if (jSONArray.equals(TYPE_FLOAT_ARRAY)) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new float[object.length()];
            for (n = n6; n < ((boolean[])jSONArray).length; ++n) {
                jSONArray[n] = (float)object.getDouble(n);
            }
            bundle.putFloatArray(string, (float[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_DOUBLE)) {
            bundle.putDouble(string, object.getDouble(JSON_VALUE));
            return;
        }
        if (jSONArray.equals(TYPE_DOUBLE_ARRAY)) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new double[object.length()];
            for (n = n7; n < ((boolean[])jSONArray).length; ++n) {
                jSONArray[n] = object.getDouble(n);
            }
            bundle.putDoubleArray(string, (double[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_CHAR)) {
            if ((object = object.getString(JSON_VALUE)) == null || object.length() != 1) return;
            bundle.putChar(string, object.charAt(0));
            return;
        }
        if (jSONArray.equals(TYPE_CHAR_ARRAY)) {
            object = object.getJSONArray(JSON_VALUE);
            jSONArray = new char[object.length()];
            for (n = 0; n < ((JSONArray)jSONArray).length; ++n) {
                String string2 = object.getString(n);
                if (string2 == null || string2.length() != 1) continue;
                jSONArray[n] = (JSONArray)string2.charAt(0);
            }
            bundle.putCharArray(string, (char[])jSONArray);
            return;
        }
        if (jSONArray.equals(TYPE_STRING)) {
            bundle.putString(string, object.getString(JSON_VALUE));
            return;
        }
        if (jSONArray.equals(TYPE_STRING_LIST)) {
            jSONArray = object.getJSONArray(JSON_VALUE);
            n8 = jSONArray.length();
            ArrayList<Object> arrayList = new ArrayList<Object>(n8);
            for (n = 0; n < n8; ++n) {
                object = jSONArray.get(n);
                object = object == JSONObject.NULL ? null : (String)object;
                arrayList.add(n, object);
            }
            bundle.putStringArrayList(string, arrayList);
            return;
        }
        if (!jSONArray.equals(TYPE_ENUM)) return;
        try {
            bundle.putSerializable(string, Enum.valueOf(Class.forName(object.getString(JSON_VALUE_ENUM_TYPE)), object.getString(JSON_VALUE)));
            return;
        }
        catch (ClassNotFoundException | IllegalArgumentException exception) {
            return;
        }
    }

    public static String getApplicationId(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        return bundle.getString(APPLICATION_ID_KEY);
    }

    static Date getDate(Bundle bundle, String string) {
        if (bundle == null) {
            return null;
        }
        long l = bundle.getLong(string, Long.MIN_VALUE);
        if (l == Long.MIN_VALUE) {
            return null;
        }
        return new Date(l);
    }

    public static Date getExpirationDate(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        return LegacyTokenHelper.getDate(bundle, EXPIRATION_DATE_KEY);
    }

    public static long getExpirationMilliseconds(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        return bundle.getLong(EXPIRATION_DATE_KEY);
    }

    public static Date getLastRefreshDate(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        return LegacyTokenHelper.getDate(bundle, LAST_REFRESH_DATE_KEY);
    }

    public static long getLastRefreshMilliseconds(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        return bundle.getLong(LAST_REFRESH_DATE_KEY);
    }

    public static Set<String> getPermissions(Bundle object) {
        Validate.notNull(object, "bundle");
        object = object.getStringArrayList(PERMISSIONS_KEY);
        if (object == null) {
            return null;
        }
        return new HashSet<String>((Collection<String>)object);
    }

    public static AccessTokenSource getSource(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        if (bundle.containsKey(TOKEN_SOURCE_KEY)) {
            return (AccessTokenSource)((Object)bundle.getSerializable(TOKEN_SOURCE_KEY));
        }
        if (bundle.getBoolean(IS_SSO_KEY)) {
            return AccessTokenSource.FACEBOOK_APPLICATION_WEB;
        }
        return AccessTokenSource.WEB_VIEW;
    }

    public static String getToken(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        return bundle.getString(TOKEN_KEY);
    }

    public static boolean hasTokenInformation(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        String string = bundle.getString(TOKEN_KEY);
        if (string != null) {
            if (string.length() == 0) {
                return false;
            }
            if (bundle.getLong(EXPIRATION_DATE_KEY, 0L) == 0L) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static void putApplicationId(Bundle bundle, String string) {
        Validate.notNull((Object)bundle, "bundle");
        bundle.putString(APPLICATION_ID_KEY, string);
    }

    static void putDate(Bundle bundle, String string, Date date) {
        bundle.putLong(string, date.getTime());
    }

    public static void putDeclinedPermissions(Bundle bundle, Collection<String> collection) {
        Validate.notNull((Object)bundle, "bundle");
        Validate.notNull(collection, JSON_VALUE);
        bundle.putStringArrayList(DECLINED_PERMISSIONS_KEY, new ArrayList<String>(collection));
    }

    public static void putExpirationDate(Bundle bundle, Date date) {
        Validate.notNull((Object)bundle, "bundle");
        Validate.notNull(date, JSON_VALUE);
        LegacyTokenHelper.putDate(bundle, EXPIRATION_DATE_KEY, date);
    }

    public static void putExpirationMilliseconds(Bundle bundle, long l) {
        Validate.notNull((Object)bundle, "bundle");
        bundle.putLong(EXPIRATION_DATE_KEY, l);
    }

    public static void putLastRefreshDate(Bundle bundle, Date date) {
        Validate.notNull((Object)bundle, "bundle");
        Validate.notNull(date, JSON_VALUE);
        LegacyTokenHelper.putDate(bundle, LAST_REFRESH_DATE_KEY, date);
    }

    public static void putLastRefreshMilliseconds(Bundle bundle, long l) {
        Validate.notNull((Object)bundle, "bundle");
        bundle.putLong(LAST_REFRESH_DATE_KEY, l);
    }

    public static void putPermissions(Bundle bundle, Collection<String> collection) {
        Validate.notNull((Object)bundle, "bundle");
        Validate.notNull(collection, JSON_VALUE);
        bundle.putStringArrayList(PERMISSIONS_KEY, new ArrayList<String>(collection));
    }

    public static void putSource(Bundle bundle, AccessTokenSource accessTokenSource) {
        Validate.notNull((Object)bundle, "bundle");
        bundle.putSerializable(TOKEN_SOURCE_KEY, (Serializable)((Object)accessTokenSource));
    }

    public static void putToken(Bundle bundle, String string) {
        Validate.notNull((Object)bundle, "bundle");
        Validate.notNull(string, JSON_VALUE);
        bundle.putString(TOKEN_KEY, string);
    }

    private void serializeKey(String string, Bundle object, SharedPreferences.Editor editor) throws JSONException {
        JSONArray jSONArray;
        Object object2 = object.get(string);
        if (object2 == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        boolean bl = object2 instanceof Byte;
        Object object3 = null;
        if (bl) {
            object = TYPE_BYTE;
            jSONObject.put(JSON_VALUE, ((Byte)object2).intValue());
            jSONArray = object3;
        } else if (object2 instanceof Short) {
            object = TYPE_SHORT;
            jSONObject.put(JSON_VALUE, ((Short)object2).intValue());
            jSONArray = object3;
        } else if (object2 instanceof Integer) {
            object = TYPE_INTEGER;
            jSONObject.put(JSON_VALUE, ((Integer)object2).intValue());
            jSONArray = object3;
        } else if (object2 instanceof Long) {
            object = TYPE_LONG;
            jSONObject.put(JSON_VALUE, ((Long)object2).longValue());
            jSONArray = object3;
        } else if (object2 instanceof Float) {
            object = TYPE_FLOAT;
            jSONObject.put(JSON_VALUE, ((Float)object2).doubleValue());
            jSONArray = object3;
        } else if (object2 instanceof Double) {
            object = TYPE_DOUBLE;
            jSONObject.put(JSON_VALUE, ((Double)object2).doubleValue());
            jSONArray = object3;
        } else if (object2 instanceof Boolean) {
            object = TYPE_BOOLEAN;
            jSONObject.put(JSON_VALUE, ((Boolean)object2).booleanValue());
            jSONArray = object3;
        } else if (object2 instanceof Character) {
            object = TYPE_CHAR;
            jSONObject.put(JSON_VALUE, (Object)object2.toString());
            jSONArray = object3;
        } else if (object2 instanceof String) {
            object = TYPE_STRING;
            jSONObject.put(JSON_VALUE, (Object)((String)object2));
            jSONArray = object3;
        } else if (object2 instanceof Enum) {
            object = TYPE_ENUM;
            jSONObject.put(JSON_VALUE, (Object)object2.toString());
            jSONObject.put(JSON_VALUE_ENUM_TYPE, (Object)object2.getClass().getName());
            jSONArray = object3;
        } else {
            jSONArray = new JSONArray();
            bl = object2 instanceof byte[];
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            if (bl) {
                object3 = TYPE_BYTE_ARRAY;
                object2 = object2;
                n = ((byte[])object2).length;
                do {
                    object = object3;
                    if (n8 < n) {
                        jSONArray.put((int)object2[n8]);
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof short[]) {
                object3 = TYPE_SHORT_ARRAY;
                object2 = object2;
                n2 = ((byte[])object2).length;
                n8 = n;
                do {
                    object = object3;
                    if (n8 < n2) {
                        jSONArray.put((int)object2[n8]);
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof int[]) {
                object3 = TYPE_INTEGER_ARRAY;
                object2 = object2;
                n = ((byte[])object2).length;
                n8 = n2;
                do {
                    object = object3;
                    if (n8 < n) {
                        jSONArray.put((int)object2[n8]);
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof long[]) {
                object3 = TYPE_LONG_ARRAY;
                object2 = object2;
                n = ((byte[])object2).length;
                n8 = n3;
                do {
                    object = object3;
                    if (n8 < n) {
                        jSONArray.put((long)object2[n8]);
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof float[]) {
                object3 = TYPE_FLOAT_ARRAY;
                object2 = object2;
                n = ((byte[])object2).length;
                n8 = n4;
                do {
                    object = object3;
                    if (n8 < n) {
                        jSONArray.put((double)object2[n8]);
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof double[]) {
                object3 = TYPE_DOUBLE_ARRAY;
                object2 = object2;
                n = ((byte[])object2).length;
                n8 = n5;
                do {
                    object = object3;
                    if (n8 < n) {
                        jSONArray.put((double)object2[n8]);
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof boolean[]) {
                object3 = TYPE_BOOLEAN_ARRAY;
                object2 = (boolean[])object2;
                n = ((byte[])object2).length;
                n8 = n6;
                do {
                    object = object3;
                    if (n8 < n) {
                        jSONArray.put(object2[n8]);
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof char[]) {
                object3 = TYPE_CHAR_ARRAY;
                object2 = (char[])object2;
                n = ((byte[])object2).length;
                n8 = n7;
                do {
                    object = object3;
                    if (n8 < n) {
                        jSONArray.put((Object)String.valueOf((char)object2[n8]));
                        ++n8;
                        continue;
                    }
                    break;
                } while (true);
            } else if (object2 instanceof List) {
                object3 = TYPE_STRING_LIST;
                Iterator iterator = ((List)object2).iterator();
                do {
                    object = object3;
                    if (iterator.hasNext()) {
                        object = object2 = (String)iterator.next();
                        if (object2 == null) {
                            object = JSONObject.NULL;
                        }
                        jSONArray.put(object);
                        continue;
                    }
                    break;
                } while (true);
            } else {
                object = null;
                jSONArray = object3;
            }
        }
        if (object != null) {
            jSONObject.put(JSON_VALUE_TYPE, object);
            if (jSONArray != null) {
                jSONObject.putOpt(JSON_VALUE, jSONArray);
            }
            editor.putString(string, jSONObject.toString());
        }
    }

    public void clear() {
        this.cache.edit().clear().apply();
    }

    public Bundle load() {
        Bundle bundle = new Bundle();
        Object object = this.cache.getAll().keySet().iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            try {
                this.deserializeKey(string, bundle);
            }
            catch (JSONException jSONException) {
                object = LoggingBehavior.CACHE;
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error reading cached value for key: '");
                stringBuilder.append(string);
                stringBuilder.append("' -- ");
                stringBuilder.append((Object)jSONException);
                Logger.log(object, 5, string2, stringBuilder.toString());
                return null;
            }
        }
        return bundle;
    }

    public void save(Bundle bundle) {
        Validate.notNull((Object)bundle, "bundle");
        Object object = this.cache.edit();
        Object object2 = bundle.keySet().iterator();
        while (object2.hasNext()) {
            String string = (String)object2.next();
            try {
                this.serializeKey(string, bundle, (SharedPreferences.Editor)object);
            }
            catch (JSONException jSONException) {
                object = LoggingBehavior.CACHE;
                object2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error processing value for key: '");
                stringBuilder.append(string);
                stringBuilder.append("' -- ");
                stringBuilder.append((Object)jSONException);
                Logger.log((LoggingBehavior)((Object)object), 5, (String)object2, stringBuilder.toString());
                return;
            }
        }
        object.apply();
    }
}
