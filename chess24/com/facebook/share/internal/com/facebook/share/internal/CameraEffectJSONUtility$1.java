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

static final class CameraEffectJSONUtility
implements CameraEffectJSONUtility.Setter {
    CameraEffectJSONUtility() {
    }

    @Override
    public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String string, Object object) throws JSONException {
        builder.putArgument(string, (String)object);
    }

    @Override
    public void setOnJSON(JSONObject jSONObject, String string, Object object) throws JSONException {
        jSONObject.put(string, object);
    }
}
