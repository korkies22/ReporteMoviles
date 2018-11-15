/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.model.SharePhoto;
import org.json.JSONObject;

public static interface OpenGraphJSONUtility.PhotoJSONProcessor {
    public JSONObject toJSONObject(SharePhoto var1);
}
