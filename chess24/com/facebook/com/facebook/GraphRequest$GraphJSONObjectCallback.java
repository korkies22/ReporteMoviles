/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONObject;

public static interface GraphRequest.GraphJSONObjectCallback {
    public void onCompleted(JSONObject var1, GraphResponse var2);
}
