/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OpenGraphJSONUtility {
    private OpenGraphJSONUtility() {
    }

    private static JSONArray toJSONArray(List object, PhotoJSONProcessor photoJSONProcessor) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        object = object.iterator();
        while (object.hasNext()) {
            jSONArray.put(OpenGraphJSONUtility.toJSONValue(object.next(), photoJSONProcessor));
        }
        return jSONArray;
    }

    public static JSONObject toJSONObject(ShareOpenGraphAction shareOpenGraphAction, PhotoJSONProcessor photoJSONProcessor) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String string : shareOpenGraphAction.keySet()) {
            jSONObject.put(string, OpenGraphJSONUtility.toJSONValue(shareOpenGraphAction.get(string), photoJSONProcessor));
        }
        return jSONObject;
    }

    private static JSONObject toJSONObject(ShareOpenGraphObject shareOpenGraphObject, PhotoJSONProcessor photoJSONProcessor) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String string : shareOpenGraphObject.keySet()) {
            jSONObject.put(string, OpenGraphJSONUtility.toJSONValue(shareOpenGraphObject.get(string), photoJSONProcessor));
        }
        return jSONObject;
    }

    public static Object toJSONValue(@Nullable Object object, PhotoJSONProcessor object2) throws JSONException {
        if (object == null) {
            return JSONObject.NULL;
        }
        if (!(object instanceof String || object instanceof Boolean || object instanceof Double || object instanceof Float || object instanceof Integer)) {
            if (object instanceof Long) {
                return object;
            }
            if (object instanceof SharePhoto) {
                if (object2 != null) {
                    return object2.toJSONObject((SharePhoto)object);
                }
                return null;
            }
            if (object instanceof ShareOpenGraphObject) {
                return OpenGraphJSONUtility.toJSONObject((ShareOpenGraphObject)object, (PhotoJSONProcessor)object2);
            }
            if (object instanceof List) {
                return OpenGraphJSONUtility.toJSONArray((List)object, (PhotoJSONProcessor)object2);
            }
            object2 = new StringBuilder();
            object2.append("Invalid object found for JSON serialization: ");
            object2.append(object.toString());
            throw new IllegalArgumentException(object2.toString());
        }
        return object;
    }

    public static interface PhotoJSONProcessor {
        public JSONObject toJSONObject(SharePhoto var1);
    }

}
