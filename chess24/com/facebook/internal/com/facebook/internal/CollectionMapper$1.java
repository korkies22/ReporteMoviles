/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import com.facebook.FacebookException;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.Mutable;

static final class CollectionMapper
implements CollectionMapper.OnMapperCompleteListener {
    final /* synthetic */ Mutable val$didReturnError;
    final /* synthetic */ CollectionMapper.OnMapperCompleteListener val$onMapperCompleteListener;
    final /* synthetic */ Mutable val$pendingJobCount;

    CollectionMapper(Mutable mutable, Mutable mutable2, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        this.val$didReturnError = mutable;
        this.val$pendingJobCount = mutable2;
        this.val$onMapperCompleteListener = onMapperCompleteListener;
    }

    @Override
    public void onComplete() {
        if (((Boolean)this.val$didReturnError.value).booleanValue()) {
            return;
        }
        Mutable mutable = this.val$pendingJobCount;
        Integer n = (Integer)this.val$pendingJobCount.value - 1;
        mutable.value = n;
        if (n == 0) {
            this.val$onMapperCompleteListener.onComplete();
        }
    }

    @Override
    public void onError(FacebookException facebookException) {
        if (((Boolean)this.val$didReturnError.value).booleanValue()) {
            return;
        }
        this.val$didReturnError.value = true;
        this.val$onMapperCompleteListener.onError(facebookException);
    }
}
