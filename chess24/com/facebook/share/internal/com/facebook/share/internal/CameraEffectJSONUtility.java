/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import com.facebook.share.model.CameraEffectArguments;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CameraEffectJSONUtility {
    private static final Map<Class<?>, Setter> SETTERS = new HashMap();

    static {
        SETTERS.put(String.class, new Setter(){

            @Override
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String string, Object object) throws JSONException {
                builder.putArgument(string, (String)object);
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                jSONObject.put(string, object);
            }
        });
        SETTERS.put(String[].class, new Setter(){

            @Override
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String string, Object object) throws JSONException {
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
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder object, String string, Object object2) throws JSONException {
                JSONArray jSONArray = (JSONArray)object2;
                String[] arrstring = new String[jSONArray.length()];
                for (int i = 0; i < jSONArray.length(); ++i) {
                    object2 = jSONArray.get(i);
                    if (object2 instanceof String) {
                        arrstring[i] = (String)object2;
                        continue;
                    }
                    object = new StringBuilder();
                    object.append("Unexpected type in an array: ");
                    object.append(object2.getClass());
                    throw new IllegalArgumentException(object.toString());
                }
                object.putArgument(string, arrstring);
            }

            @Override
            public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
                throw new IllegalArgumentException("JSONArray's are not supported in bundles.");
            }
        });
    }

    public static CameraEffectArguments convertToCameraEffectArguments(JSONObject object) throws JSONException {
        if (object == null) {
            return null;
        }
        CameraEffectArguments.Builder builder = new CameraEffectArguments.Builder();
        Iterator iterator = object.keys();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            Object object2 = object.get(string);
            if (object2 == null || object2 == JSONObject.NULL) continue;
            Setter setter = SETTERS.get(object2.getClass());
            if (setter == null) {
                object = new StringBuilder();
                object.append("Unsupported type: ");
                object.append(object2.getClass());
                throw new IllegalArgumentException(object.toString());
            }
            setter.setOnArgumentsBuilder(builder, string, object2);
        }
        return builder.build();
    }

    public static JSONObject convertToJSON(CameraEffectArguments object) throws JSONException {
        if (object == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (String string : object.keySet()) {
            Object object2 = object.get(string);
            if (object2 == null) continue;
            Setter setter = SETTERS.get(object2.getClass());
            if (setter == null) {
                object = new StringBuilder();
                object.append("Unsupported type: ");
                object.append(object2.getClass());
                throw new IllegalArgumentException(object.toString());
            }
            setter.setOnJSON(jSONObject, string, object2);
        }
        return jSONObject;
    }

    public static interface Setter {
        public void setOnArgumentsBuilder(CameraEffectArguments.Builder var1, String var2, Object var3) throws JSONException;

        public void setOnJSON(JSONObject var1, String var2, Object var3) throws JSONException;
    }

}
