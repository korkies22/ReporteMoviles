/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.net.Uri;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareGraphRequest {
    public static GraphRequest createOpenGraphObject(ShareOpenGraphObject object) throws FacebookException {
        Object object2 = object.getString("type");
        String string = object2;
        if (object2 == null) {
            string = object.getString("og:type");
        }
        if (string == null) {
            throw new FacebookException("Open graph object type cannot be null");
        }
        try {
            object2 = (JSONObject)OpenGraphJSONUtility.toJSONValue(object, new OpenGraphJSONUtility.PhotoJSONProcessor(){

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
            });
            object = new Bundle();
            object.putString("object", object2.toString());
            object2 = Locale.ROOT;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("objects/");
            stringBuilder.append(string);
            string = String.format((Locale)object2, "%s/%s", "me", stringBuilder.toString());
            object = new GraphRequest(AccessToken.getCurrentAccessToken(), string, (Bundle)object, HttpMethod.POST);
            return object;
        }
        catch (JSONException jSONException) {
            throw new FacebookException(jSONException.getMessage());
        }
    }

}
