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
import com.facebook.internal.BundleJSONConverter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

static final class BundleJSONConverter
implements BundleJSONConverter.Setter {
    BundleJSONConverter() {
    }

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
}
