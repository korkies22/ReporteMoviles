/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import com.facebook.share.internal.CameraEffectJSONUtility;
import com.facebook.share.model.CameraEffectArguments;
import org.json.JSONException;
import org.json.JSONObject;

public static interface CameraEffectJSONUtility.Setter {
    public void setOnArgumentsBuilder(CameraEffectArguments.Builder var1, String var2, Object var3) throws JSONException;

    public void setOnJSON(JSONObject var1, String var2, Object var3) throws JSONException;
}
