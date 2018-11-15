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

public static interface BundleJSONConverter.Setter {
    public void setOnBundle(Bundle var1, String var2, Object var3) throws JSONException;

    public void setOnJSON(JSONObject var1, String var2, Object var3) throws JSONException;
}
