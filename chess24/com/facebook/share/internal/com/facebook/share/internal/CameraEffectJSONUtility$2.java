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
}
