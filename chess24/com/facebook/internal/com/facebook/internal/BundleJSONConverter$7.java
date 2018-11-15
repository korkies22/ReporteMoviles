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
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

static final class BundleJSONConverter
implements BundleJSONConverter.Setter {
    BundleJSONConverter() {
    }

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
}
