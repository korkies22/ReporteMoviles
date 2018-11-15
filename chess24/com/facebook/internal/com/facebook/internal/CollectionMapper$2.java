/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.FacebookException;
import com.facebook.internal.CollectionMapper;

static final class CollectionMapper
implements CollectionMapper.OnMapValueCompleteListener {
    final /* synthetic */ CollectionMapper.Collection val$collection;
    final /* synthetic */ CollectionMapper.OnMapperCompleteListener val$jobCompleteListener;
    final /* synthetic */ Object val$key;

    CollectionMapper(CollectionMapper.Collection collection, Object object, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        this.val$collection = collection;
        this.val$key = object;
        this.val$jobCompleteListener = onMapperCompleteListener;
    }

    @Override
    public void onComplete(Object object) {
        this.val$collection.set(this.val$key, object, this.val$jobCompleteListener);
        this.val$jobCompleteListener.onComplete();
    }

    @Override
    public void onError(FacebookException facebookException) {
        this.val$jobCompleteListener.onError(facebookException);
    }
}
