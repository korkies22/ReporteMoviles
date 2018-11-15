/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleJSONConverter {
    private static final Map<Class<?>, Setter> SETTERS = new HashMap();

    static {
        SETTERS.put(Boolean.class, new Setter(){

            @Override
            public void setOnBundle(Bundle bundle, String string, Object object) throws JSONException {
                bundle.putBoolean(string, ((Boolean)object).booleanValue());
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                jSONObject.put(string, object);
            }
        });
        SETTERS.put(Integer.class, new Setter(){

            @Override
            public void setOnBundle(Bundle bundle, String string, Object object) throws JSONException {
                bundle.putInt(string, ((Integer)object).intValue());
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                jSONObject.put(string, object);
            }
        });
        SETTERS.put(Long.class, new Setter(){

            @Override
            public void setOnBundle(Bundle bundle, String string, Object object) throws JSONException {
                bundle.putLong(string, ((Long)object).longValue());
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                jSONObject.put(string, object);
            }
        });
        SETTERS.put(Double.class, new Setter(){

            @Override
            public void setOnBundle(Bundle bundle, String string, Object object) throws JSONException {
                bundle.putDouble(string, ((Double)object).doubleValue());
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                jSONObject.put(string, object);
            }
        });
        SETTERS.put(String.class, new Setter(){

            @Override
            public void setOnBundle(Bundle bundle, String string, Object object) throws JSONException {
                bundle.putString(string, (String)object);
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                jSONObject.put(string, object);
            }
        });
        SETTERS.put(String[].class, new Setter(){

            @Override
            public void setOnBundle(Bundle bundle, String string, Object object) throws JSONException {
                throw new IllegalArgumentException("Unexpected type from JSON");
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object arrstring) throws JSONException {
                JSONArray jSONArray = new JSONArray();
                arrstring = arrstring;
                int n = arrstring.length;
                for (int i = 0; i < n; ++i) {
                    jSONArray.put((Object)arrstring[i]);
                }
                jSONObject.put(string, (Object)jSONArray);
            }
        });
        SETTERS.put(JSONArray.class, new Setter(){

            @Override
            public void setOnBundle(Bundle object, String string, Object object2) throws JSONException {
                JSONArray jSONArray = (JSONArray)object2;
                ArrayList<String> arrayList = new ArrayList<String>();
                if (jSONArray.length() == 0) {
                    object.putStringArrayList(string, arrayList);
                    return;
                }
                for (int i = 0; i < jSONArray.length(); ++i) {
                    object2 = jSONArray.get(i);
                    if (object2 instanceof String) {
                        arrayList.add((String)object2);
                        continue;
                    }
                    object = new StringBuilder();
                    object.append("Unexpected type in an array: ");
                    object.append(object2.getClass());
                    throw new IllegalArgumentException(object.toString());
                }
                object.putStringArrayList(string, arrayList);
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                throw new IllegalArgumentException("JSONArray's are not supported in bundles.");
            }
        });
    }

    public static Bundle convertToBundle(JSONObject object) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator iterator = object.keys();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            Object object2 = object.get(string);
            if (object2 == null || object2 == JSONObject.NULL) continue;
            if (object2 instanceof JSONObject) {
                bundle.putBundle(string, BundleJSONConverter.convertToBundle((JSONObject)object2));
                continue;
            }
            Setter setter = SETTERS.get(object2.getClass());
            if (setter == null) {
                object = new StringBuilder();
                object.append("Unsupported type: ");
                object.append(object2.getClass());
                throw new IllegalArgumentException(object.toString());
            }
            setter.setOnBundle(bundle, string, object2);
        }
        return bundle;
    }

    public static JSONObject convertToJSON(Bundle object) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String string : object.keySet()) {
            Object object2;
            Iterator iterator = object.get(string);
            if (iterator == null) continue;
            if (iterator instanceof List) {
                object2 = new JSONArray();
                iterator = ((List)((Object)iterator)).iterator();
                while (iterator.hasNext()) {
                    object2.put((Object)((String)iterator.next()));
                }
                jSONObject.put(string, object2);
                continue;
            }
            if (iterator instanceof Bundle) {
                jSONObject.put(string, (Object)BundleJSONConverter.convertToJSON((Bundle)iterator));
                continue;
            }
            object2 = SETTERS.get(iterator.getClass());
            if (object2 == null) {
                object = new StringBuilder();
                object.append("Unsupported type: ");
                object.append(iterator.getClass());
                throw new IllegalArgumentException(object.toString());
            }
            object2.setOnJSON(jSONObject, string, iterator);
        }
        return jSONObject;
    }

    public static interface Setter {
        public void setOnBundle(Bundle var1, String var2, Object var3) throws JSONException;

        public void setOnJSON(JSONObject var1, String var2, Object var3) throws JSONException;
    }

}
