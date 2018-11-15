/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.net.Uri;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.model.SharePhoto;
import org.json.JSONException;
import org.json.JSONObject;

static final class ShareInternalUtility
implements OpenGraphJSONUtility.PhotoJSONProcessor {
    ShareInternalUtility() {
    }

    @Override
    public JSONObject toJSONObject(SharePhoto sharePhoto) {
        if (!Utility.isWebUri((Uri)(sharePhoto = sharePhoto.getImageUrl()))) {
            throw new FacebookException("Only web images may be used in OG objects shared via the web dialog");
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", (Object)sharePhoto.toString());
            return jSONObject;
        }
        catch (JSONException jSONException) {
            throw new FacebookException("Unable to attach images", (Throwable)jSONException);
        }
    }
}
