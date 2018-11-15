/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import com.facebook.share.internal.CameraEffectJSONUtility;
import com.facebook.share.model.CameraEffectArguments;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

static final class CameraEffectJSONUtility
implements CameraEffectJSONUtility.Setter {
    CameraEffectJSONUtility() {
    }

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
}
