/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  org.json.JSONObject
 */
package com.facebook;

import android.net.Uri;
import com.facebook.FacebookException;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.model.SharePhoto;
import org.json.JSONObject;

static final class ShareGraphRequest
implements OpenGraphJSONUtility.PhotoJSONProcessor {
    ShareGraphRequest() {
    }

    @Override
    public JSONObject toJSONObject(SharePhoto sharePhoto) {
        sharePhoto = sharePhoto.getImageUrl();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", (Object)sharePhoto.toString());
            return jSONObject;
        }
        catch (Exception exception) {
            throw new FacebookException("Unable to attach images", exception);
        }
    }
}
