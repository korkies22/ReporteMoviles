/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.internal;

import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import org.json.JSONObject;

public static interface Utility.GraphMeRequestWithCacheCallback {
    public void onFailure(FacebookException var1);

    public void onSuccess(JSONObject var1);
}
