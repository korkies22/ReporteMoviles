/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share;

import com.facebook.FacebookException;
import com.facebook.internal.CollectionMapper;
import com.facebook.share.model.ShareOpenGraphObject;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class ShareApi
implements CollectionMapper.Collection<String> {
    final /* synthetic */ ShareOpenGraphObject val$object;
    final /* synthetic */ JSONObject val$stagedObject;

    ShareApi(ShareOpenGraphObject shareOpenGraphObject, JSONObject jSONObject) {
        this.val$object = shareOpenGraphObject;
        this.val$stagedObject = jSONObject;
    }

    @Override
    public Object get(String string) {
        return this.val$object.get(string);
    }

    @Override
    public Iterator<String> keyIterator() {
        return this.val$object.keySet().iterator();
    }

    @Override
    public void set(String string, Object object, CollectionMapper.OnErrorListener onErrorListener) {
        try {
            this.val$stagedObject.put(string, object);
            return;
        }
        catch (JSONException jSONException) {
            Object object2 = object = jSONException.getLocalizedMessage();
            if (object == null) {
                object2 = "Error staging object.";
            }
            onErrorListener.onError(new FacebookException((String)object2));
            return;
        }
    }
}
