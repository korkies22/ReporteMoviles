/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 */
package com.facebook.share;

import com.facebook.FacebookException;
import com.facebook.internal.CollectionMapper;
import org.json.JSONArray;

class ShareApi
implements CollectionMapper.OnMapperCompleteListener {
    final /* synthetic */ CollectionMapper.OnMapValueCompleteListener val$onArrayListStagedListener;
    final /* synthetic */ JSONArray val$stagedObject;

    ShareApi(CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener, JSONArray jSONArray) {
        this.val$onArrayListStagedListener = onMapValueCompleteListener;
        this.val$stagedObject = jSONArray;
    }

    @Override
    public void onComplete() {
        this.val$onArrayListStagedListener.onComplete((Object)this.val$stagedObject);
    }

    @Override
    public void onError(FacebookException facebookException) {
        this.val$onArrayListStagedListener.onError(facebookException);
    }
}
