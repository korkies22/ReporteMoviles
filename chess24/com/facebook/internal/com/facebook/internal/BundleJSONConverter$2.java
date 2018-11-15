/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.os.Bundle;
import com.facebook.internal.BundleJSONConverter;
import org.json.JSONException;
import org.json.JSONObject;

static final class BundleJSONConverter
implements BundleJSONConverter.Setter {
    BundleJSONConverter() {
    }

    @Override
    public void setOnBundle(Bundle bundle, String string, Object object) throws JSONException {
        bundle.putInt(string, ((Integer)object).intValue());
    }

    @Override
    public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
        jSONObject.put(string, object);
    }
}
